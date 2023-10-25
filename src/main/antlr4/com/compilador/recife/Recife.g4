grammar Recife;

program : PROGRAM ID LCURLY sentenca* RCURLY func*;

sentenca : declaration | assign_op | print | loop 
            | decision | read | func | array ;

primary_variables : INT | DOUBLE | CHAR | BOOL | VAR | STRING;

op_relational: EQ | LT | GT | QEQT | LEQT | DIF;

op_arithmetic : PLUS | MINUS | MULT | DIV | MOD; 

increment :  PLUS_PLUS | MINUS_MINUS;

assign_arithmetic : ID ASSING ID op_arithmetic ID 
                    | ID ASSING ID op_arithmetic NUMBER
                    | ID ASSING ID op_arithmetic READ;

compare: ID op_relational ID | ID op_relational NUMBER
        | NUMBER op_relational NUMBER
        | NUMBER op_relational ID; 

pointer: POINTER_INT | POINTER_DOUBLE | POINTER_CHAR 
        | POINTER_BOOL | POINTER_VAR;

declaration : primary_variables ID SEMICOL 
                | pointer ID SEMICOL;

assign_op: ID ASSING NUMBER SEMICOL
            | ID ASSING ID SEMICOL
            | ID ASSING read
            | ID ASSING STRING SEMICOL;


loop: FOR LPAREN assign_op compare SEMICOL ID 
        increment RPAREN LCURLY  sentenca* RCURLY 
        | FOR LPAREN assign_op compare SEMICOL 
            assign_arithmetic RPAREN LCURLY  
            sentenca RCURLY;


decision: IF LPAREN compare RPAREN LCURLY
            sentenca* RCURLY (ELSE LCURLY sentenca* RCURLY)*
            | IF LPAREN compare RPAREN LCURLY
            sentenca* RCURLY (ELSE IF LPAREN compare RPAREN LCURLY
            sentenca* RCURLY)* ELSE LCURLY 
            sentenca* RCURLY;

print : PRINT LPAREN (NUMBER | ID | STR) RPAREN SEMICOL
		|PRINT LPAREN (NUMBER | ID | STR)(PLUS (NUMBER | ID | STR))* RPAREN SEMICOL;

read: READ LPAREN primary_variables RPAREN SEMICOL; 

func: (primary_variables | VOID | ID) ID LPAREN  (primary_variables ID)* (COMMA primary_variables ID)* RPAREN LCURLY sentenca* RCURLY;

array: primary_variables ID LBRACKETS NUMBER RBRACKETS SEMICOL
		| primary_variables ID LBRACKETS NUMBER RBRACKETS LBRACKETS NUMBER RBRACKETS SEMICOL; 


PROGRAM: 'arrocha';
VAR: 'var';
CONST: 'const';
PRINT: 'oia';
READ: 'dizai';
IF: 'se';
ELSE: 'senao';
FOR: 'arrudeia';
VOID: 'void';

INT: 'int';
DOUBLE: 'double';
CHAR: 'char';
BOOL: 'bool';
STRING: 'string';
POINTER_INT: 'int*';
POINTER_DOUBLE: 'double*';
POINTER_CHAR: 'char*';
POINTER_BOOL: 'bool*';
POINTER_VAR: 'var*';
POINTER_STRING: 'string*';

PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';
ASSING: '=';
PLUS_PLUS: '++';
MINUS_MINUS: '--';

AND : '&&' ;
OR : '||' ;
NOT : '!' ;

EQ : '==' ;
LT: '<';
GT: '>';
QEQT: '>=';
LEQT: '<=';
DIF: '!=';

SEMICOL : ';' ;
LPAREN : '(' ;
RPAREN : ')' ;
LCURLY : '{' ;
RCURLY : '}' ;
DQUOTE : '"';
LBRACKETS:'[';
RBRACKETS: ']';
COMMA: ',';

NUMBER : [0-9]+ ;
STR: '"' ~["\r\n]* '"';
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
WS: [ \t\n\r\f]+ -> skip ;
CM: ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;