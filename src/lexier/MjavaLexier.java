package lexier;

import java.io.*;
import lexier.Token;
import lexier.Token.TokenType;

/**
 * @author Alexwell
 *
 */
public class MjavaLexier {

	private PushbackReader in = null;
	/*
	 * ʹ��PushbackReader��ԭ�� ���ж�identifier��ʱ�������ƥ��ԭ�� ������������ʱ������ֹ�����Ǵ�ʱ�Ѿ������Ŷ���
	 * ʹ���ƻؽ����ŷ��ص��ļ����У���һ���ٴ��� ���ʱ�����ǿ��Դ���ǰ���������identifier���Ǳ����ؼ���
	 */
	private FileWriter out = null;//�����Ϣ
	
	private int state = 0; // ��ǰ״̬

	private int start = 0; // ��ʼ״̬

	private char ch; // �����ַ�

	private StringBuffer buffer = new StringBuffer(); // ���浱ǰ���ַ��������ж��Ƿ��ǹؼ���

	public int line = 1; // �кţ��ļ���ʼλ���ǵ�һ��

	private int column = 0; // �кţ��ļ���ʼλ���ǵ�һ��

	/**
	 * @param inputFile �����ļ���
	 */
	public MjavaLexier(String inputFile, String outputFile) {
		PushbackReader reader = null;
		FileWriter writer= null;
		try {
			reader = new PushbackReader(new FileReader(inputFile),10);
			writer = new FileWriter(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		in = reader;
		out = writer;
	}

	/**
	 * ��һ���ַ�����buffer�У�����DFA�ж�
	 */
	private void getNext() {
		try {
			ch = (char) in.read();
			buffer.append(ch);
			column++;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * ���ոն�����ַ����˵�����������
	 */
	private void pushBack() {
		try {
			in.unread(buffer.charAt(buffer.length() - 1)); // �����һ���ַ����˵�����
			buffer.deleteCharAt(buffer.length() - 1);
			column--;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * @param type
	 * @return new Token
	 */
	public Token getToken(TokenType type) {
		state = 0;
		start = 0;
		String token = buffer.toString();
		buffer.setLength(0);
		return new Token(type, token, line, column);
	}

	/**
	 * ���buffer�е�����
	 */
	private void dropChar() {
		buffer.setLength(0);
	}

	public Token nextToken() throws IOException {

		while (true) {
			switch (state) {
			
			case 0:
				// �õ���һ���ַ�
				getNext();
				// �ж��Ƿ��ǿհ�
				if (Character.isWhitespace(ch)) {
					if (ch == '\n') {
						line++;
						column = 0;
					}
					dropChar();
					break;
				}else if (Character.isDigit(ch)) {
					state = 1;		//��������
				}else if(Character.isLetter(ch)) {
					state = 2;		//�����ַ�
				}else {
					state = 4;		//�������
				}
				break;
			//����
			case 1:
				// �õ���һ���ַ�
				getNext();
				if (Character.isDigit(ch)) {
					state = 1;
				}else {
					if(ch == '.') {
						getNext();
						while(Character.isDigit(ch)) {
							getNext();
						}
						out.write("get nextToken error! \n");
						pushBack();
						return getToken(TokenType.ILLEGAL_TOKEN);
					}
					if(ch == ';') {
						pushBack();
						return getToken(TokenType.INTEGERLITERAL);
					}
					// �ж��Ƿ��ǿհ�
					if (Character.isWhitespace(ch)) {
						if (ch == '\n') {
							line++;
							column = 1;
						}
						return getToken(TokenType.INTEGERLITERAL);
					}
						if((!Character.isLetter(ch))) {
							pushBack();
							return getToken(TokenType.INTEGERLITERAL);
						}
						out.write("get nextToken error! \n");
						while(Character.isLetter(ch)) {
							getNext();
						}
						return getToken(TokenType.ILLEGAL_TOKEN);	
				}
				break;
			//�ַ�	
			case 2:
				// �õ���һ���ַ�
				getNext();
				if(Character.isLetterOrDigit(ch)) {
					state = 2;
				}else if(ch == '.') {
					String temp = buffer.toString();
					if(temp.equals("System.") || temp.equals("System.out.")) {
						state = 2;
					}else {
						pushBack();
						return getToken(TokenType.IDENTIFIER);
					}
				}else if(ch == '_'){
					state = 3;
				}else {
					pushBack();
					String key = buffer.toString();
					if(key.equals("class")) {
						return getToken(TokenType.KEY_CLASS);
					}else if(key.equals("public")) {
						return getToken(TokenType.KEY_PUBLIC);
					}else if(key.equals("static")) {
						return getToken(TokenType.KEY_STATIC);
					}else if(key.equals("void")) {
						return getToken(TokenType.KEY_VOID);
					}else if(key.equals("main")) {
						return getToken(TokenType.KEY_MAIN);
					}else if(key.equals("string")) {
						return getToken(TokenType.KEY_STRING);
					}else if(key.equals("extends")) {
						return getToken(TokenType.KEY_EXTENDS);
					}else if(key.equals("return")) {
						return getToken(TokenType.KEY_RETURN);
					}else if(key.equals("int")) {
						return getToken(TokenType.KEY_INT);
					}else if(key.equals("boolean")) {
						return getToken(TokenType.KEY_BOOLEAN);
					}else if(key.equals("if")) {
						return getToken(TokenType.KEY_IF);
					}else if(key.equals("else")) {
						return getToken(TokenType.KEY_ELSE);
					}else if(key.equals("while")) {
						return getToken(TokenType.KEY_WHILE);
					}else if(key.equals("System.out.println")) {
						return getToken(TokenType.KEY_PRINTLIN);
					}else if(key.equals("length")) {
						return getToken(TokenType.KEY_LENGTH);
					}else if(key.equals("true")) {
						return getToken(TokenType.KEY_TRUE);
					}else if(key.equals("false")) {
						return getToken(TokenType.KEY_FALSE);
					}else if(key.equals("this")) {
						return getToken(TokenType.KEY_THIS);
					}else if(key.equals("new")) {
						return getToken(TokenType.KEY_NEW);
					}else {
						return getToken(TokenType.IDENTIFIER);
					}
				}
				break;
				
			case 3:
				getNext();
				if(Character.isLetterOrDigit(ch)) {
					state = 2;
				}else {
					pushBack();
					out.write("get nextToken error! ");
					out.write("Find illegal character "+ch);
					out.write(" At line "+line+", column "+column+"\n");
				}
				break;
				
			case 4:
				if (ch == '+') {
					return getToken(TokenType.PLUS);
				}else if (ch == '-') {
					return getToken(TokenType.MINUS);
				}else if (ch == '*') {
					return getToken(TokenType.TIMES);
				}else if (ch == '(') {
					return getToken(TokenType.LPAREN);
				}else if (ch == ')') {
					return getToken(TokenType.RPAREN);
				}else if (ch == '{') {
					return getToken(TokenType.LBRACES);
				}else if (ch == '}') {
					return getToken(TokenType.RBRACES);
				}else if (ch == '[') {
					return getToken(TokenType.LBRACKET);
				}else if (ch == ']') {
					return getToken(TokenType.RBRACKET);
				}else if (ch == ';') {
					return getToken(TokenType.SEMICOLON);
				}else if (ch == '<') {
					return getToken(TokenType.LESS);
				}
				else if (ch == '!') {
					return getToken(TokenType.LOGICAL_NOT);
				}
				else if (ch == '&') {
					getNext();
					if(ch == '&'){
						return getToken(TokenType.LOGICAL_AND);
					}else {
						pushBack();
						out.write("get nextToken error! ");
						out.write("Find illegal character &");
						out.write(" At line "+line+"\n");
						return getToken(TokenType.ILLEGAL_TOKEN);
					}
				}
				else if(ch == '.') {
					getNext();
					if(Character.isWhitespace(ch)||Character.isLetter(ch)) {
						pushBack();
						return getToken(TokenType.POINT);
					}
					else {
						out.write("get nextToken error! ");
						out.write("Find illegal character "+'.');
						out.write(" At line "+line+"\n");
						while(!Character.isWhitespace(ch)) {
							getNext();
						}
						pushBack();
						return getToken(TokenType.ILLEGAL_TOKEN);
					}
					
				}
				else if (ch == '=') {
					return getToken(TokenType.ASSIGN);
				}
				else if (ch == ',') {
					return getToken(TokenType.COMMA);
				}
				else if ((ch&0xff) == 0xff) {//�ļ���β������EOF
					return getToken(TokenType.EOF);
				}
				else {
					out.write("get nextToken error! ");
					out.write("Find illegal character "+ch);
					out.write(" At line "+line+", column "+column+"\n");
					while(Character.isLetter(ch)||ch=='_'){
						getNext();
						if(!(Character.isLetter(ch)||ch=='_')) {
							pushBack();
							break;
						}
					}
					return getToken(TokenType.ILLEGAL_TOKEN);
				}
				
			default:
				out.write("Get nextToken error! ");
				out.write("Find illegal state:"+state+"\n");
				System.exit(1);

			}
			
		}
	}
	
	/**
	 * @param inputFile	�����ļ�·��
	 * @param outputFile	����ļ�·��
	 */
	public static void start(String inputFile, String outputFile) {
		try {
			MjavaLexier lexier = new MjavaLexier(inputFile, outputFile);
			Token token = lexier.nextToken();
			while(token.getType() != TokenType.EOF) {
				lexier.out.write(token.getToken()+"\t\t\t"+token.getType()+"\n");
				token = lexier.nextToken();
			}
			lexier.out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MjavaLexier.start("E:\\Users\\Alexwell\\eclipse_workspace\\Mjava\\src\\testFile\\test1.txt", "C:\\Users\\Alexwell\\Desktop\\myTestOut.txt");
	}

}
