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
	static private SyntaxNode if_Statement;
	static private SyntaxNode while_Statement;
	static private SyntaxNode print_Statement;
	static private SyntaxNode varAssigan_Statement;
	static private SyntaxNode arrayAssign_Statement;
	
	
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
    
    
}
