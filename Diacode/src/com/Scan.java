package com;
import java.util.*;
import static com.Token.TokenType.*;
import java.lang.Boolean;

public class Scan {
    private final String line;
    private final List<Token> tokens = new ArrayList<>();
    static final Map<String, Token.TokenType> keywords;
    private int start = 0;
    private int current = 0;
    private int  countLine = 1;
    public Scan(String line) {
        this.line = line;
    }
    private boolean isAtEnd(){
        if (current >= line.length()) return Boolean.TRUE;
        else return Boolean.FALSE;
    }
    private boolean isDigit(char c){
         if(c>='0'&& c<='9')return Boolean.TRUE;
         else return Boolean.FALSE;
    }
    private boolean isLetter(char c){
        if((c>='a'&& c<='z')||(c >= 'A' && c <= 'Z') || c == '_')return Boolean.TRUE;
        else return Boolean.FALSE;
    }
    private boolean isLetterNum(char c){
        if(isLetter(c)||isDigit(c))return Boolean.TRUE;
        else return Boolean.FALSE;
    }
    private char peek() {
        if (isAtEnd()) return '\0';
        return line.charAt(current);
    }
    private char advance(){
        return line.charAt(current++);
    }
    private void addToken(Token.TokenType type){
        addToken(type,null);
    }
    private void addToken (Token.TokenType type, Object literal){
        String text = line.substring(start,current);
        tokens.add(new Token(type,text,literal,countLine));
    }
    private boolean testEqualsCh (char testCh){
        if(isAtEnd()) return Boolean.FALSE;
        if(line.charAt(current)!=testCh) return Boolean.FALSE;
        current++;
        return Boolean.TRUE;
    }
    private char testNextCh(){
        int n=current++;
        if(n>=line.length())return '\0';
        else return line.charAt(n);
    }
    private void NumberToken(){
        while(isDigit(peek())){
            advance();
        }
        if (peek()=='.'&& isDigit(testNextCh())){
            advance();
            while(isDigit(peek())){
                advance();
            }
        }
        addToken(NUMBER,Double.parseDouble(line.substring(start, current)));
    }
    private void WordToken(){
        while(isLetterNum(peek())){
            advance();
        }
        String text = line.substring(start,current);
        Token.TokenType type= keywords.get(text.toLowerCase());
        if(type==null) addToken(IDENTIFIER);
        else addToken(type);
    }
    private void StringToken(){
        while (peek()!='"'&& !isAtEnd()){
            advance();
        }
        if (isAtEnd()){
            char startError=line.charAt(start+1);
            Main.error(countLine,startError,"Unterminated string.");
            return;
        }
        //pass quotes
        advance();
        String literal = line.substring(start+1,current-1);
        addToken(STRING,literal);
    }
    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token( UNCLASSIFIED, "", null, countLine));
        countLine++;
        return tokens;
    }
    static {
        keywords = new HashMap<>();
        keywords.put("begin", BLOCK_START);
        keywords.put("end", BLOCK_END);
        keywords.put("if", IF);
        keywords.put("else", ELSE);
        keywords.put("for", FOR);
        keywords.put("while", WHILE);
        keywords.put("from", FROM);
        keywords.put("to", FOR_DIRECTION);
        keywords.put("downto", FOR_DIRECTION);
        keywords.put("true", BOOLEAN);
        keywords.put("false", BOOLEAN);
        keywords.put("not", OPERATOR);
        keywords.put("and", OPERATOR);
        keywords.put("or", OPERATOR);
    }
    private void scanToken(){
        char c = advance();
        switch (c) {
            case '(':
                addToken(BRACKET_START);
                break;
            case ')':
                addToken(BRACKET_END);
                break;
            case '[':
                addToken(ARRAY_BRACKET_START);
                break;
            case ']':
                addToken(ARRAY_BRACKET_END);
                break;
            case '+':
            case '*':
            case '%':
            case '-':
                addToken(OPERATOR);
                break;
            case '/':
                if (testEqualsCh('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                }
                else addToken(OPERATOR);
                break;
            case '!':
            case '=':
            case '<':
            case '>':
                addToken(testEqualsCh('=') ? OPERATOR: OPERATOR);
                break;
            case'"':
                StringToken();
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                countLine++;
                break;
            default:
                if(isDigit(c)){
                    NumberToken();
                }
                else if(isLetter(c)){
                    WordToken();
                }
                else{
                    char startError=line.charAt(start);
                    Main.error(countLine,startError,"Unexpected character.");}
                break;
        }
    }
}