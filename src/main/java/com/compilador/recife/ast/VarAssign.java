package com.compilador.recife.ast;

import java.util.Map;

public class VarAssign implements ASTNode {

	private String name;
	private ASTNode value;
	private String declaredType;

	public VarAssign(String name, ASTNode value, String declaredType) {
		this.name = name;
		this.value = value;
		this.declaredType = declaredType;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		// Verifica se a variável está na tabela de símbolos
		if (!symbolTable.containsKey(name)) {
			throw new RuntimeException("Variable '" + name + "' not declared.");
		}
		// Avalia o valor da expressão à direita e atribui à variável
		Object result = value.execute(symbolTable);

		// Verifica a compatibilidade de tipos
		if (!isTypeCompatible(declaredType, result)) {
			throw new RuntimeException("Incompatible types in variable assignment for '" + name + "'.");
		}
		
		
		symbolTable.put(name, result);
		

		return result;
	}

	private boolean isTypeCompatible(String declaredType, Object assignedValue) {
		if ("int".equals(declaredType) && assignedValue instanceof Integer) {
			return true;
		} else if ("double".equals(declaredType) && assignedValue instanceof Float) {
			return true;
		} else if ("char".equals(declaredType) && assignedValue instanceof Character) {
			return true;
		} else if ("bool".equals(declaredType) && assignedValue instanceof Boolean) {
			return true;
		} else if ("string".equals(declaredType) && assignedValue instanceof String) {
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public ASTNode getValue() {
		return value;
	}

	public String getDeclaredType() {
		return declaredType;
	}

	@Override
	public String toString() {
		return "VarAssign [name=" + name + ", value=" + value + "]";
	}
}
