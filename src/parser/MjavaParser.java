package parser;

import java.io.*;

import lexier.MjavaLexier;
import lexier.Token;
import lexier.Token.TokenType;
import parser.SyntaxNode;

public class MjavaParser {
	Token token;//���浱ǰToken
	Token preToken;//ǰһ��Token
	Token ppreToken;//ǰ����Token
	Token pppreToken;//ǰ����Token
	
	int line = 1;
	MjavaLexier lexier;
	private FileWriter out = null;//�����Ϣ
	
	//���ڵ�
//	private SyntaxNode statement() {
//		return null;
//	}
//	private SyntaxNode if_Statement() {
//		return null;
//	}
//	private SyntaxNode while_Statement() {
//		return null;
//	}
//	private SyntaxNode print_Statement() {
//		return null;
//	}
//	private SyntaxNode varAssigan_Statement() {
//		return null;
//	}
//	private SyntaxNode arrayAssign_Statement() {
//		return null;
//	}

	
	//���ʽ�ڵ�
	private SyntaxNode expression() {
		return null;
	}
	private SyntaxNode int_Expression() {
		return null;
	}
	private SyntaxNode true_Expression() {
		return null;
	}
	private SyntaxNode false_Expression() {
		return null;
	}
	private SyntaxNode identifier_Expression() {
		return null;
	}
	private SyntaxNode this_Expression() {
		return null;
	}
	private SyntaxNode newArray_Expression() {
		return null;
	}
	private SyntaxNode new_Expression() {
		return null;
	}
	private SyntaxNode not_Expression() {
		return null;
	}
	private SyntaxNode brace_Expression() {
		return null;
	}
	
	//�����ڵ�
//	private SyntaxNode mainClass() {
//		return null;
//	}
//	private SyntaxNode classDeclaration() {
//		return null;
//	}
//	private SyntaxNode varDeclaration() {
//		return null;
//	}
//	private SyntaxNode methodDeclaration() {
//		return null;
//	}
	
	//���ڵ�
//	private SyntaxNode goal;
	
	
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
	
	//���ߺ������岿��
	
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
    public boolean match(TokenType expected) throws IOException {
    	if(token.getType().equals(expected)) {
    		getNextToken();
    		return true;
    	}else {
    		syntaxError("unexpected token: ");
    		out.write(token.getToken());
    		getNextToken();
    		return false;
    	}
    }
    
    /**
     * ���˵�ǰtoken���ַ�����
     */
    public void pushBackToken() {
    	lexier.pushBackWhitSpace();
    	int length = token.getToken().length();
    	for(int i = 0; i<length; i++) {
    		lexier.pushBack();
    	}
    	token = preToken;
		preToken = ppreToken;
		ppreToken = pppreToken;
    }
    
    public void getNextToken() throws IOException {
    	pppreToken = ppreToken;
    	ppreToken = preToken;
    	preToken = token;
    	token = lexier.nextToken();
    }
    
    /**
     * �жϵ�ǰtoken�Ƿ��Ƕ��������type
     * @return
     * @throws IOException
     */
    private boolean matchType() throws IOException {
    	if(token.getToken().equals("int")) {
    		getNextToken();
    		if(token.getToken().equals("[")) {
    			getNextToken();
    			if(match(TokenType.RBRACKET)) {
    				return true;//int []
    			}else {
    				return false;
    			}
    		}else {//���û��ƥ�䵽��[������ô����ǰtoken���˵��ַ�����
    			pushBackToken();//����ǰ��Token���˵��ַ�����
    			return true;//int
    		}
    	}else if(token.getToken().equals("boolean")) {
    		getNextToken();
			return true;//boolean
    	}else if(token.getType() == TokenType.IDENTIFIER) {
    		getNextToken();
			return true;//identifier
    	}else {
    		getNextToken();
    		syntaxError("unexpected type");
    		return false;
    	}
    }
    
    /*
     * �ݹ��½��������岿��
     */
    
    
//    public SyntaxNode newDeclarationNode(Declaration type) throws IOException {
//    	SyntaxNode newNode = new SyntaxNode();
//    	newNode.nodeType = NodeType.Declaration;
//    	switch(type) {
//    	case Goal:
//    		newNode.declaration = Declaration.Goal;
//    		break;
//    	case MainClass:
//    		newNode.declaration = Declaration.MainClass;
//    		break;
//    	case ClassDeclaration:
//    		newNode.declaration = Declaration.ClassDeclaration;
//    		break;
//    	case VarDeclaration:
//    		newNode.declaration = Declaration.VarDeclaration;
//    		break;
//    	case MethodDeclaration:
//    		newNode.declaration = Declaration.MethodDeclaration;
//    		break;
//    	default:
//    		syntaxError("Unexpected token at line: "+line);
//    		preToken = token;
//    		token = lexier.nextToken();
//    		break;
//    	}
//    	return newNode;
//    }
    
//    public SyntaxNode newExpressionNode(Expression type) throws IOException {
//    	SyntaxNode newNode = new SyntaxNode();
//    	newNode.nodeType = NodeType.Expression;
//    	switch(type) {
//    	case Int_Expression:
//    		newNode.expression = Expression.Int_Expression;
//    		break;
//    	case True_Expression:
//    		newNode.expression = Expression.True_Expression;
//    		break;
//    	case False_Expression:
//    		newNode.expression = Expression.False_Expression;
//    		break;
//    	case Identifier_Expression:
//    		newNode.expression = Expression.Identifier_Expression;
//    		break;
//    	case This_Expression:
//    		newNode.expression = Expression.This_Expression;
//    		break;
//    	case NewArray_Expression:
//    		newNode.expression = Expression.NewArray_Expression;
//    		break;
//    	case New_Expression:
//    		newNode.expression = Expression.New_Expression;
//    		break;
//    	case Not_Expression:
//    		newNode.expression = Expression.Not_Expression;
//    		break;
//    	case Brace_Expression:
//    		newNode.expression = Expression.Brace_Expression;
//    		break;
//    	default:
//    		syntaxError("Unexpected token at line: "+line);
//   	 	preToken = token;
//    		token = lexier.nextToken();
//    		break;
//    	}
//    	return newNode;
//    }
    
