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
class GenericASTNode {
public:
    virtual ~GenericASTNode() = default;
    virtual void toString() {};
    virtual Value *codegen() = 0;
};

class NumberASTNode : public GenericASTNode {
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
        }
        return nullptr; // Unsupported operation
    }
};


void CodeGenTopLevel(unique_ptr<GenericASTNode> AST_Root)
{
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
    }

    auto Filename = "output.ll";
    std::error_code EC;
    raw_fd_ostream dest(Filename, EC);

    if (EC) {
        errs() << "Could not open file: " << EC.message();
        return;
    }

    F->print(errs());
    F->print(dest);
    F->eraseFromParent();
}

//===----------------------------------------------------------------------===//
// Parser
//===----------------------------------------------------------------------===//
int yylex();
int symbol;
extern int yylval;

void next_symbol()
{
	symbol = yylex();
}


unique_ptr<GenericASTNode> Z();
unique_ptr<GenericASTNode> E_AS(); // Addition and subtraction
unique_ptr<GenericASTNode> E_MDR(); // Multiplication, Division and Remainder
unique_ptr<GenericASTNode> T();

unique_ptr<GenericASTNode> Z(){
	return E_AS();
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
int main()
{
    InitializeModule();
    next_symbol();
    CodeGenTopLevel(Z());
    return 0;
}
