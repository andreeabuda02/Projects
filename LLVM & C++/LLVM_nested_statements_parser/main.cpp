#include <cstdio>
#include <cstdlib>
#include <memory>
#include <map>

#include "llvm/IR/IRBuilder.h"
#include "llvm/IR/Value.h"
#include "llvm/IR/NoFolder.h"
#include "llvm/Support/raw_ostream.h"

using namespace std;
using namespace llvm;

#define NUMBER 256
#define IF 1
#define ELSE 2
#define WHILE 3

static unique_ptr<LLVMContext> TheContext;
static unique_ptr<IRBuilder<NoFolder>> Builder;
static unique_ptr<Module> TheModule;

static map<string, AllocaInst *> allocatedVariables;

// Creează o variabilă globală pentru formatul de printf
GlobalVariable* createGlobalFormatString(Module &M, const std::string &str, const std::string &name) {
    Constant *formatStr = ConstantDataArray::getString(M.getContext(), str, true);
    auto *globalVar = new GlobalVariable(
        M, formatStr->getType(), true, GlobalValue::PrivateLinkage, formatStr, name);
    return globalVar;
}

// Definește funcția printf în modul LLVM
void definePrintfFunction(Module &M) {
    FunctionType *printfType = FunctionType::get(
        IntegerType::getInt32Ty(M.getContext()),
        PointerType::get(Type::getInt8Ty(M.getContext()), 0),
        true // variadic
    );
    Function::Create(printfType, Function::ExternalLinkage, "printf", M);
}


GlobalVariable *PrintfFormatStr;
void InitializeModule() {
    TheContext = std::make_unique<LLVMContext>();
    TheModule = std::make_unique<Module>("MyModule", *TheContext);
    Builder = std::make_unique<IRBuilder<NoFolder>>(*TheContext);

    // Creează șirul de format
    PrintfFormatStr = createGlobalFormatString(*TheModule, "%d\n", "formatStr");

    // Definește funcția printf
    definePrintfFunction(*TheModule);
}


/*
static void InitializeModule()
{
    TheContext = std::make_unique<LLVMContext>();
    TheModule = std::make_unique<Module>("MyModule", *TheContext);
    Builder = std::make_unique<IRBuilder<NoFolder>>(*TheContext);
}*/

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

class StatementASTNode : public GenericASTNode
{
    unique_ptr<GenericASTNode> node;
    unique_ptr<GenericASTNode> nextNode;

public:
    StatementASTNode(unique_ptr<GenericASTNode> node, unique_ptr<GenericASTNode> nextNode)
    {
        this->node = std::move(node);
        this->nextNode = std::move(nextNode);
    }

void toString() {
    printf("Statement:\n");
    node->toString();
    if (nextNode) {
        printf("; ");
        nextNode->toString();
    }
}


Value* codegen() {
    //Value* currentVal = node->codegen(); 
    //if (nextNode) {
      //  return nextNode->codegen();
    //}
    //return currentVal;
    Value* currentVal = node->codegen();
    if (nextNode) {
        return nextNode->codegen();
    }
    // Adăugarea returnării explicite a ultimei valori evaluate
    Builder->CreateRet(currentVal);
    return currentVal;
}

};

class NumberASTNode : public GenericASTNode {
    int Val;

public:
    NumberASTNode(int Val) : Val(Val) {}

    void toString() { printf("Valoare: %d", Val); }
    Value* codegen() {
        return ConstantInt::get(*TheContext, APInt(32, Val, true));
    }
};


class VariableReadASTNode : public GenericASTNode {
    string name;

public:
    VariableReadASTNode(char name[]) : name(name) {}

    void toString() {
        printf("Citeste variabila: %s\n", name.c_str());
    }

    Value* codegen() {
        if (allocatedVariables.find(name) == allocatedVariables.end()) {
            fprintf(stderr, "Error: Variabila %s nu a fost declarata.\n", name.c_str());
            return nullptr;
        }
        AllocaInst* varPointer = allocatedVariables[name];
        return Builder->CreateLoad(Type::getInt32Ty(*TheContext), varPointer, name);
    }
};


class VariableDeclarationASTNode : public GenericASTNode {
    string name;
    unique_ptr<GenericASTNode> initValue;  // valoare opțională

public:
    VariableDeclarationASTNode(char name[], unique_ptr<GenericASTNode> initValue = nullptr)
        : name(name), initValue(std::move(initValue)) {}

    void toString() {
        printf("Declararea variabilei: %s", name.c_str());
        if (initValue) {
            printf(" = ");
            initValue->toString();
        }
        printf("\n");
    }

