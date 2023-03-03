/*
 * Author: My Tran
 * Filename: token.java
 * Description: Specifies tokens used in lexing and parsing in assembler.java
 */
enum token 
{
    //tokens for classifying symbols
    LETTER,         //indicates letter character
    DIGIT,          //indicates numeric
    NEGATIVE,       //indicates negative number
    UNKNOWN,        //indicates non-alphanumeric symbol
    INT_LITERAL,    //token classifying identifier as int literal
    REGISTER,       //token classifying identifier as register

    //error codes
    FLOAT_CODE,     //token indicating floating point value encountered
    EOF,            //token indicates end of line

    //tokens for different commands
    COMMAND,
    MOVE_COMMAND,
    ADD_COMMAND,
    SUB_COMMAND,
    RSHIFT_COMMAND,
    LSHIFT_COMMAND,
    AND_COMMAND,
    OR_COMMAND,
    XOR_COMMAND,
    NOT_COMMAND,
    MUL_COMMAND,
    INTERRUPT_COMMAND,
    HALT_COMMAND,
    JUMP_COMMAND,
    COMPARE_COMMAND,
    BRANCH_EQUAL_COMMAND,
    BRANCH_NOT_EQUAL_COMMAND,
    BRANCH_GREATER_COMMAND,
    BRANCH_GREATER_OR_EQUAL_COMMAND,
    POP_COMMAND,
    PUSH_COMMAND,
    CALL_COMMAND,
    RETURN_COMMAND
}
