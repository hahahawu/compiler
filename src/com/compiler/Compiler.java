package com.compiler;

import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzer;
import com.compiler.procedure.lexiacalAnalysis.LexicalAnalyzerImpl;

public class Compiler {
    public static void main(String[] args){
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl();
        lexicalAnalyzer.lexicalAnalyzer("while a>0 do");
    }
}
