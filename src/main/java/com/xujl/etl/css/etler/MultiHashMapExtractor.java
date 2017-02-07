package com.xujl.etl.css.etler;

import java.util.ArrayList;
import java.util.HashMap;

import jodd.jerry.Jerry;
/**
 * one block contains Multiple record.
 * @author admin
 *
 */
public class MultiHashMapExtractor extends CssSelectorExtracter<ArrayList<HashMap<String,String>>,ArrayList<HashMap<String,Object>>> {
	/*
	 * 
	 */
	boolean loop = false; 
	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public MultiHashMapExtractor(ArrayList<HashMap<String,String>> rules,Class<ArrayList<HashMap<String,Object>>> model) {
		super(model,rules);
	}

	@Override
	public ArrayList<HashMap<String,Object>> map(String in) {
		 ArrayList<HashMap<String,Object>> ls= new  ArrayList<HashMap<String,Object>>();
			final Block doc = getBlock(in);
		if(loop)
			doc.forEach(ele->multiModelExtract(ls, ele));
		else
			multiModelExtract(ls, doc);
		return ls;
	}
/**
 * 
 * @param one block contains Multiple record.
 * @param doc
 */
	private void multiModelExtract(ArrayList<HashMap<String, Object>> ls, final Jerry doc) {
		rules.forEach(ruleMap->{
			HashMap<String,Object> info= extract(doc, ruleMap);
			ls.add(info);
		});
	}


	
}