    private SyntaxNode goal() throws IOException {
    	SyntaxNode goal_Node = new SyntaxNode();
    	goal_Node.declaration = Declaration.Goal;
    	SyntaxNode mainClassChild = mainClass();
    	goal_Node.childList.add(mainClassChild);
    	while(token.getType() != TokenType.EOF) {
    		SyntaxNode classDeclarationChild = classDeclaration();
    		goal_Node.childList.add(classDeclarationChild);
    	}
		return goal_Node;
    }
    
    private SyntaxNode mainClass() throws IOException {
    	SyntaxNode mainClassNode = new SyntaxNode();
    	mainClassNode.nodeType = NodeType.Declaration;
    	mainClassNode.declaration = Declaration.MainClass;
    	
    	match(TokenType.KEY_CLASS);
    	match(TokenType.IDENTIFIER);
    	match(TokenType.LBRACES);
    	match(TokenType.KEY_PUBLIC);
    	match(TokenType.KEY_STATIC);
    	match(TokenType.KEY_VOID);
    	match(TokenType.KEY_MAIN);	
    	match(TokenType.LPAREN);    	
    	match(TokenType.KEY_STRING);    	
    	match(TokenType.LBRACKET);    	
    	match(TokenType.RBRACKET);    	
    	match(TokenType.IDENTIFIER);   	
    	match(TokenType.RPAREN);   	
    	match(TokenType.LBRACES);   	
    	SyntaxNode statementChild = statement();
    	
    	mainClassNode.childList.add(statementChild);  	
    	match(TokenType.RBRACES);    	
    	match(TokenType.RBRACES);
		return mainClassNode;
    }
    
    private SyntaxNode classDeclaration() throws IOException {
    	SyntaxNode classDeclaratioNode = new SyntaxNode();
    	classDeclaratioNode.nodeType = NodeType.Declaration;
    	classDeclaratioNode.declaration = Declaration.ClassDeclaration;
    	
    	match(TokenType.KEY_CLASS);
    	
    	match(TokenType.IDENTIFIER);
    	
    	if(match(TokenType.KEY_EXTENDS)) {
    		match(TokenType.IDENTIFIER);
    	}
    	
    	match(TokenType.LBRACES);
    	
    	while(!(token.getToken().equals("public") || token.getToken().equals("}"))) {
    		SyntaxNode varDeclarationNode = varDeclaration();
    		classDeclaratioNode.childList.add(varDeclarationNode);
    	}
    	
    	while(token.getToken().equals("public")) {
    		SyntaxNode methodDeclarationNode = methodDeclaration();
    		classDeclaratioNode.childList.add(methodDeclarationNode);
    	}
    	
    	match(TokenType.RBRACES);
    	
		return classDeclaratioNode;
	}
    
    private SyntaxNode varDeclaration() throws IOException {
    	SyntaxNode varDeclaratioNode = new SyntaxNode();
    	varDeclaratioNode.nodeType = NodeType.Declaration;
    	varDeclaratioNode.declaration = Declaration.VarDeclaration;
    	
    	if(token.getToken().equals("int")) {
    		getNextToken();
    		if(token.getToken().equals("[")) {
    			getNextToken();
    			if(match(TokenType.RBRACKET)){
    				varDeclaratioNode.expType = var_Type.Int_Array;
    			}else {
    				syntaxError(" Excepte ']' ");
    				pushBackToken();//������]��token���˵��ַ�����
    			}
    		}else {//���û��ƥ�䵽��[������ô����ǰtoken���˵��ַ�����
    			varDeclaratioNode.expType = var_Type.Int;
    			pushBackToken();//����ǰ��Token���˵��ַ�����
    		}
    	}else if(token.getToken().equals("boolean")) {
    		getNextToken();
    		varDeclaratioNode.expType = var_Type.Boolean;
    	}else if(token.getType() == TokenType.IDENTIFIER) {
    		getNextToken();
    		varDeclaratioNode.expType = var_Type.Identifier;
    	}else {
    		getNextToken();
    		syntaxError("unexpected VarType");
    	}
    	
    	match(TokenType.IDENTIFIER);
    	match(TokenType.SEMICOLON);
		return varDeclaratioNode;
	}
    
