package lexier;

/**
 * @author Alexwell
 *
 */
public class Token {
	private TokenType type;
	private String token;
	private int line;
	private int column;

	/**
	 * 
	 * �ʷ��ǺŵĹ�����
	 * 
	 * @param type   �ʷ��Ǻŵ�����
	 * @param token  �ʷ��ǺŵĴ�ֵ
	 * @param line   �ʷ��Ǻ����ڵ��кţ����ڵ��Ժͼ�����
	 * @param column �ʷ��Ǻ����ڵ��кţ����ڵ��Ժͼ�����
	 */

	public Token(TokenType type, String token, int line, int column) {
		this.type = type;
		this.token = token;
		this.line = line;
		this.column = column;
	}

	public TokenType getType() {
		return type;
	}

	public String getToken() {
		return token;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public enum TokenType {
		/** ���� **/
		IDENTIFIER, // ��ʶ��

		/** ���� **/
		INTEGERLITERAL, // ���ͳ���

		/** �ؼ��� **/
		KEY_CLASS, // class
		KEY_PUBLIC, // public
		KEY_STATIC, // static
		KEY_VOID, // void
		KEY_MAIN, // main
		KEY_STRING, // string
		KEY_EXTENDS, // extends
		KEY_RETURN, // return
		KEY_INT, // int
		KEY_BOOLEAN, // boolean
		KEY_IF, // if
		KEY_ELSE, // else
		KEY_WHILE, // while
		KEY_PRINTLIN, // System.out.println
		KEY_LENGTH, // length
		KEY_TRUE, // true
		KEY_FALSE, // false
		KEY_THIS, // this
		KEY_NEW, // new

		/** ��������� **/
		PLUS, // +
		MINUS, // -
		TIMES, // *

		/** ��ֵ **/
		ASSIGN, // =

		/** ���� **/
		LPAREN, // (
		RPAREN, // )
		LBRACKET, // [
		RBRACKET, // ]
		LBRACES, // {
		RBRACES, // }

		/** ��� **/
		COMMA, // ���ţ�
		SEMICOLON, // �ֺ�;
		POINT, // ��

		/** �߼������ **/
		LOGICAL_NOT, // ��
		LOGICAL_AND, // &&

		/** ��ϵ����� **/
		LESS ,// <
		
		/**����**/
		EOF,
		
		/**�Ƿ�**/
		ILLEGAL_TOKEN
	}
}

