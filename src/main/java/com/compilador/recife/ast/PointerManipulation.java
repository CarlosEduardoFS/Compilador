package com.compilador.recife.ast;

import java.util.Map;

public class PointerManipulation implements ASTNode {

	private String pointerName;
	private ASTNode value;
	private String referencedVariable;
	
	public PointerManipulation(String pointerName, ASTNode value,String referencedVariable) {
		this.pointerName = pointerName;
		this.value = value;
		this.referencedVariable = referencedVariable;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		// Verifica se a variável está na tabela de símbolos
		if (!symbolTable.containsKey(pointerName)) {
			throw new RuntimeException("Pointer '" + pointerName + "' not declared.");
		}
		if (!symbolTable.containsKey(referencedVariable)) {
			throw new RuntimeException("referencedVariable '" + referencedVariable + "' not declared.");
		}
		// Avalia o valor da expressão à direita e atribui à variável
		Object resultValue = value.execute(symbolTable);
		Object resultreferencedVariable = symbolTable.get(referencedVariable);
		
		if (!isTypeCompatible(resultValue, resultreferencedVariable)) {
			throw new RuntimeException("Pointer type is different from referencedVariable type");
		}
		
		symbolTable.put(referencedVariable, resultValue);
		 
		return null;
	}

	private boolean isTypeCompatible(Object resultValue, Object resultreferencedVariable) {
		if (resultValue instanceof Integer && resultreferencedVariable instanceof Integer ) {
			return true;
		} else if (resultValue instanceof Float && resultreferencedVariable instanceof Float) {
			return true;
		} else if (resultValue instanceof Character && resultreferencedVariable instanceof Character) {
			return true;
		} else if (resultValue instanceof Boolean && resultreferencedVariable instanceof Boolean) {
			return true;
		} else if (resultValue instanceof String && resultreferencedVariable instanceof String) {
			return true;
		}
		return false;
	}

	public String getPointerName() {
		return pointerName;
	}

	public void setPointerName(String pointerName) {
		this.pointerName = pointerName;
	}

	public ASTNode getValue() {
		return value;
	}

	public void setValue(ASTNode value) {
		this.value = value;
	}

	public String getReferencedVariable() {
		return referencedVariable;
	}

	public void setrefeRencedVariable(String referencedVariable) {
		this.referencedVariable = referencedVariable;
	}

}
