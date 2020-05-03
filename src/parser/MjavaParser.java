package parser;

import java.io.*;
import java.util.ArrayList;

import lexier.MjavaLexier;
import lexier.Token;
import lexier.Token.TokenType;
import parser.SyntaxNode;

public class MjavaParser {
	Token token;//���浱ǰToken
	Token preToken;//ǰһ��Token
	Token ppreToken;//ǰ����Token
	Token pppreToken;//ǰ����Token
	public ArrayList<TokenType> Exp_FirstSet = new ArrayList<TokenType>() {{
		add(TokenType.INTEGERLITERAL);
		add(TokenType.KEY_TRUE);
		add(TokenType.KEY_FALSE);
		add(TokenType.IDENTIFIER);
		add(TokenType.KEY_THIS);
		add(TokenType.KEY_NEW);
		add(TokenType.LOGICAL_NOT);
		add(TokenType.LPAREN);
	}};
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
//	private SyntaxNode expression() {
//		return null;
//	}
//	private SyntaxNode int_Expression() {
//		return null;
//	}
//	private SyntaxNode true_Expression() {
//		return null;
//	}
//	private SyntaxNode false_Expression() {
//		return null;
//	}
//	private SyntaxNode identifier_Expression() {
//		return null;
//	}
//	private SyntaxNode this_Expression() {
//		return null;
//	}
//	private SyntaxNode newArray_Expression() {
//		return null;
//	}
//	private SyntaxNode new_Expression() {
//		return null;
//	}
//	private SyntaxNode not_Expression() {
//		return null;
//	}
//	private SyntaxNode brace_Expression() {
//		return null;
//	}
	
	//��������ڵ�
//	private SyntaxNode a_Statement() {
//		return null;
//	}
//	private SyntaxNode op_A() {
//		return null;
//	}
//	private SyntaxNode exp_A() {
//		return null;
//	}
//	private SyntaxNode length_A() {
//		return null;
//	}
//	private SyntaxNode method_A() {
//		return null;
//	}
//	private SyntaxNode null_A() {
//		return null;
//	}
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
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
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
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
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
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
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
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
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
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
		match(TokenType.RBRACKET);
		match(TokenType.ASSIGN);
		SyntaxNode _expNode = expression();
		newNode.childList.add(_expNode);
		match(TokenType.SEMICOLON);
		return newNode;
	}
	
	private SyntaxNode expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		switch(token.getType()) {
		case INTEGERLITERAL:
			newNode = int_Expression();
			return newNode;
		case KEY_TRUE:
			newNode = true_Expression();
			return newNode;
		case KEY_FALSE:
			newNode = false_Expression();
			return newNode;
		case IDENTIFIER:
			newNode = identifier_Expression();
			return newNode;
		case KEY_THIS:
			newNode = this_Expression();
			return newNode;
		case KEY_NEW:
			getNextToken();
			if(token.getToken().equals("int")) {
				newNode = newArray_Expression();//��ʱ��tokenΪint��ֱ�Ӵ�int��ʼƥ�伴��
				return newNode;
			}else if(token.getType() == TokenType.IDENTIFIER) {
				newNode = new_Expression();//��ʱ��tokenΪIDENTIFIER
				return newNode;
			}else {
				syntaxError("Unexpected token at line: "+lexier.line);
	    		getNextToken();
	    		return null;
			}
		case LOGICAL_NOT:
			newNode = not_Expression();
			return newNode;
		case LPAREN:
			newNode = brace_Expression();
			return newNode;
		default:
			syntaxError("Unexpected token at line: "+lexier.line);
    		getNextToken();
    		return null;
		}
	}
	
	private SyntaxNode int_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Int_Expression;
		
		match(TokenType.INTEGERLITERAL);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		
		return newNode;
	}
	private SyntaxNode true_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.True_Expression;
		match(TokenType.KEY_TRUE);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode false_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.False_Expression;
		match(TokenType.KEY_FALSE);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode identifier_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Identifier_Expression;
		match(TokenType.IDENTIFIER);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode this_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.This_Expression;
		match(TokenType.KEY_THIS);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode newArray_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.NewArray_Expression;
		match(TokenType.KEY_INT);
		match(TokenType.LBRACKET);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		match(TokenType.RBRACKET);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode new_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.New_Expression;
		match(TokenType.IDENTIFIER);
		match(TokenType.LPAREN);
		match(TokenType.RPAREN);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode not_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Not_Expression;
		
		match(TokenType.LOGICAL_NOT);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode brace_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Brace_Expression;
		
		match(TokenType.LPAREN);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		match(TokenType.RPAREN);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	
	private SyntaxNode a_Statement() throws IOException {
		SyntaxNode newNode = null;
		switch(token.getType()) {
		case LOGICAL_AND:
			newNode = op_A();
			return newNode;
		case LESS:
			newNode = op_A();
			return newNode;
		case PLUS:
			newNode = op_A();
			return newNode;
		case MINUS:
			newNode = op_A();
			return newNode;
		case TIMES:
			newNode = op_A();
			return newNode;
		case LBRACKET:
			newNode = exp_A();
			return newNode;
		case POINT:
			getNextToken();
			if(token.getType() == TokenType.KEY_LENGTH) {
				newNode = length_A();
				return newNode;
			}else if(token.getType() == TokenType.IDENTIFIER) {
				newNode = method_A();
				return newNode;
			}else {
				syntaxError("Unexpected token at line: "+lexier.line);
	    		getNextToken();
	    		return null;
			}
		default:
			newNode = null_A();
			return newNode;
		}
	}
	private SyntaxNode op_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.op_A;
		TokenType currentType= token.getType();
		if(currentType == TokenType.LOGICAL_AND || currentType == TokenType.LESS || currentType == TokenType.PLUS || currentType ==TokenType. MINUS || currentType == TokenType.TIMES) {
			SyntaxNode expChild = expression();
			if(expChild != null) {
				newNode.childList.add(expChild);
			}
			SyntaxNode a_statementChild = a_Statement();
			if(a_statementChild != null) {
				newNode.childList.add(a_statementChild);
			}
		}else {
			syntaxError("Unexpected token at line: "+lexier.line);
    		getNextToken();
    		return null;
		}
		
		return newNode;
	}
	private SyntaxNode exp_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.exp_A;
		match(TokenType.LBRACKET);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		match(TokenType.RBRACKET);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode length_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.length_A;
		match(TokenType.KEY_LENGTH);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode method_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.method_A;
		
		match(TokenType.IDENTIFIER);
		match(TokenType.LPAREN);
		if(Exp_FirstSet.contains(token.getType())) {
			SyntaxNode expChild = expression();
			if(expChild != null) {
				newNode.childList.add(expChild);
			}
			while(match(TokenType.COMMA)) {
				SyntaxNode _expChild = expression();
				if(_expChild != null) {
					newNode.childList.add(_expChild);
				}
			}
		}
		match(TokenType.RPAREN);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode null_A() {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.null_A;
		
		pushBackToken();
		return newNode;
	}
}
