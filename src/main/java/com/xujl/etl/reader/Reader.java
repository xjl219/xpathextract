package com.xujl.etl.reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Reader {
	public	String read(){
		try {
			
	
		 Stream<String> lines = Files.lines(Paths.get("e:/gs.html"));
         StringBuffer body =new StringBuffer();
         lines.forEach(body::append);
         return body.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
}
