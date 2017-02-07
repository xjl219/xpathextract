package com.xujl.etl.css.etler;

import java.util.List;

import com.xujl.etl.BlockExtractor;

public class SigleBlockExtracter extends CssSelectorExtracter<List<String>, Block> implements BlockExtractor {
	Block block=Block.create("");

	public SigleBlockExtracter(List<String> rules) {
		super(Block.class, rules);
	}

	@Override
	public Block map(String in) {
		final Block parser = getBlock(in);
		if(rules.isEmpty()) return parser;
		rules.forEach(rule->block.append(parser.$(rule).htmlAll(true)));
		return block;
	}

}
