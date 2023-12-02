grammar Recife;

@parser::header{ 
	import java.util.Map;
	import java.util.HashMap;
	import java.util.List;
	import java.util.ArrayList;
	import com.compilador.recife.ast.*;
}

@parser::members {
	Map<String, Object> symbolTable = new HashMap<String, Object>();
}

program : PROGRAM ID LCURLY 
		{
			List<ASTNode> body = new ArrayList<ASTNode>();
			Map<String, Object> symbolTable = new HashMap<String, Object>();
		}
		(sentence {body.add($sentence.node);})* 
		RCURLY (procedure_declaration {body.add($procedure_declaration.node);})*
		{
			List<ASTNode> listProcedureCall = new ArrayList<>();
			for(ASTNode n : body) {
				if (n instanceof ProcedureCall)
					listProcedureCall.add(n);
				else
					n.execute(symbolTable);
			}
			
			for(ASTNode n : listProcedureCall) {
				n.execute(symbolTable);
			}
		};

sentence returns [ASTNode node]: 
	  print {$node = $print.node;}
	| read_statement {$node = $read_statement.node;}
	| var_decl {$node = $var_decl.node;}
	| pointer_decl {$node = $pointer_decl.node;}
	| pointer_manipulation {$node = $pointer_manipulation.node;}
	| var_assign {$node = $var_assign.node;}
	| pointer_assign {$node = $pointer_assign.node;}
	| conditional {$node = $conditional.node;}
	| wilhe_loop {$node = $wilhe_loop.node;}
	| procedure_call {$node = $procedure_call.node;};

var: 'int' |'double' | 'char'| 'bool' | 'string';

print returns [ASTNode node]:PRINT LPAREN value=logicalExpression RPAREN SEMICOL 
								{$node = new Oia($value.node);}
								|PRINTL LPAREN value=logicalExpression RPAREN SEMICOL 
								{$node = new Oial($value.node);} ; 

read_statement returns [ASTNode node]: READ LPAREN varName=ID RPAREN SEMICOL{
	String declaredType = (String) symbolTable.get($varName.text);
	$node = new Read($varName.text,declaredType);
};
							
primaryExpression returns [ASTNode node]: 
    logicalNotExpression {$node = $logicalNotExpression.node;}
    | INTEGER_LITERAL {$node = new Constant(Integer.parseInt($INTEGER_LITERAL.text));}
    | BOOLEAN_LITERAL {$node = new Constant(Boolean.parseBoolean($BOOLEAN_LITERAL.text));}
    | CHAR_LITERAL {$node = new Constant($CHAR_LITERAL.text.charAt(1));}
    | STRING_LITERAL {$node = new Constant($STRING_LITERAL.text.substring(1, $STRING_LITERAL.text.length() - 1));}
    | DOUBLE_LITERAL {$node = new Constant(Float.parseFloat($DOUBLE_LITERAL.text));}
    | ID {$node = new VarRef($ID.text);}
    | LPAREN expr=logicalExpression RPAREN {$node = $expr.node;}
    ; 
logicalNotExpression returns [ASTNode node]: 
    NOT operand=primaryExpression {$node = new LogicalNot($operand.node);}
    ;
   
var_decl returns [ASTNode node]: var ID SEMICOL {
	symbolTable.put($ID.text, $var.text);
	$node = new VarDecl($ID.text, $var.text);
};

pointer_var: 'int*' |'double*' | 'char*'| 'bool*' | 'string*';
pointer_decl returns [ASTNode node]: POINTER pointer_var ID SEMICOL {
	symbolTable.put($ID.text, $pointer_var.text);
	$node = new VarDecl($ID.text, $pointer_var.text);
};

var_assign returns [ASTNode node]: ID ASSING logicalExpression SEMICOL {
	String declaredType = (String) symbolTable.get($ID.text);
	$node = new VarAssign($ID.text, $logicalExpression.node,declaredType);
};

pointer_assign returns [ASTNode node]: POINTER pointer=ID ASSING variable=ID SEMICOL {
	String declaredTypePointer = (String) symbolTable.get($pointer.text);
	String declaredTypeVar = (String) symbolTable.get($variable.text);
	symbolTable.put($pointer.text, $variable.text);
	$node = new PointerAssign($pointer.text, $variable.text,declaredTypePointer,declaredTypeVar);
};


value_pointer returns [ASTNode node]: INTEGER_LITERAL {$node = new Constant(Integer.parseInt($INTEGER_LITERAL.text));}
    | BOOLEAN_LITERAL {$node = new Constant(Boolean.parseBoolean($BOOLEAN_LITERAL.text));}
    | CHAR_LITERAL {$node = new Constant($CHAR_LITERAL.text.charAt(1));}
    | STRING_LITERAL {$node = new Constant($STRING_LITERAL.text.substring(1, $STRING_LITERAL.text.length() - 1));}
    | DOUBLE_LITERAL {$node = new Constant(Float.parseFloat($DOUBLE_LITERAL.text));};

pointer_manipulation returns [ASTNode node]: POINTER ID ASSING value_pointer SEMICOL {
	String pointerValue = (String) symbolTable.get($ID.text);
	$node = new PointerManipulation($ID.text, $value_pointer.node,pointerValue);
};

logicalExpression returns [ASTNode node]:
	  logicalOrExpression {$node = $logicalOrExpression.node;}
	| logicalAndExpression {$node = $logicalAndExpression.node;}
	| NOT logicalExpression {$node = new LogicalNot($logicalExpression.node);}
	;

logicalOrExpression returns [ASTNode node]: 
    logicalAndExpression {$node = $logicalAndExpression.node;}
    (OR right=logicalAndExpression {$node = new LogicalOr($node, $right.node);})*
    ;

