package com.compiler;

import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzer;
import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzerImpl;

import java.io.*;

public class Compiler {
    public static void main(String[] args){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
            String line = null;
            int row = 1;
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl();
            while ((line = br.readLine()) != null) {
                lexicalAnalyzer.lexicalAnalyzer(line,row++);
            }
            lexicalAnalyzer.lexicalAnalyzer("#",row);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
