package com.xujl.etl.css.etler;

import java.util.List;

import com.xujl.etl.BlockExtractor;

public class MultiBlockExtracter extends SigleBlockExtracter  implements BlockExtractor {

	public MultiBlockExtracter(List<String> rules) {
		super(rules);
	}
	@Override
	public Block map(String in) {
		final Block parser = getBlock(in);
		if(rules.isEmpty()) return parser;
		rules.forEach(rule->block=block.merge(parser.find(rule).getNodes()));
		return block;
	}
}
