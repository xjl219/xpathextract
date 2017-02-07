package com.xujl.etl.css.etler;

import java.util.HashMap;
import java.util.Objects;

import com.xujl.etl.BlockExtractor;
import com.xujl.etl.Mapping;

import jodd.jerry.Jerry;
import jodd.jerry.Jerry.JerryParser;
import jodd.lagarto.dom.LagartoDOMBuilder;

public abstract class CssSelectorExtracter<RULES,R> implements Mapping<String, R> {
 protected	Class<R> clazz;
 protected	RULES rules;
 protected JerryParser jerryParser = Jerry.jerry();
 protected LagartoDOMBuilder dom = (LagartoDOMBuilder) jerryParser.getDOMBuilder();
 protected BlockExtractor blockEx;
	public BlockExtractor getBlockEx() {
	return blockEx;
}
public void setBlockEx(BlockExtractor blockEx) {
	this.blockEx = blockEx;
}
	public CssSelectorExtracter(Class<R> clazz,RULES rules){
		dom.enableHtmlMode();
		this.clazz=clazz;
		this.rules=rules;
	}
	protected HashMap<String, Object> extract(final Jerry doc, HashMap<String, String> ruleMap) {
		HashMap<String, Object> info= new HashMap<>();
		ruleMap.forEach((k,v)->{
			Jerry $ = doc.$(v);
			info.put(k, $.text());
		});
		return info;
	}
	protected Block getBlock(String html){
		return Objects.isNull(blockEx) ? Block.create(html) : blockEx.map(html);
	}
	
}
