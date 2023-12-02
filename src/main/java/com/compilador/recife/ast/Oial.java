package com.compilador.recife.ast;

import java.util.Map;

public class Oial implements ASTNode{
	
	private ASTNode expression;
	
	
	public Oial(ASTNode expression) {
		super();
		this.expression = expression;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		System.out.println(expression.execute(symbolTable));
		return null;
	}

}
