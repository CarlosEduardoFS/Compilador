package com.compilador.recife.ast;

import java.util.Map;

public class VarDecl implements ASTNode {

	private String name;
	private String type;

	public VarDecl(String name, String type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		// Verifica se a variável já está na tabela de símbolos
		if (symbolTable.containsKey(name)) {
			throw new RuntimeException("Variable '" + name + "' already declared.");
		}

		// Adiciona a variável à tabela de símbolos com um valor inicial nulo
		symbolTable.put(name, insertDefaltuValue(type));

		return null;
	}
	
	private Object insertDefaltuValue(String type) {
		if ("int".equals(type)) {
			return 0;
		} else if ("double".equals(type)) {
			return 0.0;
		} else if ("char".equals(type)) {
			return 'a';
		} else if ("bool".equals(type)) {
			return false;
		} else if ("string".equals(type)) {
			return " ";
		}
		return false;
	}
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "VarDecl [name=" + name + ", type=" + type + "]";
	}

}
