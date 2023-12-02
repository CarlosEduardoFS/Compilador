package com.compilador.recife.ast;

import java.util.List;
import java.util.Map;

public class ProcedureCall implements ASTNode {
	private String procedureName;
	private List<ASTNode> argumentList;

	public ProcedureCall(String procedureName, List<ASTNode> argumentList) {
		this.procedureName = procedureName;
		this.argumentList = argumentList;
	}

	@SuppressWarnings("unused")
	@Override
	public Object execute(Map<String, Object> symbolTable) {
		// Recupera o procedimento da tabela de símbolos
		ProcedureDeclaration procedureDeclaration = (ProcedureDeclaration) symbolTable.get(procedureName);

		// Verifica se o procedimento existe
		if (procedureDeclaration == null) {
			throw new RuntimeException("Procedimento '" + procedureName + "' não declarado.");
		}

		// Cria um novo escopo local para a execução da função
		Map<String, Object> localSymbolTable = procedureDeclaration.getLocalSymbolTable();

		if (argumentList != null && localSymbolTable.size() != argumentList.size())
			throw new RuntimeException(
					"Quantidade de parametros inválidos passadas na chamada do procedimento'" + procedureName + "'.");

		Object[] keys = localSymbolTable.keySet().toArray();

		for (int i = 0; i < keys.length; i++) {
			if (!isTypeCompatible((String) localSymbolTable.get(keys[0].toString()),
					argumentList.get(i).execute(symbolTable)))
				throw new RuntimeException("Os tipos de dados passados como parametors são incompativeis!");

			localSymbolTable.put(keys[i].toString(), argumentList.get(i).execute(symbolTable));
		}

		// Executa o corpo do procedimento
		for (ASTNode node : procedureDeclaration.getBody()) {
			node.execute(localSymbolTable);
		}

		// Retorna null, já que o procedimento não retorna valor
		return null;

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

}
