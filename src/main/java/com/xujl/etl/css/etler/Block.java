package com.xujl.etl.css.etler;

import java.util.ArrayList;
import java.util.List;

import  jodd.jerry.Jerry;
import jodd.lagarto.dom.DOMBuilder;
import jodd.lagarto.dom.LagartoDOMBuilder;
import jodd.lagarto.dom.Node;
import jodd.lagarto.dom.NodeSelector;

public class Block extends Jerry {
	public static Block create(String html){
		 JerryParser jerryParser = Jerry.jerry();
		 DOMBuilder domBuilder =  jerryParser.getDOMBuilder();
		return new  Block(domBuilder,domBuilder.parse(html));
	} 
	public Block(Jerry parent, List<Node> nodeList){
		super(parent,nodeList);
	}
	public Block(Jerry parent, Node[] nodes1, Node[] nodes2){
		super(parent,nodes1,nodes2);
	}
	public Block(Jerry parent, Node... nodes){
		super(parent,nodes);
	}
	Block(DOMBuilder builder, Node... nodes){

		super(builder,nodes);
	}
	public LagartoDOMBuilder getDOMBuilder(){
		LagartoDOMBuilder builder=(LagartoDOMBuilder)super.builder;
		return builder;
	}
	public Node[] getNodes(){
		
		return nodes;
	}
	public Block merge(Node[] addNodes) {
		
		return new Block(this,getNodes(), addNodes);
	}
	public Block merge(Block block) {
		
		return new Block(this,nodes, block.nodes);
	}
@Override
	public Block find(String cssSelector) {
		final List<Node> result = new NodeList();

		if (nodes.length > 0) {
			for (Node node : nodes) {
				NodeSelector nodeSelector = createNodeSelector(node);
				List<Node> filteredNodes = nodeSelector.select(cssSelector);
				result.addAll(filteredNodes);
			}
		}

		return new Block(this, result);
	}
	private static class NodeList extends ArrayList<Node> {

		private NodeList(int initialCapacity) {
			super(initialCapacity);
		}

		private NodeList() {
		}

		@Override
		public boolean add(Node o) {
			for (Node node : this) {
				if (node == o) {
					return false;
				}
			}
			return super.add(o);
		}
	}
}
