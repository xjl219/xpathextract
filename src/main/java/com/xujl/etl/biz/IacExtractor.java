package com.xujl.etl.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.xujl.etl.css.etler.*;
import com.xujl.etl.reader.Reader;

public class IacExtractor {
	Reader reader;
	public Reader getReader() {
		return reader;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	public SigleHashMapExtrctor getBaseExt() {
		return baseExt;
	}
	public void setBaseExt(SigleHashMapExtrctor baseExt) {
		this.baseExt = baseExt;
	}
	public MultiHashMapExtractor getGdczExt() {
		return gdczExt;
	}
	public void setGdczExt(MultiHashMapExtractor gdczExt) {
		this.gdczExt = gdczExt;
	}
	SigleHashMapExtrctor baseExt;
	MultiHashMapExtractor gdczExt;
	public void extract(){
		String read = reader.read();
		Map baseInfo=baseExt.map(read);
		System.out.println(baseInfo);
		ArrayList<HashMap<String, Object>> gdczMap = gdczExt.map(read);
		System.out.println(gdczMap);

		
	}

}
