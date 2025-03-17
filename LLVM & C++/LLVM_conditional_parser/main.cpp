#include <cstdio>
#include <cstdlib>
#include <memory>

#include "llvm/IR/IRBuilder.h"
#include "llvm/IR/Value.h"
#include "llvm/IR/NoFolder.h"
#include "llvm/Support/raw_ostream.h"

using namespace std;
using namespace llvm;

#define NUMBER 256
#define IF 1
#define ELSE 2

static unique_ptr<LLVMContext> TheContext;
static unique_ptr<IRBuilder<NoFolder>> Builder;
static unique_ptr<Module> TheModule;

static void InitializeModule()
{
    TheContext = std::make_unique<LLVMContext>();
    TheModule = std::make_unique<Module>("MyModule", *TheContext);
    Builder = std::make_unique<IRBuilder<NoFolder>>(*TheContext);
}

//===----------------------------------------------------------------------===//
// AST nodes
//===----------------------------------------------------------------------===//
class GenericASTNode
{
public:
    virtual ~GenericASTNode() = default;
    virtual void toString(){};
    virtual Value *codegen() = 0;
};

class NumberASTNode : public GenericASTNode
{
    int Val;

public:
    NumberASTNode(int Val)
    {
        this->Val = Val;
    }

    void toString() {
        printf("Valoare: %d",Val);
    }
    Value* codegen() { 
        return ConstantInt::get(*TheContext, APInt(32,Val,true));
    }
};

class BinaryExprAST : public GenericASTNode {
    char Op;
    unique_ptr<GenericASTNode> LHS, RHS;

public:
    BinaryExprAST(char Op, unique_ptr<GenericASTNode> LHS, unique_ptr<GenericASTNode> RHS)
      : Op(Op), LHS(std::move(LHS)), RHS(std::move(RHS)) {}

    void toString() {  
        LHS->toString();
        printf(" %c ", Op);
        RHS->toString();
    }
    Value* codegen() { 
        Value *L = LHS->codegen();
        Value *R = RHS->codegen();
        if (Op == '+') {
            return Builder->CreateAdd(L, R, "addtmp");
        } else if (Op == '-') {
            return Builder->CreateSub(L, R, "subtmp");
        } else if (Op == '*') {
            return Builder->CreateMul(L, R, "multmp");
        } else if (Op == '/') {
            return Builder->CreateSDiv(L, R, "divtmp");
        } else if (Op == '%') {
            return Builder->CreateSRem(L, R, "modtmp");
        } else if (Op == '=') { // Tratarea operatorului ==
            return Builder->CreateICmpEQ(L, R, "eqtmp");
    	}
        return nullptr; // Unsupported operation
    }
};


class IfStatementAST : public GenericASTNode
{
    unique_ptr<GenericASTNode> Cond, TrueExpr, FalseExpr;

public:
    IfStatementAST(unique_ptr<GenericASTNode> Cond, unique_ptr<GenericASTNode> TrueExpr, unique_ptr<GenericASTNode> FalseExpr)
    {
        //this->Cond = move(Cond);
        //this->TrueExpr = move(TrueExpr);
        //this->FalseExpr = move(FalseExpr);
        
        this->Cond = std::move(Cond);
	this->TrueExpr = std::move(TrueExpr);
	this->FalseExpr = std::move(FalseExpr);


        // only if statement
        if (this->FalseExpr == nullptr)
        {
            //daca nu avem si ramura de else
            this->FalseExpr = std::make_unique<NumberASTNode>(0);
        }   
    }

    void toString() { 
    	printf("if(");
    	Cond->toString();
    	printf("){");
    	TrueExpr->toString();
    	printf("}");
    	if (FalseExpr) {
        	printf("else{");
        	FalseExpr->toString();
        	printf("}");
    	}
    }
    
    Value *codegen() { 
    	
    	Function *functiaCurenta = Builder->GetInsertBlock()->getParent();
    	BasicBlock *thenB = BasicBlock::Create(*TheContext, "blocAdevarat", functiaCurenta);
    	BasicBlock *elseB = BasicBlock::Create(*TheContext, "blocFals", functiaCurenta);
    	BasicBlock *mergeB = BasicBlock::Create(*TheContext, "blocFinal", functiaCurenta);
    	
    	Value* zeroValue = ConstantInt::get(*TheContext, APInt(32, 0, true));
	
    	Value *conditieValue = Cond->codegen();
    	if (!conditieValue){
    		return nullptr;
    	}
    	
    	Value *rezultatComparatie = Builder->CreateICmpNE(conditieValue, zeroValue, "conditie");
    	
    	Builder->CreateCondBr(rezultatComparatie, thenB, elseB);
    	Builder->SetInsertPoint(thenB);
    	Value *rezultatAdevarat = TrueExpr->codegen();
    	if (!rezultatAdevarat){
    		return nullptr;
    	}
    	Builder->CreateBr(mergeB);

    	functiaCurenta->insert(functiaCurenta->end(), elseB);
    	Builder->SetInsertPoint(elseB);
    	Value *rezultatFals;
    	if (FalseExpr) {
        	rezultatFals = FalseExpr->codegen();
    	} else {
        	rezultatFals = ConstantInt::get(*TheContext, APInt(32, 0, true));
   	}
    	Builder->CreateBr(mergeB);

    	functiaCurenta->insert(functiaCurenta->end(), mergeB);
    	Builder->SetInsertPoint(mergeB);

    	PHINode *nodPHI = Builder->CreatePHI(Type::getInt32Ty(*TheContext), 2, "rezultatIf");
    	nodPHI->addIncoming(rezultatAdevarat, thenB);
    	nodPHI->addIncoming(rezultatFals, elseB);

    	return nodPHI;
    }
};

