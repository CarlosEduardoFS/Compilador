package com.compilador.recife.ast;

import java.util.Map;
import java.util.Scanner;

public class Read implements ASTNode {

	private String variableName;
	private String variableType;

    public Read(String variableName, String variableType) {
    	this.variableName = variableName;
    	this.variableType = variableType;
    }

    @Override
    public Object execute(Map<String, Object> symbolTable) {
    	// Verifica se a variável está na tabela de símbolos
        if (!symbolTable.containsKey(variableName)) {
            throw new RuntimeException("Variable '" + variableName + "' not declared.");
        }
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a value for variable '" + variableName + "' of type '" + variableType + "': ");
        String userInput = scanner.nextLine();

        Object parsedValue = parseInput(userInput, variableType);

        symbolTable.put(variableName, parsedValue);

        scanner.close();

        return null;
    }
    
    private Object parseInput(String userInput, String targetType) {
        try {
            if ("int".equals(targetType)) {
                return Integer.parseInt(userInput);
            } else if ("double".equals(targetType)) {
            	return Double.parseDouble(userInput);
            } else if ("char".equals(targetType)) {
            	return userInput.charAt(0);
            } else if ("bool".equals(targetType)) {
                // Verifica se a entrada é um valor booleano
                if ("true".equalsIgnoreCase(userInput) || "false".equalsIgnoreCase(userInput)) {
                    return Boolean.parseBoolean(userInput);
                } else {
                    throw new RuntimeException("Invalid input for boolean variable '" + variableName + "'. Expected 'true' or 'false'.");
                }
            } else if ("string".equals(targetType)) {
                return userInput;
            } else {
                throw new RuntimeException("Unsupported type: " + targetType);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid input for variable '" + variableName + "'. Expected type: " + targetType);
        }
    }

}
