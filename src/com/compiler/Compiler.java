package com.compiler;

import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzer;
import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzerImpl;

import java.io.*;

public class Compiler {
    public static int row = 1;
    public static void main(String[] args){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("code/boolean.txt")));
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
