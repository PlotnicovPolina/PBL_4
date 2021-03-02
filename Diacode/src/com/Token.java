package com;
public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    public String getToken() {
        return line+" "+type + " " + lexeme + " " + literal;
    }
    enum TokenType {
        IDENTIFIER,// - type names, variable names and function names
        OPERATOR,// - binary and unary operators

        NUMBER,// - numbers, both int and float
        STRING ,//- string value, without quote characters
        BOOLEAN,// - string representation of true/false

        BLOCK_START,//- begin
        BLOCK_END,// - end
        FOR,// - for
        FROM,// - from
        FOR_DIRECTION,// - to/downto
        WHILE,// - while
        IF,//- if
        ELSE,// - else

        BRACKET_START,// - (
        BRACKET_END,// - )

        ARRAY_BRACKET_START,// - [
        ARRAY_BRACKET_END,// - ]

        UNCLASSIFIED,//- any character that didn't match other token types

    }
}
