package com.compiler;

import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzer;
import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzerImpl;

import java.io.*;

public class Compiler {
    public static void main(String[] args){
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("code.txt")));
            String line = null;
            int row = 1;
            while ((line = br.readLine()) != null) lexicalAnalyzer.lexicalAnalyzer(line,row++);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
