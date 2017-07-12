package com.compiler.procedure.lexiacalAnalysis;

import com.compiler.exception.IllegalInputException;
import com.compiler.exception.RedefinitionException;
import com.compiler.procedure.symboltables.ConstantsTable;
import com.compiler.procedure.symboltables.SymbolTable;
import com.compiler.symbols.KeyWords;

import java.lang.reflect.Field;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {

    private char ch = '\0';
    private String strToken = "";
    private String line = null;
    private int pointer = 0;
    private int currRow = 1;

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
        if (!SymbolTable.symbolMap.containsKey(strToken)){
            SymbolTable.symbolMap.put(strToken,null);
            return strToken;
        }
        else try {
            throw new RedefinitionException();
        } catch (RedefinitionException e) {
            System.out.println(ch+"redefinition");
//            e.printStackTrace();
//            System.exit(-1);
        }
        return null;
    }

    /**
     * insert const into const table
     * @return the const'id in const table
     */
    private int insertConst(){
        Integer temp = Integer.parseInt(strToken);
        if (!ConstantsTable.constTable.containsKey(temp.hashCode())){
            ConstantsTable.constTable.put(temp.hashCode(),temp);
            return temp.hashCode();
        }
        else return strToken.hashCode();
    }


    public void lexicalAnalyzer(String line, int row){
        this.currRow = row;
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
                    System.out.println("(1,"+value+")");
                }
                else System.out.println("("+code+",-)");
            }
            else if (isDigit()){
                while (isDigit()){
                    concat();
                    getChar();
                }
                retract();
                int value = insertConst();
                System.out.println("(10,"+value+")");
            }
            else {
                switch (ch){
                    case '+':
                        getChar();
                        if (ch == '+') System.out.println("(204,-)");
                        else {
                            System.out.println("(200,-)");
                            retract();
                        }
                        break;
                    case '-':
                        getChar();
                        if (ch == '-') System.out.println("(205,-)");
                        else {
                            System.out.println("(201,-)");
                            retract();
                        }
                        break;
                    case '*':
                        System.out.println("(202,*)");
                        break;
                    case '\\':
                        System.out.println("(203,1)");
                        break;
                    case ':':
                        getChar();
                        if (ch == '=') System.out.println("(206,-)");
                        else {
                            System.out.println("(108,-)");
                            retract();
                        }
                        break;
                    case '!':
                        getChar();
                        if (ch == '=') System.out.println("(207,-)");
                        else {
                            System.out.println("(214,-)");
                            retract();
                        }
                        break;
                    case '>':
                        getChar();
                        if (ch == '=') System.out.println("(209,-)");
                        else {
                            System.out.println("(208,-)");
                            retract();
                        }
                        break;
                    case '<':
                        getChar();
                        if (ch == '=') System.out.println("(211,-)");
                        else {
                            System.out.println("(210,-)");
                            retract();
                        }
                        break;
                    case '&':
                        getChar();
                        if (ch == '&') System.out.println("(212,-)");
                        else {
                            try {
                                throw new IllegalInputException();
                            } catch (IllegalInputException e) {
                                System.out.println("Syntax Error.Line "+row+" : Your input is not legal.");
                                System.exit(-1);
                            }
                        }
                        break;
                    case '|':
                        getChar();
                        if (ch == '|') System.out.println("(213,-)");
                        else {
                            try {
                                throw new IllegalInputException();
                            } catch (IllegalInputException e) {
                                System.out.println("Syntax Error.Line "+row+" : Your input is not legal.");
                                System.exit(-1);
                            }
                        }
                        break;
                    case ',':
                        System.out.println("(100,-)");
                        break;
                    case ';':
                        System.out.println("(101,-)");
                        break;
                    case '(':
                        System.out.println("(102,-)");
                        break;
                    case ')':
                        System.out.println("(103,-)");
                        break;
                    case '[':
                        System.out.println("(104,-)");
                        break;
                    case ']':
                        System.out.println("(105,-)");
                        break;
                    case '{':
                        System.out.println("(106,-)");
                        break;
                    case '}':
                        System.out.println("(107,-)");
                        break;
                    default:
                        try {
                            throw new IllegalInputException();
                        } catch (IllegalInputException e) {
                            System.out.println("Syntax Error.Line "+row+" : Your input is not legal.");
                            System.exit(-1);
                        }
                }
            }
        }
    }

}
