package com.example;

import static org.junit.Assert.*;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.xujl.etl.css.etler.Block;
import com.xujl.etl.css.etler.SigleBlockExtracter;

import jodd.jerry.Jerry;

public class BlockCssSelectorExtracterTest {
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
		SigleBlockExtracter bcse= new SigleBlockExtracter(rs);
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
