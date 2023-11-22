package com.compilador.recife.ast;

import java.util.Map;

public class PointerAssign implements ASTNode {

	private String name;
	private String variable;
	private String typePointer;
	private String typeVar;
	
	public PointerAssign(String name, String variable,String typePointer,String typeVar) {
		this.name = name;
		this.variable = variable;
		this.typePointer = typePointer;
		this.typeVar = typeVar;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		// Verifica se a variável está na tabela de símbolos
		if (!symbolTable.containsKey(name)) {
			throw new RuntimeException("Pointer '" + name + "' not declared.");
		}
		if (!symbolTable.containsKey(variable)) {
			throw new RuntimeException("Variable '" + variable + "' not declared.");
		}
		if (!isCompatiblevariable(typePointer, typeVar)) {
			throw new RuntimeException("Pointer type is different from variable type");
		}
		
		symbolTable.put(name, variable);
		 
		return null;
	}

	private boolean isCompatiblevariable(String typePointer2, String typeVar2) {
		if ("int*".equals(typePointer) && "int".equals(typeVar)) {
			return true;
		} else if ("double*".equals(typePointer) && "double".equals(typeVar)) {
			return true;
		} else if ("char*".equals(typePointer) && "char".equals(typeVar)) {
			return true;
		} else if ("bool*".equals(typePointer) && "bool".equals(typeVar)) {
			return true;
		} else if ("string*".equals(typePointer) && "string".equals(typeVar)) {
			return true;
		}
		
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getTypePointer() {
		return typePointer;
	}

	public void setTypePointer(String typePointer) {
		this.typePointer = typePointer;
	}

	public String getTypeVar() {
		return typeVar;
	}

	public void setTypeVar(String typeVar) {
		this.typeVar = typeVar;
	}

}
