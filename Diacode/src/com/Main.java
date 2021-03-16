package com;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;

public class Main {
    static boolean hadError = Boolean.FALSE;
    public static void main(String[] args) throws IOException{
        String path;
        String allLines="";
        path = "F:\\USERS\\Polina\\Рабочий стол\\PBL_4.txt";
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!allLines.isEmpty()) {
                allLines += "\n";
            }
            allLines += line;
        }
        TestText(allLines);
        if (hadError) System.exit(1);
        scanner.close();
    }
    private static void TestText(String testLine) {
        Scan scanner = new Scan(testLine);
        List<Token> tokens = scanner.scanTokens();
        for (Token token : tokens) {
            System.out.println(token.getToken());
        }
    }
    static void error(int line, char where, String message) {
        System.err.println(
                "Line [" + line + "] Error \"" + where + "\": " + message);
        hadError = true;
    }
}
