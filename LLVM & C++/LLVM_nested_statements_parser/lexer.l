%{
#define NUMBER 256
#define IF 1
#define ELSE 2
#define WHILE 3

char identifier[255]; 
int yylval;
%}

%%

"var"           { printf("VAR gasit\n"); return 'v'; }
"assign"        { printf("ASSIGN gasit\n"); return 'a'; }
"if"            { printf("IF gasit\n"); return IF; }
"else"          { printf("ELSE gasit\n"); return ELSE; }
"while"         { printf("WHILE gasit\n"); return WHILE; }
[0-9]+          { yylval = atoi(yytext); printf("NUMBER: %s\n", yytext); return NUMBER; }
"+"             { printf("PLUS gasit\n"); return *yytext; }
"-"             { printf("MINUS gasit\n"); return *yytext; }
"*"             { printf("MULTIPLY gasit\n"); return *yytext; }
"/"             { printf("DIVIDE gasit\n"); return *yytext; }
"%"             { printf("MODULUS gasit\n"); return *yytext; }
"("             { printf("( gasit\n"); return *yytext; }
")"             { printf(") gasit\n"); return *yytext; }
"{"             { printf("{ gasit\n"); return *yytext; }
"}"             { printf("} gasit\n"); return *yytext; }
"="             { printf("= gasit\n"); return *yytext; }
";"             { printf("; gasit\n"); return *yytext; }
[a-zA-Z_][a-zA-Z0-9_]* {
                    strncpy(identifier, yytext, sizeof(identifier) - 1);
                    identifier[sizeof(identifier) - 1] = '\0';
                    printf("IDENTIFIER: %s\n", yytext);
                    return 'i'; 
                }
\\\n            { printf("Continuare linie\n"); }
[ \t]+          {}     
\n              {}     
. 		{ printf("simbol necunoscut: %s\n", yytext); exit(1); }


%%