    Value* codegen() {
        AllocaInst* varPointer = Builder->CreateAlloca(Type::getInt32Ty(*TheContext), nullptr, name);
        allocatedVariables[name] = varPointer;

        if (initValue) {
            Value* initVal = initValue->codegen();
            if (!initVal) return nullptr;
            Builder->CreateStore(initVal, varPointer);
        }
        return varPointer;
    }
};


class VariableAssignASTNode : public GenericASTNode {
    string varName;
    unique_ptr<GenericASTNode> value;

public:
    VariableAssignASTNode(const string& varName, unique_ptr<GenericASTNode> value)
    : varName(varName), value(std::move(value)) {}


    void toString() {
        printf("Asignarea variabilei: %s = ", varName.c_str());
        value->toString();
    }

    Value* codegen() {
	    printf("Generare cod pentru asignare: %s = ", varName.c_str());
	    value->toString();
	    printf("\n");

	    if (allocatedVariables.find(varName) == allocatedVariables.end()) {
		fprintf(stderr, "Eroare: Variabila %s nu este declarata.\n", varName.c_str());
		return nullptr;
	    }

	    AllocaInst* varPointer = allocatedVariables[varName];
	    Value* val = value->codegen();
	    if (!val) {
		fprintf(stderr, "Eroare: Expresie invalida pentru asignare.\n");
		return nullptr;
	    }

	    printf("Stocare valoare in variabila: %s\n", varName.c_str());
	    Builder->CreateStore(val, varPointer);
	    return val;
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
        printf("%c\n", Op);
        RHS->toString();
    }
    
    Value* codegen() {
    printf("Evaluare expresie binara:\n");
    printf("Stanga: ");
    LHS->toString();
    printf("\nOperator: %c\n", Op);
    printf("Dreapta: ");
    RHS->toString();
    printf("\n");

    Value *L = LHS->codegen();
    Value *R = RHS->codegen();

    if (!L || !R) {
        fprintf(stderr, "Eroare: Operanzi invalizi pentru expresia binara.\n");
        return nullptr;
    }

    Value *Result = nullptr;
    if (Op == '+') {
        Result = Builder->CreateAdd(L, R, "addtmp");
    } else if (Op == '-') {
        Result = Builder->CreateSub(L, R, "subtmp");
    } else if (Op == '*') {
        Result = Builder->CreateMul(L, R, "multmp");
    } else if (Op == '/') {
        Result = Builder->CreateSDiv(L, R, "divtmp");
    } else if (Op == '%') {
        Result = Builder->CreateSRem(L, R, "remtmp");
    }

    printf("Rezultat generat pentru expresia binara.\n");
    return Result;
}

};

class IfStatementAST : public GenericASTNode
{
    unique_ptr<GenericASTNode> Cond, TrueExpr, FalseExpr;

public:
    IfStatementAST(unique_ptr<GenericASTNode> Cond, unique_ptr<GenericASTNode> TrueExpr, unique_ptr<GenericASTNode> FalseExpr) {
        this->Cond = std::move(Cond);
        this->TrueExpr = std::move(TrueExpr);
        this->FalseExpr = std::move(FalseExpr);
        if (this->FalseExpr == nullptr) {
            this->FalseExpr = std::make_unique<NumberASTNode>(0);  
        }
    }

    void toString() {
        printf("If Statement:\n");
        printf("Condition:\n");
        Cond->toString();
        printf("True Branch:\n");
        TrueExpr->toString();
        if (FalseExpr) {
            printf("False Branch:\n");
            FalseExpr->toString();
        } else {
            printf("No False Branch (else) provided.\n");
        }
    }


