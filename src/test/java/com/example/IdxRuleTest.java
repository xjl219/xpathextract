package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.xujl.etl.css.etler.IdxBlockExtracter;
import com.xujl.etl.css.etler.MultiBlockExtracter;

import jodd.jerry.Jerry;

public class IdxRuleTest {
	static String html;
	public static void setUp() throws Exception {
		   Stream<String> lines = Files.lines(Paths.get("e:/gs.html"));
           StringBuffer body =new StringBuffer();
           lines.forEach(body::append);
           html=body.toString();
	}

	public static void test() {
		Jerry map = IdxBlockExtracter.test().map(html);//.$("span");
		map.forEach(i->{
			System.out.println(i.$("#yearStrTitleFont").text());
		});
	}
	public static void main(String[] args) {
		try {
			setUp();
			test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
