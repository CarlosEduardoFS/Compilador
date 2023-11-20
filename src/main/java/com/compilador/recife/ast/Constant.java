package com.compilador.recife.ast;

import java.util.Map;

public class Constant implements ASTNode {

	 private Object value;

	    public Constant(Object value) {
	        this.value = value;
	    }

	    @Override
	    public Object execute(Map<String, Object> symbolTable) {
	        return value;
	    }

		@Override
		public String toString() {
			return "Constant [value=" + value + "]";
		}

	    
}
