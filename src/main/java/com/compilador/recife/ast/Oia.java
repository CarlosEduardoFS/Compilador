package com.compilador.recife.ast;

import java.util.Map;

public class Oia implements ASTNode{
	
	private ASTNode expression;
	
	
	public Oia(ASTNode expression) {
		super();
		this.expression = expression;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		System.out.println(expression.execute(symbolTable));
		return null;
	}

}
