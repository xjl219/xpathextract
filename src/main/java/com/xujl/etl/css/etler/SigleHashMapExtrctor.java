package com.xujl.etl.css.etler;

import java.util.HashMap;

import jodd.jerry.Jerry;

public class SigleHashMapExtrctor extends CssSelectorExtracter<HashMap<String,String>,HashMap<String,Object>> {
	public SigleHashMapExtrctor(HashMap<String,String> rules) {
		
		super((Class<HashMap<String, Object>>) new HashMap<String,Object>().getClass(),rules);
	}

	@Override
	public HashMap<String, Object> map(String in) {
			final Block doc = getBlock(in);
			HashMap<String, Object> info=extract(doc, rules);
		
			return info;
	}

}
