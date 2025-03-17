#include <stdio.h>
#include <stdlib.h>
#define LETTER 2

char symbol;
char yylval;

typedef struct _node{
	char value;
	struct _node *left, *right;
}node;

void next_symbol(){
	symbol = yylex(); //primeste valoarea pe rand a fiecarui simbol recunoscut de lex
}

node* E();
node* F();
node* T();
node* P();

node* E(){
	node* e=(node*)malloc(sizeof(node));
	e=T();
	if(symbol=='|'){
		next_symbol();
		e->left=e;
		e->right=E();
		e->value='|';
	}
	return e;
}

node* T(){
	node* e=(node*)malloc(sizeof(node));
	e=F();
	if(symbol=='(' || symbol==LETTER){
		e->left=e;
		e->right=T();
		e->value='&';
	}
	return e;
}

node* F(){
	node* e=(node*)malloc(sizeof(node));
	e=P();
	if(symbol=='*'){
		next_symbol();
		e->left=e;
		e->right=NULL;
		e->value='*';
	}
	return e;
}

node* P(){
	node* e=(node*)malloc(sizeof(node));
	if(symbol==LETTER){
		e->left=NULL;
		e->right=NULL;
		e->value=yylval;
		next_symbol();
	}
	else if(symbol=='('){
		next_symbol();
		e=E();
		if(symbol!=')'){
			perror("Nu e inchisa paranteza");
		}
	}
	return e;
}

int main(){
	next_symbol();
	while(symbol != 0){
		node* e=E(); //este Z de unde incep functiile 
		next_symbol();
	}
	return 0;
}
