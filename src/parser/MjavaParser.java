package parser;

import java.io.*;

import lexier.MjavaLexier;
import lexier.Token;
import lexier.Token.TokenType;
import parser.SyntaxNode;

public class MjavaParser {
	Token token;//���浱ǰToken
	int line = 1;
	MjavaLexier lexier;
	private FileWriter out = null;//�����Ϣ
	
	//���ڵ�
	private SyntaxNode statement;
	private SyntaxNode if_Statement;
	private SyntaxNode while_Statement;
	private SyntaxNode print_Statement;
	private SyntaxNode varAssigan_Statement;
	private SyntaxNode arrayAssign_Statement;
	
	//���ʽ�ڵ�
	private SyntaxNode int_Expression;
	private SyntaxNode true_Expression;
	private SyntaxNode false_Expression;
	private SyntaxNode identifier_Expression;
	private SyntaxNode this_Expression;
	private SyntaxNode newArray_Expression;
	private SyntaxNode new_Expression;
	private SyntaxNode not_Expression;
	private SyntaxNode brace_Expression;
	
	//�����ڵ�
	private SyntaxNode mainClass;
	private SyntaxNode classDeclaration;
	private SyntaxNode varDeclaration;
	private SyntaxNode methodDeclaration;
	
	//���ڵ�
	private SyntaxNode goal;
	
	
	/**
	 * @param input ��������ļ�·��
	 * @param lexierOutPut �ʷ�����������ļ�
	 * @param parserOutput �﷨����������ļ�
	 */
	public MjavaParser(String input, String lexierOutPut, String parserOutput) {
		lexier = new MjavaLexier(input, lexierOutPut);
		FileWriter writer= null;
		try {
			writer = new FileWriter(parserOutput);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		out = writer;
	}
	
	/**
	 * ���ܣ����������Ϣ���ļ���
	 * @param message ������Ϣ
	 * @throws IOException
	 */
	private void syntaxError(String message) throws IOException {
		out.write("\n>>>");
		out.write("Syntax error "+message+" at line "+lexier.line+"\n");
	}
	
    /**
     * ���ܣ�match������ɵ�ǰToken������Token��ƥ��
     * 			  ���Tokenƥ���������һ��token����֮���unexpected token����
     * @param expected ������Token
     * @throws IOException
     */
    public void match(TokenType expected) throws IOException {
    	if(token.getType().equals(expected)) {
    		token = lexier.nextToken();   		
    	}else {
    		syntaxError("unexpected token: ");
    		out.write(token.getToken());
    	}
    }
    
    public SyntaxNode newDeclarationNode(Declaration type) throws IOException {
    	SyntaxNode newNode = new SyntaxNode();
    	newNode.nodeType = NodeType.Declaration;
    	switch(type) {
    	case Goal:
    		newNode.declaration = Declaration.Goal;
    		break;
    	case MainClass:
    		newNode.declaration = Declaration.MainClass;
    		break;
    	case ClassDeclaration:
    		newNode.declaration = Declaration.ClassDeclaration;
    		break;
    	case VarDeclaration:
    		newNode.declaration = Declaration.VarDeclaration;
    		break;
    	case MethodDeclaration:
    		newNode.declaration = Declaration.MethodDeclaration;
    		break;
    	default:
    		syntaxError("Unexpected token at line: "+line);
    		token = lexier.nextToken();
    		break;
    	}
    	return newNode;
    }
    
    public SyntaxNode newStatementNode() throws IOException {
    	SyntaxNode newNode = new SyntaxNode();
    	newNode.nodeType = NodeType.Statement;
    	switch(token.getType()) {
    	case KEY_WHILE:
    		newNode.statement = Statement.While_Statement;
    		break;
    	case KEY_IF:
    		newNode.statement = Statement.If_Statement;
    		break;
    	case KEY_PRINTLIN:
    		newNode.statement = Statement.Print_Statement;
    		break;
    	case ASSIGN:
    		newNode.statement = Statement.VarAssign_Statement;
    		break;
    	default:
    		syntaxError("Unexpected token at line: "+line);
    		token = lexier.nextToken();
    		break;
    	}
    	return newNode;
    }
    
    public SyntaxNode newExpressionNode(Expression type) throws IOException {
    	SyntaxNode newNode = new SyntaxNode();
    	newNode.nodeType = NodeType.Expression;
    	switch(type) {
    	case Int_Expression:
    		newNode.expression = Expression.Int_Expression;
    		break;
    	case True_Expression:
    		newNode.expression = Expression.True_Expression;
    		break;
    	case False_Expression:
    		newNode.expression = Expression.False_Expression;
    		break;
    	case Identifier_Expression:
    		newNode.expression = Expression.Identifier_Expression;
    		break;
    	case This_Expression:
    		newNode.expression = Expression.This_Expression;
    		break;
    	case NewArray_Expression:
    		newNode.expression = Expression.NewArray_Expression;
    		break;
    	case New_Expression:
    		newNode.expression = Expression.New_Expression;
    		break;
    	case Not_Expression:
    		newNode.expression = Expression.Not_Expression;
    		break;
    	case Brace_Expression:
    		newNode.expression = Expression.Brace_Expression;
    		break;
    	default:
    		syntaxError("Unexpected token at line: "+line);
    		token = lexier.nextToken();
    		break;
    	}
    	return newNode;
    }
    
    private SyntaxNode goal() throws IOException {
    	SyntaxNode goal_Node = newDeclarationNode(Declaration.Goal);
    	SyntaxNode mainClassChild = mainClass();
    	goal_Node.childList.add(mainClassChild);
    	while(token.getType() != TokenType.EOF) {
    		SyntaxNode classDeclarationChild = newDeclarationNode(Declaration.ClassDeclaration);
    		goal_Node.childList.add(classDeclarationChild);
    	}
		return goal_Node;
    }
    
    private SyntaxNode mainClass() {
    	SyntaxNode mainClassNode = new SyntaxNode();
    	mainClassNode.nodeType = NodeType.Declaration;
    	mainClassNode.declaration = Declaration.MainClass;
    	
    	if(token.getToken().equals("class")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	match(TokenType.IDENTIFIER);
    	
    	if(token.getToken().equals("{")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("public")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("static")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("void")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("main")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("(")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("String")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("[")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("]")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	match(TokenType.IDENTIFIER);
    	
    	if(token.getToken().equals(")")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	if(token.getToken().equals("{")) {
    		token = lexier.nextToken();
    	}else {
    		out.write("Unexpected token: "+token.getToken()+" at line "+lexier.line+"\n");
    		token = lexier.nextToken();
    	}
    	
    	SyntaxNode statementChild = statement();
    }
}