    Value* codegen() {
    printf("Generare cod pentru instructiunea IF.\n");

    Value *CondVal = Cond->codegen();
    if (!CondVal) {
        fprintf(stderr, "Eroare: Conditie IF invalida.\n");
        return nullptr;
    }

    printf("Conditie evaluata: ");
    Cond->toString();
    printf("\n");

    Value *ZeroValue = ConstantInt::get(*TheContext, APInt(32, 0, true));
    Value *CondResult = Builder->CreateICmpNE(CondVal, ZeroValue, "cond");

    Function *TheFunction = Builder->GetInsertBlock()->getParent();
    BasicBlock *ThenBB = BasicBlock::Create(*TheContext, "then", TheFunction);
    BasicBlock *ElseBB = FalseExpr ? BasicBlock::Create(*TheContext, "else", TheFunction) : nullptr;
    BasicBlock *MergeBB = BasicBlock::Create(*TheContext, "ifcont", TheFunction);

    Builder->CreateCondBr(CondResult, ThenBB, ElseBB ? ElseBB : MergeBB);

    // Ramura TRUE
    Builder->SetInsertPoint(ThenBB);
    printf("Generare cod pentru ramura TRUE.\n");
    Value *ThenVal = TrueExpr->codegen();
    if (!ThenVal) return nullptr;
    Builder->CreateBr(MergeBB);

    // Ramura FALSE, dacă există
    Value *ElseVal = nullptr;
    if (FalseExpr) {
        Builder->SetInsertPoint(ElseBB);
        printf("Generare cod pentru ramura FALSE.\n");
        ElseVal = FalseExpr->codegen();
        Builder->CreateBr(MergeBB);
    }

    // Blocul final
    Builder->SetInsertPoint(MergeBB);
    PHINode *PN = Builder->CreatePHI(Type::getInt32Ty(*TheContext), 2, "iftmp");
    PN->addIncoming(ThenVal, ThenBB);
    if (ElseBB) {
        PN->addIncoming(ElseVal, ElseBB);
    } else {
        PN->addIncoming(ZeroValue, ThenBB);
    }

    return PN;
}

};


class WhileStatementAST : public GenericASTNode
{
    unique_ptr<GenericASTNode> Cond, Body;

public:
    WhileStatementAST(
        unique_ptr<GenericASTNode> Cond,
        unique_ptr<GenericASTNode> Body)
    {
        this->Cond = std::move(Cond);
        this->Body = std::move(Body);
    }

    void toString() { 
        printf("While Statement:\n");
        printf("Condition:\n");
        Cond->toString();
        printf("Body:\n");
        Body->toString();
    }
    
    Value* codegen() {
        Function *TheFunction = Builder->GetInsertBlock()->getParent();

    	BasicBlock *CondBB = BasicBlock::Create(*TheContext, "cond", TheFunction);
    	BasicBlock *BodyBB = BasicBlock::Create(*TheContext, "body", TheFunction);
    	BasicBlock *EndBB = BasicBlock::Create(*TheContext, "end", TheFunction);

    	Builder->CreateBr(CondBB);

    	Builder->SetInsertPoint(CondBB);
    	Value *CondVal = Cond->codegen();
    	if (!CondVal) return nullptr;

    	CondVal = Builder->CreateICmpNE(CondVal, ConstantInt::get(*TheContext, APInt(32, 0)), "cond");
    	Builder->CreateCondBr(CondVal, BodyBB, EndBB);

    	Builder->SetInsertPoint(BodyBB);
    	if (!Body->codegen()) return nullptr;

    	Builder->CreateBr(CondBB);

    	Builder->SetInsertPoint(EndBB);

    	return ConstantInt::get(*TheContext, APInt(32, 0));
}

};

/*
void CodeGenTopLevel(shared_ptr<GenericASTNode> AST_Root)
{
    printf("Generare functie main...\n");

    vector<Type *> ArgumentsTypes(0);
    FunctionType *FT = FunctionType::get(Type::getInt32Ty(*TheContext), ArgumentsTypes, false);
    Function *F = Function::Create(FT, Function::ExternalLinkage, "main", TheModule.get());

    BasicBlock *BB = BasicBlock::Create(*TheContext, "entry", F);
    Builder->SetInsertPoint(BB);

    printf("Generare cod pentru AST...\n");
    if (Value *RetVal = AST_Root->codegen())
    {
        printf("Cod generat cu succes. Returnare valoare...\n");
        Builder->CreateRet(RetVal);
    }
    else
    {
        fprintf(stderr, "Eroare la generarea codului.\n");
        return;
    }

    auto Filename = "output.ll";
    std::error_code EC;
    raw_fd_ostream dest(Filename, EC);

    if (EC)
    {
        errs() << "Nu s-a putut deschide fisierul: " << EC.message();
        return;
    }

    printf("Scriere cod LLVM în fisier %s...\n", Filename);
    F->print(errs());
    F->print(dest);
    F->eraseFromParent();
}
*/