void CodeGenTopLevel(unique_ptr<GenericASTNode> AST_Root)
{
    if (!AST_Root) {
        errs() << "Error: AST root is null. Cannot generate code.\n";
        return;
    }

    // Create an anonymous function with no parameters
    vector<Type *> ArgumentsTypes(0);
    FunctionType *FT = FunctionType::get(Type::getInt32Ty(*TheContext), ArgumentsTypes, false);
    Function *F = Function::Create(FT, Function::ExternalLinkage, "main", TheModule.get());

    // Create a label 'entry' and set it to the current position in the builder
    BasicBlock *BB = BasicBlock::Create(*TheContext, "entry", F);
    Builder->SetInsertPoint(BB);

    // Generate the code for the body of the function and return the result
    if (Value *RetVal = AST_Root->codegen()) {
        Builder->CreateRet(RetVal);
    } else {
        errs() << "Error: Code generation for AST root failed.\n";
        return;
    }

    auto Filename = "output.ll";
    std::error_code EC;
    raw_fd_ostream dest(Filename, EC);

    if (EC)
    {
        errs() << "Could not open file: " << EC.message();
        return;
    }

    F->print(errs());
    F->print(dest);
    dest.flush();
    F->eraseFromParent();
}

//===----------------------------------------------------------------------===//
// Parser
//===----------------------------------------------------------------------===//
int yylex();
int symbol;
extern int yylval;

unique_ptr<GenericASTNode> Z();
unique_ptr<GenericASTNode> E_AS();  // Addition and Subtraction
unique_ptr<GenericASTNode> E_MDR(); // Multiplication, Division and Remainders
unique_ptr<GenericASTNode> E_IF();
unique_ptr<GenericASTNode> T();

void next_symbol()
{
    symbol = yylex();
}

unique_ptr<GenericASTNode> Z(){
	if(symbol == IF){
		return E_IF();
	}
	else{
		return E_AS();
	}
}


unique_ptr<GenericASTNode> E_AS(){
	unique_ptr<GenericASTNode> e_lhs;
	e_lhs = E_MDR();
	while(symbol == '+' || symbol == '-' ){
		char operatie = symbol;
		next_symbol();
		unique_ptr<GenericASTNode> radacina = E_MDR();
		e_lhs = std::make_unique<BinaryExprAST>(operatie, std::move(e_lhs), std::move(radacina));
	}
	return e_lhs;
	
}

unique_ptr<GenericASTNode> E_MDR(){
	unique_ptr<GenericASTNode> e_lhs;
	e_lhs = T();
	while(symbol == '*' || symbol == '/' || symbol == '%'){
		char operatie = symbol;
		next_symbol();
		unique_ptr<GenericASTNode> radacina = T();
		e_lhs = std::make_unique<BinaryExprAST>(operatie, std::move(e_lhs), std::move(radacina));
	}
	return e_lhs;
	
}

unique_ptr<GenericASTNode> E_IF() {
    if (symbol == IF) {
        next_symbol();
    } else {
        return nullptr;
    }

    if (symbol != '(') {
        return nullptr;
    }

    next_symbol();
    unique_ptr<GenericASTNode> conditie = E_AS();
    if (!conditie || symbol != ')') {
        return nullptr;
    }

    next_symbol();

    if (symbol != '{') {
        return nullptr;
    }
    next_symbol();

    unique_ptr<GenericASTNode> adevarat = E_AS();
    if (!adevarat || symbol != '}') {
        return nullptr;
    }

    next_symbol();

    unique_ptr<GenericASTNode> fals = nullptr;
    if (symbol == ELSE) {
        next_symbol();
        if (symbol != '{') {
            return nullptr;
        }
        next_symbol();

        fals = E_AS();
        if (!fals || symbol != '}') {
            return nullptr;
        }
        next_symbol();
    }
    return std::make_unique<IfStatementAST>(std::move(conditie), std::move(adevarat), std::move(fals));
}


unique_ptr<GenericASTNode> T(){
	unique_ptr<GenericASTNode> e;
	if(symbol == NUMBER){
		int valoare = yylval;
		next_symbol();
		return std::make_unique<NumberASTNode> (valoare);
	}
	else if(symbol == '('){
		next_symbol();
		e = E_AS();
		if(symbol!=')'){
			return nullptr;
		}
		else{
		        next_symbol();
		}
		return e;
		
	}
	return nullptr;
}

//===----------------------------------------------------------------------===//
// main function
//===----------------------------------------------------------------------===//
int main() {
    InitializeModule();

    next_symbol();
    unique_ptr<GenericASTNode> astRoot = Z();
    if (!astRoot) {
        printf("Eroare: Nodul rădăcină returnat de Z() este null.\n");
        return 1;
    }
    CodeGenTopLevel(std::move(astRoot));

    printf("Programul s-a terminat cu succes.\n");
    return 0;
}

