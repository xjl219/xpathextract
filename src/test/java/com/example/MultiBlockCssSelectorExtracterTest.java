package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.xujl.etl.css.etler.MultiBlockExtracter;
import com.xujl.etl.css.etler.SigleBlockExtracter;

import jodd.jerry.Jerry;

public class MultiBlockCssSelectorExtracterTest {
	static String html;
	public static void setUp() throws Exception {
		   Stream<String> lines = Files.lines(Paths.get("e:/gs.html"));
           StringBuffer body =new StringBuffer();
           lines.forEach(body::append);
           html=body.toString();
	}

	public static void test() {
		  ArrayList<String> rs= new ArrayList<String>(){{
       
       		   add("#yearStrTitle:eq(0)");
       		   add("#center:eq(0)");
       	
       	   
          }};
          MultiBlockExtracter bcse= new MultiBlockExtracter(rs);
		Jerry map = bcse.map(html);//.$("span");
		System.out.println(map.$("#yearStrTitleFont").text());
		map.forEach(i->{
			System.out.println(i.htmlAll(true));
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
