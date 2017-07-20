package com.compiler.procedure.lexiacalAnalysis;

import com.compiler.Compiler;
import com.compiler.exception.IllegalInputException;
import com.compiler.model.Lexical2Syntax;
import com.compiler.procedure.symboltables.ConstantsTable;
import com.compiler.procedure.syntacticAnalysis.SyntacticAnalyzer;
import com.compiler.procedure.syntacticAnalysis.SyntacticAnalyzerImpl;
import com.compiler.symbols.KeyWords;

import java.lang.reflect.Field;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {

    private char ch = '\0';
    private String strToken = "";
    private String line = null;
    private int pointer = 0;
    private SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzerImpl();

    /**
     * @return the next char
     */
    private void getChar(){
        if (pointer<line.length()){
            ch = line.charAt(pointer);
        }
        else ch = '\0';
        pointer++;
    }

    /**
     * @return true if ch is backspace
     */
    private boolean getBC(){
        return ch == 32;
    }

    /**
     * @return the new string
     */
    private void concat(){
        strToken = strToken.concat(ch+"");
    }

    /**
     * @return true if ch is a letter
     */
    private boolean isLetter(){
        return (ch>64 && ch<91) || (ch>96 && ch < 123);
    }

    /**
     * @return true if ch is a digit
     */
    private boolean isDigit(){
        return (ch>47 && ch <58);
    }

    /**
     * @return if strToken is keyword, the code of relational keyword will be return. Else, 0 is returned.
     */
    private int reserve(){
        try{
            Field[] keywords = KeyWords.class.getFields();
            for (Field keyword : keywords){
                String keywordName = keyword.getName();
                if (keywordName.equalsIgnoreCase(strToken)) return keyword.getInt(keywordName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * set ch null; pointer--;
     */
    private void retract(){
        pointer--;
        ch = '\0';
    }

    /**
     * insert strToken into symbol table
     * @return the id of strToken in the symbol table
     */
    private String insertID(){
//        SymbolTable.symbolMap.put(strToken,null);
        return strToken;
    }

    /**
     * insert const into const table
     * @return the const'id in const table
     */
    private int insertConst(){
        Integer temp = Integer.parseInt(strToken);
        ConstantsTable.constTable.put(temp.hashCode(),temp);
        return temp.hashCode();
    }


    public Object lexicalAnalyzer(String line, int row){
        this.line = line;
        this.pointer = 0;
        while (pointer<line.length()){
            strToken = "";
            getChar();
            while (getBC()){getChar();}
            if (isLetter()){
                while (isLetter() || isDigit()){
                    concat();
                    getChar();
                }
                retract();
                int code = reserve();
                if (code == 0){
                    String value = insertID();
                    syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(1,value));
                }
                else {
                    syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(code,strToken));
                }
            }
            else if (isDigit()){
                while (isDigit()){
                    concat();
                    getChar();
                }
                retract();
                int value = insertConst();
                syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(10,value+""));
            }
            else {
                switch (ch){
                    case '+':
                        getChar();
                        if (ch == '+') {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(204,"++"));
                        }
                        else {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(200,"+"));
                            retract();
                        }
                        break;
                    case '-':
                        getChar();
                        if (ch == '-') {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(205,"--"));
                        }
                        else {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(201,"-"));
                            retract();
                        }
                        break;
                    case '*':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(202,"*"));
                        break;
                    case '/':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(203,"/"));
                        break;
                    case ':':
                        getChar();
                        if (ch == '=') {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(206,":="));
                        }
                        else {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(108,":"));
                            retract();
                        }
                        break;
                    case '=':
                        getChar();
                        if (ch == '=') syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(215,"=="));
                        else {
                            try {
                                throw new IllegalInputException("="+ch,row);
                            } catch (IllegalInputException e) {
                                System.out.println(e.getMessage());
                                System.exit(-1);
                            }
                        }
                        break;
                    case '!':
                        getChar();
                        if (ch == '=') {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(207,"!="));
                        }
                        else {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(214,"!"));
                            retract();
                        }
                        break;
                    case '>':
                        getChar();
                        if (ch == '=') {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(209,">="));
                        }
                        else {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(208,">"));
                            retract();
                        }
                        break;
                    case '<':
                        getChar();
                        if (ch == '='){
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(211,"<="));
                        }
                        else {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(210,"<"));
                            retract();
                        }
                        break;
                    case '&':
                        getChar();
                        if (ch == '&') {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(212,"&&"));
                        }
                        else {
                            try {
                                throw new IllegalInputException();
                            } catch (IllegalInputException e) {
                                System.out.println("Lexical Error.Line "+row+" : Your input is not legal.");
                                System.exit(-1);
                            }
                        }
                        break;
                    case '|':
                        getChar();
                        if (ch == '|') {
                            syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(213,"||"));
                        }
                        else {
                            try {
                                throw new IllegalInputException();
                            } catch (IllegalInputException e) {
                                System.out.println("Lexical Error.Line "+row+" : Your input is not legal.");
                                System.exit(-1);
                            }
                        }
                        break;
                    case ',':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(100,","));
                        break;
                    case ';':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(101,";"));
                        break;
                    case '(':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(102,"("));
                        break;
                    case ')':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(103,")"));
                        break;
                    case '[':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(104,"["));
                        break;
                    case ']':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(105,"]"));
                        break;
                    case '{':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(106,"{"));
                        break;
                    case '}':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(107,"}"));
                        break;
                    case '#':
                        syntacticAnalyzer.syntacticAnalyzer(new Lexical2Syntax(0,"#"));
                        break;
                    default:
                        try {
                            throw new IllegalInputException("Your input "+ch+" is not legal.", Compiler.row);
                        } catch (IllegalInputException e) {
                            System.out.println(e.getMessage());
                            System.exit(-1);
                        }
                }
            }
        }
        return null;
    }

}
