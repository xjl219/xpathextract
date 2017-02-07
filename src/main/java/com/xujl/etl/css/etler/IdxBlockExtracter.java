package com.xujl.etl.css.etler;

import java.util.ArrayList;

import com.xujl.etl.BlockExtractor;
import com.xujl.etl.Mapping;

public class IdxBlockExtracter implements Mapping<String,Block>,BlockExtractor{
	/*
	 * 占位符
	 */
	String[] hoders;
	//start index
	Integer[] starts;
	//incream
	Integer[] steps;
	
	String rules[];
	
	public String[] getRules() {
		return rules;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}

	public String[] getHoders() {
		return hoders;
	}

	public void setHoders(String[] hoders) {
		this.hoders = hoders;
	}

	public Integer[] getStarts() {
		return starts;
	}

	public void setStarts(Integer[] starts) {
		this.starts = starts;
	}

	public Integer[] getSteps() {
		return steps;
	}

	public void setSteps(Integer[] steps) {
		this.steps = steps;
	}
	Block block=Block.create("");
	


	@Override
	public Block map(String in) {
		Block create = Block.create(in);
	has:	while(true)
		for (int i = 0; i < hoders.length; i++) {
			String rule = rules[i].replaceAll(hoders[i], starts[i]+"");
//			System.out.println(rule);
			starts[i]+=steps[i];
			Block find = create.find(rule);
//			System.out.println(find.size());
			if(find.size() <1) break has;
			block=block.merge(find);
			
		}
		
	
		return block;
	}

	
	
	public static IdxBlockExtracter test(){
		IdxBlockExtracter rule = new IdxBlockExtracter();
		rule.hoders=new String[]{"_"};
		rule.rules=new String[]{"#yearStrTitle:eq(_),#center:eq(_)"};
		rule.starts=new Integer[]{0};
		rule.steps=new Integer[]{1};
		return rule;
	}

	
}
