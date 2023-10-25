
package com.compilador.recife;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

	private static final String EXTENSION = "gaia";

	public static void main(String[] args) throws IOException {
		String program = args.length > 1 ? args[1] : "test/test." + EXTENSION;

		System.out.println("Interpreting file " + program);

		RecifeLexer lexer = new RecifeLexer(new ANTLRFileStream(program));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RecifeParser parser = new RecifeParser(tokens);

		RecifeParser.ProgramContext tree = parser.program();

		RecifeCustomVisitor visitor = new RecifeCustomVisitor();
		visitor.visit(tree);

		System.out.println("Interpretation finished");
	}

}
