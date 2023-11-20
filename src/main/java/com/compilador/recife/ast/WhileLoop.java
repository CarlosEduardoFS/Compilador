package com.compilador.recife.ast;

import java.util.Map;
import java.util.List;

public class WhileLoop implements ASTNode {
	private List<ASTNode> whileBody;
	private ASTNode logicalExpression;
	
	public WhileLoop(ASTNode logicalExpression, List<ASTNode> whileBody) {
		super();
		this.logicalExpression = logicalExpression;
		this.whileBody = whileBody;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable) {
		while((boolean)logicalExpression.execute(symbolTable)) {
			for(ASTNode node : whileBody) {
				node.execute(symbolTable);
			}
		}
		return null;
	}

}
