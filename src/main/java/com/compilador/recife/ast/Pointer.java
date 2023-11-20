package com.compilador.recife.ast;

import java.util.Map;

public class Pointer implements ASTNode{
	
	private String type;
	private ASTNode referencedVariableName;
	
	public Pointer(String type, ASTNode referencedVariableName) {
		this.type = type;
		this.referencedVariableName = referencedVariableName;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ASTNode getReferencedVariableName() {
		return referencedVariableName;
	}

	public void setReferencedVariableName(ASTNode referencedVariableName) {
		this.referencedVariableName = referencedVariableName;
	}

}
