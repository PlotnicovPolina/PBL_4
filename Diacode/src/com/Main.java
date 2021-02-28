package com;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException{
        String path;
        path = "F:\\USERS\\Polina\\Рабочий стол\\govno.txt";
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            TestText(line);
        }
        scanner.close();
    }
    private static void TestText(String testLine) {
        Scan scanner = new Scan(testLine);
        List<Token> tokens = scanner.scanTokens();
        System.out.println(testLine);
        for (Token token : tokens) {
            System.out.println(token.getToken());
        }
    }

}