    private SyntaxNode methodDeclaration() throws IOException {
    	SyntaxNode methodDeclaratioNode = new SyntaxNode();
    	methodDeclaratioNode.nodeType = NodeType.Declaration;
    	methodDeclaratioNode.declaration = Declaration.MethodDeclaration;
    	
    	match(TokenType.KEY_PUBLIC);
    	matchType();
    	match(TokenType.IDENTIFIER);
    	match(TokenType.LPAREN);
    	if(matchType()) {
    		match(TokenType.IDENTIFIER);
    		while(match(TokenType.COMMA)) {
    			matchType();
    			match(TokenType.IDENTIFIER);
    		}
    	}
    	match(TokenType.RPAREN);
    	match(TokenType.LBRACES);
    	while(matchType()) {
    		pushBackToken();//��type��token���ˣ����ڴ���VarDeclaration�ڵ�
    		SyntaxNode varDeclarationChildNode = varDeclaration();
    		methodDeclaratioNode.childList.add(varDeclarationChildNode);
    	}
		return methodDeclaratioNode;
	}
    
    private SyntaxNode statement() throws IOException {
    	SyntaxNode newNode = null;
    	switch(token.getType()) {
    	case LBRACES:
    		newNode = statementSeries();
    		match(TokenType.RBRACES);
    		return newNode;
    	case KEY_WHILE:
    		newNode = while_Statement();
    		return newNode;
    	case KEY_IF:
    		newNode = if_Statement();
    		return newNode;
    	case KEY_PRINTLIN:
    		newNode = print_Statement();
    		return newNode;
    	case ASSIGN:
    		newNode = assigan_Statement();
    		return newNode;
    	default:
    		syntaxError("Unexpected token at line: "+lexier.line);
    		getNextToken();
    		return null;
    	}
    }
    
	private SyntaxNode statementSeries() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.Series_Statement;
		
		match(TokenType.LBRACES); 
		while(!token.getToken().equals("}")) {//����}��seriesNode�޹أ����Խ�match����statement()��
			SyntaxNode childNode = statement();
			if(childNode != null) {
				newNode.childList.add(childNode);
			}
		}
		return newNode;
	}
	
	private SyntaxNode if_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.If_Statement;
		
		match(TokenType.KEY_IF);
		match(TokenType.LPAREN);
		SyntaxNode expNode = expression();
		newNode.childList.add(expNode);
		match(TokenType.RPAREN);
		SyntaxNode stateChild = statement();
		if(stateChild != null) {
			newNode.childList.add(stateChild);
		}
		match(TokenType.KEY_ELSE);
		SyntaxNode _stateChild = statement();
		if(_stateChild != null) {
			newNode.childList.add(_stateChild);
		}
		return newNode;
	}
	
	private SyntaxNode while_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.While_Statement;
		
		match(TokenType.KEY_WHILE);
		match(TokenType.LPAREN);
		SyntaxNode expNode = expression();
		newNode.childList.add(expNode);
		match(TokenType.RPAREN);
		SyntaxNode stateChild = statement();
		if(stateChild != null) {
			newNode.childList.add(stateChild);
		}
		return newNode;
	}
	
	private SyntaxNode print_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.Print_Statement;
		
		match(TokenType.KEY_PRINTLIN);
		match(TokenType.LPAREN);
		SyntaxNode expNode = expression();
		newNode.childList.add(expNode);
		match(TokenType.RPAREN);
		match(TokenType.SEMICOLON);
		return newNode;
	}
	
	private SyntaxNode assigan_Statement() throws IOException {
		SyntaxNode assignNode = null;
		pushBackToken();
		switch (token.getType()) {
		case KEY_INT:
			assignNode = varAssigan_Statement();
			return assignNode;
		case RBRACKET:

			assignNode = arrayAssign_Statement();
			return assignNode;
		default:
			syntaxError("Unexpected token at line: "+lexier.line);
			getNextToken();
    		return null;
		}
	}
	
	private SyntaxNode varAssigan_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.VarAssign_Statement;
		
		match(TokenType.IDENTIFIER);
		match(TokenType.ASSIGN);
		SyntaxNode expNode = expression();
		newNode.childList.add(expNode);
		match(TokenType.SEMICOLON);
		return newNode;
	}
	
	private SyntaxNode arrayAssign_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.ArrayAssign_Statement;
		
		pushBackToken();
		pushBackToken();
		match(TokenType.IDENTIFIER);
		match(TokenType.LBRACKET);
		SyntaxNode expNode = expression();
		newNode.childList.add(expNode);
		match(TokenType.RBRACKET);
		match(TokenType.ASSIGN);
		SyntaxNode _expNode = expression();
		newNode.childList.add(_expNode);
		match(TokenType.SEMICOLON);
		return newNode;
	}
}
