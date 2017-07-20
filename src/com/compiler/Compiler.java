package com.compiler;

import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzer;
import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzerImpl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Compiler {
    public static int row = 1;
    public static void main(String[] args){
        compiler();
    }

    private static void compiler() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("code/code.txt")));
            String line = null;
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl();
            while ((line = br.readLine()) != null) {
                lexicalAnalyzer.lexicalAnalyzer(line,row);
                row++;
            }
            lexicalAnalyzer.lexicalAnalyzer("#",row);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