void addPrintfForVariable(const std::string &varName) {
    if (allocatedVariables.find(varName) != allocatedVariables.end()) {
        // Obține funcția `printf`
        Function *printfFunc = TheModule->getFunction("printf");

        // Asigură-te că funcția `printf` există
        if (!printfFunc) {
            fprintf(stderr, "Eroare: Funcția printf nu este definită în modul.\n");
            return;
        }

        // Obține șirul de format
        Value *formatStr = Builder->CreateBitCast(PrintfFormatStr, PointerType::get(Type::getInt8Ty(*TheContext), 0));

        // Încarcă valoarea variabilei
        AllocaInst *varPointer = allocatedVariables[varName];
        Value *varValue = Builder->CreateLoad(Type::getInt32Ty(*TheContext), varPointer, varName);

        // Creează apelul către `printf`
        Builder->CreateCall(printfFunc, {formatStr, varValue});
    } else {
        fprintf(stderr, "Eroare: Variabila %s nu este declarată.\n", varName.c_str());
    }
}




void printAllVariables() {
    for (const auto &var : allocatedVariables) {
        printf("Afișare variabilă: %s\n", var.first.c_str());
        addPrintfForVariable(var.first);
    }
}


void CodeGenTopLevel(shared_ptr<GenericASTNode> AST_Root) {
    printf("Generare functie main...\n");

    vector<Type *> ArgumentsTypes(0);
    FunctionType *FT = FunctionType::get(Type::getInt32Ty(*TheContext), ArgumentsTypes, false);
    Function *F = Function::Create(FT, Function::ExternalLinkage, "main", TheModule.get());

    BasicBlock *BB = BasicBlock::Create(*TheContext, "entry", F);
    Builder->SetInsertPoint(BB);

    printf("Generare cod pentru AST...\n");
    if (Value *RetVal = AST_Root->codegen()) {
        printf("Cod generat cu succes. Afișarea valorii variabilei...\n");

        // Afișează toate variabilele
                addPrintfForVariable("isOdd");
        printAllVariables();

        // Returnează valoarea finală
        Builder->CreateRet(RetVal);
    } else {
        fprintf(stderr, "Eroare la generarea codului.\n");
        return;
    }

    auto Filename = "output.ll";
    std::error_code EC;
    raw_fd_ostream dest(Filename, EC);

    if (EC) {
        errs() << "Nu s-a putut deschide fisierul: " << EC.message();
        return;
    }

    printf("Scriere cod LLVM în fisier %s...\n", Filename);
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
extern char identifier[255];

unique_ptr<GenericASTNode> Z();
unique_ptr<GenericASTNode> E_AS();  // Addition and Subtraction
unique_ptr<GenericASTNode> E_MDR(); // Multiplication, Division and Remainders
unique_ptr<GenericASTNode> E_IF();
unique_ptr<GenericASTNode> E_WHILE();
unique_ptr<GenericASTNode> T();

unique_ptr<GenericASTNode> VAR_DECL();
unique_ptr<GenericASTNode> VAR_ASSIGN();

unique_ptr<GenericASTNode> Statements();



void next_symbol()
{
    symbol = yylex();
}


unique_ptr<GenericASTNode> T() {
    if (symbol == NUMBER) {
        unique_ptr<NumberASTNode> node = std::make_unique<NumberASTNode>(yylval);
        next_symbol();
        return node;
    } else if (symbol == 'i') { // identificatori
        unique_ptr<GenericASTNode> node = std::make_unique<VariableReadASTNode>(identifier);
        next_symbol();
        return node;
    } else if (symbol == '(') {
        next_symbol();
        unique_ptr<GenericASTNode> node1 = E_AS();
        if (symbol == ')') next_symbol();
        return node1;
    }
    return nullptr;
}

unique_ptr<GenericASTNode> E_MDR() {
    unique_ptr<GenericASTNode> node = T();
    while (symbol == '*' || symbol == '/' || symbol == '%') {
        char op = symbol;
        next_symbol();
        unique_ptr<GenericASTNode> nextNode = T();
        node = std::make_unique<BinaryExprAST>(op, std::move(node), std::move(nextNode));
    }
    return node;
}


unique_ptr<GenericASTNode> E_AS() {
    unique_ptr<GenericASTNode> emdr = E_MDR();
    while (symbol == '+' || symbol == '-') {
        char op = symbol;
        next_symbol();
        unique_ptr<GenericASTNode> emdr2 = E_MDR();
        emdr = std::make_unique<BinaryExprAST>(op, std::move(emdr), std::move(emdr2));
    }
    return emdr;
}

unique_ptr<GenericASTNode> E_IF() {
    if (symbol != IF) return nullptr;
    next_symbol();

    if (symbol != '(') return nullptr;
    next_symbol();

    unique_ptr<GenericASTNode> Cond = E_AS();
    if (!Cond || symbol != ')') return nullptr;
    next_symbol();

    if (symbol != '{') return nullptr;
    next_symbol();

    unique_ptr<GenericASTNode> TrueExpr = Statements();
    if (!TrueExpr || symbol != '}') return nullptr;
    next_symbol();

    unique_ptr<GenericASTNode> FalseExpr = nullptr;
    if (symbol == ELSE) {
        next_symbol();
        if (symbol != '{') return nullptr;
        next_symbol();

        FalseExpr = Statements();
        if (!FalseExpr || symbol != '}') return nullptr;
        next_symbol();
    }

    return make_unique<IfStatementAST>(std::move(Cond), std::move(TrueExpr), std::move(FalseExpr));
}

unique_ptr<GenericASTNode> E_WHILE() {
    if (symbol != WHILE) return nullptr;
    next_symbol();

    if (symbol != '(') return nullptr;
    next_symbol();

    unique_ptr<GenericASTNode> Cond = E_AS();
    if (!Cond || symbol != ')') return nullptr;
    next_symbol();

    if (symbol != '{') return nullptr;
    next_symbol();

    unique_ptr<GenericASTNode> Body = Statements();
    if (!Body || symbol != '}') return nullptr;
    next_symbol();

    return make_unique<WhileStatementAST>(std::move(Cond), std::move(Body));
}


unique_ptr<GenericASTNode> VAR_DECL() {
    printf("Parsing declaratie variabila...\n");
    if (symbol != 'v') return nullptr;
    next_symbol(); // Treci peste 'v'

    if (symbol != 'i') {
        fprintf(stderr, "Eroare: Lipsa identificator dupa 'var'.\n");
        return nullptr;
    }
    printf("Variabila declarata: %s\n", identifier);
    unique_ptr<GenericASTNode> decl = make_unique<VariableDeclarationASTNode>(identifier);
    next_symbol();
    return decl;
}


unique_ptr<GenericASTNode> VAR_ASSIGN() {
    if (symbol != 'a') return nullptr;
    next_symbol(); // Treci peste 'a'

    if (symbol != 'i') { 
        fprintf(stderr, "Eroare: Lipsă identificator după 'assign'.\n");
        return nullptr;
    }
    string varName = identifier; 
    next_symbol();

    if (symbol != '=') {
        fprintf(stderr, "Eroare: Lipsă '=' în expresia de atribuire.\n");
        return nullptr;
    }
    next_symbol(); // trec peste =

    unique_ptr<GenericASTNode> value = E_AS();
    if (!value) {
        fprintf(stderr, "Eroare: Expresie invalidă pentru atribuire.\n");
        return nullptr;
    }

    return make_unique<VariableAssignASTNode>(varName, std::move(value));
}


unique_ptr<GenericASTNode> Statement() {
    printf("Parsing statement...\n");
    if (symbol == 'v') {
        printf("Declaratie de variabila detectata+.\n");
        return VAR_DECL();
    }
    if (symbol == 'a') {
        printf("Atribuire detectata.\n");
        return VAR_ASSIGN();
    }
    if (symbol == IF) {
        printf("Instructiune IF detectata.\n");
        return E_IF();
    }
    if (symbol == WHILE) {
        printf("Instructiune WHILE detectata.\n");
        return E_WHILE();
    }
    printf("Parsing expresie...\n");
    return E_AS();
}


unique_ptr<GenericASTNode> Statements() {
    unique_ptr<GenericASTNode> stmt = Statement();
    if (!stmt) {
        fprintf(stderr, "Eroare: Statement invalid.\n");
        return nullptr;
    }

    while (symbol == ';') { // Consuma ;
        next_symbol(); 
        printf("; gasit\n");
        unique_ptr<GenericASTNode> nextStmt = Statement();
        if (!nextStmt) {
            printf("Parsing finalizat, nu mai sunt alte instructiuni.\n");
            break;
        }
        stmt = make_unique<StatementASTNode>(std::move(stmt), std::move(nextStmt));
    }

    if (symbol == '}') { 
        printf("} gasit, se termina blocul curent.\n");
        return stmt;
    }

    if (symbol == 'i') { // Dacă ultimul simbol este un identificator
    	string varName = identifier;
    	next_symbol();
    if (allocatedVariables.find(varName) != allocatedVariables.end()) {
        AllocaInst* varPointer = allocatedVariables[varName];
        return std::make_unique<VariableReadASTNode>((char *)varName.c_str());
    } else {
        fprintf(stderr, "Eroare: Variabila %s nu a fost declarata.\n", varName.c_str());
        return nullptr;
    }
    }


    return stmt;
}




unique_ptr<GenericASTNode> Z() {
    return Statements();
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

