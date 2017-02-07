package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.htmlcleaner.DomSerializer;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import com.xujl.etl.xpath.extractor.Extractor;
import com.xujl.etl.xpath.extractor.impl.XpathExtractor;
import com.xujl.etl.xpath.kit.XMLConfBuilder;
import com.xujl.etl.xpath.schema.Field;
import com.xujl.etl.xpath.schema.Model;
import com.xujl.etl.xpath.schema.Page;
import com.xujl.etl.xpath.schema.Page.Models;

public class Person {

	public static void main(String[] args) {
		final String xml = "tianyancha.xml";
		final com.xujl.etl.xpath.schema.Config conf = new XMLConfBuilder(xml).build();
		String html = read();// "<html><title>Hello</title><targets>dfd<target
								// name='vivi' /><target name='linda'
								// /></targets></html>";
		Extractor extractor = new XpathExtractor(html);
		List<Model> models = conf.getPages().all().get(0).getModels().all();
		models.forEach(extractor::addModel);
		extractor.extract(new Extractor.Callback() {
			public void onModelExtracted(ModelEntry entry) {
				System.out.println(entry.getModel().getName() + "->\r\n" + JSON.toJSONString(entry.getFields(), true)
						+ "\r\n\r\n");
			}

			public void onFieldExtracted(FieldEntry entry) {
			}
		});
	}

	private static void t1() {
		String html = read();// "<html><title>Hello</title><targets>dfd<target
								// name='vivi' /><target name='linda'
								// /></targets></html>";
		Extractor extractor = new XpathExtractor(html);
		Model page = new Model("page");
		// page.addField("title").set("xpath", "//title/text()");
		Field set = page.addField("target").set("xpath", ".//*[@id='center']").set("isAutoExtractAttrs", true)
				.set("isArray", true);

		// set.addField("name").set("xpath",
		// ".//*[@id='yearStrTitleFont']/text()");
		set.addField("zw").set("xpath", ".//table[@class='tableList']/tbody/tr[1]/td[2]/text()");
		extractor.addModel(page);
		extractor.extract(new Extractor.Callback() {
			public void onModelExtracted(ModelEntry entry) {
				System.out.println(entry.getModel().getName() + "->\r\n" + JSON.toJSONString(entry.getFields(), true)
						+ "\r\n\r\n");
			}

			public void onFieldExtracted(FieldEntry entry) {
			}
		});
	}

	private String lastName;
	private String firstName;

	public Person() {

	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "firstName: " + firstName + ", lastName: " + lastName;
	}

	public static String read() {
		try {

			Stream<String> lines = Files.lines(Paths.get("e:/gs.html"));
			StringBuffer body = new StringBuffer();
			lines.forEach(body::append);
			return body.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
}
