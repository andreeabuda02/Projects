//lex recunoaste ceea ce avem in sir - fiecare caracter pe rand, daca e litera sau operator

%option noyywrap
%{//ceea ce face legatura cu fisierul c
#define LETTER 2
extern char yylval; //e cu extern pentru ca se ia din fisierul c
%}
%% 
    //ce facem cu litera gasita
[a-z]	{ yylval = *yytext;
    	  return LETTER;
    	}
[*|()\n]	return *yytext;
%%
