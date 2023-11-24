package com.compilador.recife.ast;

import java.util.Map;
import java.util.List;

public class ProcedureDeclaration implements ASTNode {

	private String procedureName;
    private List<ASTNode> body;
    private Map<String, Object> localSymbolTable;
    private List<Parameter> parameterList;

    public ProcedureDeclaration(String procedureName, List<ASTNode> body, Map<String, Object> localSymbolTable, List<Parameter> parameterList) {
        this.procedureName = procedureName;
        this.body = body;
        this.localSymbolTable = localSymbolTable;
        this.parameterList = parameterList;
    }

    @Override
    public Object execute(Map<String, Object> symbolTable) {
    	if (parameterList != null) {
	    	for (Parameter i : parameterList) {
	    		localSymbolTable.put(i.getName(), i.getType());
	    	}
    	}
        symbolTable.put(procedureName, this); // Adiciona a procedimento à tabela de símbolos

        return null; // Procedimento não retorna valor
    }
    
    public List<ASTNode> getBody() {
        return body;
    }

    public Map<String, Object> getLocalSymbolTable() {
        return localSymbolTable;
    }

	public List<Parameter> getParameterList() {
		return parameterList;
	}
    
}
