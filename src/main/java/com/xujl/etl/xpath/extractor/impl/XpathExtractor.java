package com.xujl.etl.xpath.extractor.impl;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.dom.*;
import com.xujl.etl.xpath.extractor.AbstractXPathExtractor;
import com.xujl.etl.xpath.extractor.Extractor;
import com.xujl.etl.xpath.kit.K;
import com.xujl.etl.xpath.schema.Field;
import com.xujl.etl.xpath.schema.Model;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.springframework.boot.jackson.JsonComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class XpathExtractor extends AbstractXPathExtractor {

    public static Extractor.Builder builder() {
        return (p, ms) -> new XpathExtractor( p, ms);
    }

    private HtmlCleaner htmlCleaner;
    private TagNode tempDoc;
    private  org.w3c.dom.Document doc;
    public XpathExtractor(String html, Model... models) {
        super( null, models);
        // 使用HtmlCleaner组件
        htmlCleaner = new HtmlCleaner();
        htmlCleaner.getProperties().setTreatDeprecatedTagsAsContent(true);
        this.tempDoc = htmlCleaner.clean(html);
        init();
    }



    protected Object getDoc() {
        return this.doc;
    }
    void init(){
    	try {
			doc = new DomSerializer(
			        new CleanerProperties()).createDOM(tempDoc);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    protected List<Object> extractModel(Object aDoc, String modelXpath) {
    
    	List<Object> nodeArray = new ArrayList<>();
        if (K.isNotBlank(modelXpath)) {
            try {
            	XPath xpath = XPathFactory.newInstance().newXPath();
            	NodeList nl = (NodeList)xpath.evaluate(modelXpath, aDoc,XPathConstants.NODESET);
            	int multi = modelXpath.split("[|]").length;
            	Document document =null;// DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();;
            	Element ele =null;
            	for(int i=0;i<nl.getLength();i++){
            		if(multi >1){
            			if(i % multi == 0){
            				 document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            				 ele = document.createElement("tmp");
            				 nodeArray.add(ele);
            			}
            			ele.appendChild(document.importNode(nl.item(i), true));
            			
            			
            		}else{
            			nodeArray.add(nl.item(i));
            			
            		}
            		
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (nodeArray == null || nodeArray.size() == 0) {
            return null;
        }
//System.out.println(JSON.toJSONString(nodeArray));
        return nodeArray;
    }

    protected List<Object> extractField(Object model, Field field, String aXpath, String attr, boolean isSerialize) {
        final List<Object> values = new ArrayList<>();
        
        List<Node> nodeArray = new ArrayList<>();
        String xpath = aXpath.replace("/text()", "");
        try {
//        	nodeArray = mNode.evaluateXPath(xpath);
        	XPath xpathX = XPathFactory.newInstance().newXPath();
        	int multi = aXpath.split("[|]").length;
        	NodeList nl = (NodeList)xpathX.evaluate(xpath, model,XPathConstants.NODESET);
//        	System.out.println(nl.item(0).getClass());
        	Document document =null;// DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();;
        	Element ele =null;
        	for(int i=0;i<nl.getLength();i++){
        		if(multi >1){
        			if(i % multi == 0){
        				 document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        				 ele = document.createElement("tmp");
        				 nodeArray.add(ele);
        			}
        			ele.appendChild(document.importNode(nl.item(i), true));
        			
        			
        		}else{
        			nodeArray.add(nl.item(i));
        			
        		}
        		
        	}
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (nodeArray == null || nodeArray.size() == 0) {
            return null;
        }

        for (Node node:nodeArray) {
          
            if (aXpath.endsWith("/text()")) {
                values.add(node.getTextContent().trim());
                continue;
            }

            Object value;
            if (K.isNotBlank(attr)) {
                value = node.getAttributes().getNamedItem(attr).getNodeValue();
            } else if (isSerialize) {
                StringWriter sw = new StringWriter();
                CleanerProperties prop = htmlCleaner.getProperties();
                prop.setOmitXmlDeclaration(true);
                SimpleXmlSerializer ser = new SimpleXmlSerializer(prop);
                try {
//                    ser.write(tagNode, sw, getTask().getResponse().getCharset(), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                value = sw.getBuffer().toString();
            } else {
                value = node;
            }
            values.add(value);
        }
//        System.out.println(JSON.toJSONString(nodeArray));
        return values;
    }

    protected Map<String, String> extractAttributes(Object node) {
        Map<String, String> r = new HashMap<>();
        Node nn=(Node)node;
        NamedNodeMap attributes = nn.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
        	Node item = attributes.item(i);
//        	System.out.println(item.getNodeName()+","+item.getNodeValue());
        	r.put(item.getNodeName(), item.getNodeValue());
		}
//        r.putAll(tagNode.getAttributes());
        return r;
    }

}
