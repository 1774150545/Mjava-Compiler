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
		out.write("Syntax error "+message+" at line "+line+"\n");
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
    
    public SyntaxNode newDeclarationNode(Declaration type) {
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
    	}
    	return newNode;
    }
    
    public SyntaxNode newStatementNode(Declaration type) {
    	
    	
    	return null;
    }
    
    public SyntaxNode newExpressionNode(Declaration type) {
    	
    	
    	return null;
    }
    
    private SyntaxNode goal() {
    	SyntaxNode goal_Node = new SyntaxNode();
    	goal_Node.declaration = Declaration.Goal;
    	SyntaxNode mainClassChild = new SyntaxNode();
		return goal_Node;
    }
}
