package com;
import java.util.*;
import static com.Token.TokenType.*;
import java.lang.Boolean;

public class Scan {
    private final String line;
    private final List<Token> tokens = new ArrayList<>();
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
    private char peek() {
        if (isAtEnd()) return '\0';
        return line.charAt(current);
    }
    private char advance(){
        return line.charAt(current++);
    }
    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token( UNCLASSIFIED, "", null, countLine));
        return tokens;
    }
    private void addToken(Token.TokenType type){
        addToken(type,null);
    }
    private void addToken (Token.TokenType type, Object literal){
        String text = line.substring(start,current);
        tokens.add(new Token(type,text,literal,countLine));
    }
    private boolean test (char testCh){
        if(isAtEnd()) return Boolean.FALSE;
        if(line.charAt(current)!=testCh) return Boolean.FALSE;
        current++;
        return Boolean.TRUE;
    }
    static final Map<String, Token.TokenType> keywords;

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
        keywords.put("while",  WHILE);
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
            case '=':
                addToken(test('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(test('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(test('=') ? GREATER_EQUAL : GREATER);
                break;
            case'i':
                if (test('f')){
                    addToken(IF);
                }
            case't':
                if (test('o')){
                    addToken(FOR_DIRECTION);
                }
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                countLine++;
                break;
        }
    }
}