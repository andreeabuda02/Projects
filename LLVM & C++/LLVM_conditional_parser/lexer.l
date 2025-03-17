%{
#define NUMBER 256
#define IF 1
#define ELSE 2
int yylval;
%}

%%

[0-9]+           { yylval = atoi(yytext); printf("NUMBER: %s\n", yytext); return NUMBER; }
"if"             { printf("IF gasit\n"); return IF; }
"else"           { printf("ELSE gasit\n"); return ELSE; }
"+"              { printf("PLUS gasit\n"); return *yytext; }
"-"              { printf("MINUS gasit\n"); return *yytext;  }
"*"              { printf("MULTIPLY gasit\n"); return *yytext;  }
"/"              { printf("DIVIDE gasit\n"); return *yytext;  }
"("              { printf("( gasit\n"); return *yytext;  }
")"              { printf(") gasit\n"); return *yytext;  }
"{"              { printf("{ gasit\n"); return *yytext;  }
"}"              { printf("} gasit\n"); return *yytext;  }
"="              { printf("= gasit\n"); return *yytext;  }
[ \t]+           {}
\n               {}
.                { printf("simbol necunoscut: %s\n", yytext); return yytext[0]; }

%%

