# A compiler in java 

Implementing the subset of a compiler system which includes lexical analysis, syntactic analysis, semantic 
analysis, symbols table and exception handling.

# compositions of language

## Types of data

- integer
- float/double
- char
- boolean

## simple variables

## The arithmetic expressions

+, - , * , / , ++ , --

## The relational expressions

< , <= , > , >= , = , !=

## The boolean expressions

&& , || , !

## Statements

- assignment statement : including the quote of multi-dimensional array
- branch statement : if-else , if-else-then , switch-case
- looping statement : do-while , while-do
- procedure call statement
- definition statement

# Input & Output

input : a .txt file which contains your source code

output : if the code is correct, the intermediate code and symbols table are excepted. Else, you are 
supposed to point the location and reason of exception.

# Examples

```
int a,b;
while a<20 && b>8 do{
    if a>10 || b<16 then{
        if a<15 then{
            a := 19;
            b :=15;
        }
        else{
            a := 11;
            b :=9;
        }
    }
    else{
        a := 1;
        b := 17;
    }
}
```