logicalAndExpression returns [ASTNode node]: 
    equalityExpression {$node = $equalityExpression.node;}
    (AND right=equalityExpression {$node = new LogicalAnd($node, $right.node);})*
    ;

equalityExpression returns [ASTNode node]: 
    relationalExpression {$node = $relationalExpression.node;}
    (EQUALITY_OPERATOR right=relationalExpression {$node = new EqualityExpression($node, $right.node, $EQUALITY_OPERATOR.text);})*
    ;

relationalExpression returns [ASTNode node]: 
    additiveExpression {$node = $additiveExpression.node;}
    (RELATIONAL_OPERATOR right=additiveExpression {$node = new RelationalExpression($node, $right.node, $RELATIONAL_OPERATOR.text);})*
    ;

additiveExpression returns [ASTNode node]: 
    multiplicativeExpression {$node = $multiplicativeExpression.node;}
    (ADDITIVE_OPERATOR right=multiplicativeExpression {$node = new AdditiveExpression($node, $right.node, $ADDITIVE_OPERATOR.text);})*
    ;

multiplicativeExpression returns [ASTNode node]: 
    unaryExpression {$node = $unaryExpression.node;}
    (MULTIPLICATIVE_OPERATOR right=unaryExpression {$node = new MultiplicativeExpression($node, $right.node, $MULTIPLICATIVE_OPERATOR.text);})*
    ;
  
unaryExpression returns [ASTNode node]: 
    ADDITIVE_OPERATOR operand=unaryExpression {$node = new UnaryExpression($ADDITIVE_OPERATOR.text, $operand.node);}
    | primaryExpression {$node = $primaryExpression.node;}
    ;

conditional returns [ASTNode node]: IF LPAREN logicalExpression RPAREN
	{
		List<ASTNode> body = new ArrayList<ASTNode>();
	}
	LCURLY (s1 = sentence {body.add($s1.node);})* 
	{
		$node = new If($logicalExpression.node, body, null);
	}
	RCURLY
	(ELSE
	{
		List<ASTNode> elseBody = new ArrayList<ASTNode>();
	}
	LCURLY (s2 = sentence {elseBody.add($s2.node);})* RCURLY
	{
		$node = new If($logicalExpression.node, body, elseBody);
	})*;

wilhe_loop returns [ASTNode node]: WHILE LPAREN logicalExpression RPAREN
	{
		List<ASTNode> body = new ArrayList<ASTNode>();
	}
	LCURLY (s1 = sentence {body.add($s1.node);})* RCURLY
	{
		$node = new WhileLoop($logicalExpression.node, body);
	};

procedure_declaration returns [ASTNode node]: VOID ID LPAREN parameterList RPAREN LCURLY
		{
			List<ASTNode> body = new ArrayList<ASTNode>();
			Map<String, Object> localSymbolTable = new HashMap<String, Object>();
			List<Parameter> parameterList = $parameterList.list;
		}
			(s = sentence {body.add($s.node);})*  RCURLY
			{$node = new ProcedureDeclaration($ID.text, body, localSymbolTable,parameterList);}
		|VOID ID LPAREN RPAREN LCURLY
		{
			List<ASTNode> body = new ArrayList<ASTNode>();
			Map<String, Object> localSymbolTable = new HashMap<String, Object>();
		}
			(s = sentence {body.add($s.node);})* RCURLY
			{$node = new ProcedureDeclaration($ID.text, body, localSymbolTable, null);};

parameterList returns [List<Parameter> list]: 
		{List<Parameter> params = new ArrayList<Parameter>();}
		(p = parameter {params.add($p.param);} (COMMA p = parameter {params.add($p.param);})*)
		{$list = params;};

parameter returns [Parameter param]: 
		var ID {$param = new Parameter($var.text, $ID.text);};

procedure_call returns [ASTNode node]: ID LPAREN argumentList RPAREN SEMICOL
			{$node = new ProcedureCall($ID.text, $argumentList.list);} 
		| ID LPAREN  RPAREN SEMICOL
			{$node = new ProcedureCall($ID.text, null);};

argumentList returns [List<ASTNode> list]: 
		{List<ASTNode> args = new ArrayList<ASTNode>();}
		(e = logicalExpression {args.add($e.node);} (COMMA e = logicalExpression {args.add($e.node);})*)
		{$list = args;};
			
PROGRAM: 'arrocha';
CONST: 'const';
PRINT: 'oia';
PRINTL: 'oial';
READ: 'dizai';
IF: 'se';
ELSE: 'senao';
WHILE: 'arrudeia';
VOID: 'void';
POINTER: 'pointer';

ADDITIVE_OPERATOR: '+' | '-';
MULTIPLICATIVE_OPERATOR: '*' | '/';
MOD: '%';
ASSING: '=';
PLUS_PLUS: '++';
MINUS_MINUS: '--';

AND : '&&' ;
OR : '||' ;
NOT : '!' ;

EQUALITY_OPERATOR: '==' | '!=';
RELATIONAL_OPERATOR: '>' | '<' | '>=' | '<=';


SEMICOL : ';' ;
LPAREN : '(' ;
RPAREN : ')' ;
LCURLY : '{' ;
RCURLY : '}' ;
DQUOTE : '"';
LBRACKETS:'[';
RBRACKETS: ']';
COMMA: ',';

INTEGER_LITERAL: [0-9]+;
BOOLEAN_LITERAL: 'true' | 'false';
CHAR_LITERAL: '\'' ~["'\r\n'] '\'';
STRING_LITERAL: '"' ~["\r\n"]* '"';
DOUBLE_LITERAL: [0-9]+ '.' [0-9]+;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
WS: [ \t\n\r\f]+ -> skip ;
CM: ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;