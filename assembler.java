/*
 * Author: My Tran
 * Filename: assembler.java
 * Description: Definition of methods and members of computer class
 */
public class assembler 
{
    //Data Members
    private static String lexeme = "", expression, instruction = "";
    private static int currentIndex = 0, argumentCount = 0;
    private static char nextCharacter = ' ';
    private static token characterClass, nextToken = token.EOF, opToken = token.EOF;

    //Methods
    public static String[] assemble(String[] commands) throws Exception
    {
        String[] instructionArray = new String[commands.length];

        for(int i = 0; i < commands.length; i++)
        {
            //reset instruction for next command
            instruction = "";

            //expression string takes some processing to complete parsing properly
            expression = commands[i].trim();
            reset();

            parse();
            instructionArray[i] = instruction;
        }

        return instructionArray;
    }

    //performs parse of expression to bit comprised instruction
    private static void parse() throws Exception
    {
        //empty strings are not valid commands
        if(expression.isEmpty())
        {
            throw new Exception("EmptyCommandException: No command was given.\nStatement: " + expression);
        }
        else
        {
            //priming read is required for recursive descent lexical analysis to start
            getCharacter();

            //perform parsing until error code is encountered
            
                lex();
                expression();
        }
    }

    //accumulates lexeme and determines its class (i.e. R0 is a register)
    private static void lex() throws Exception
    {
        lexeme = "";
        getNonBlank();

        //lexeme classification is dependent on the type of symbol being read
        switch(characterClass)
        {
            case LETTER:
                addCharacter();
                getCharacter();

                //accumulate the rest of the lexeme which can be alpha-numeric
                while(characterClass == token.LETTER || characterClass == token.DIGIT)
                {
                    addCharacter();
                    getCharacter();
                }

                //loop from before stops acculumating symbols when their are not alpha-numeric and unidentified symbols are a syntax error
                if(characterClass == token.UNKNOWN)
                {
                    throw new Exception("SyntaxErrorException: \'" + nextCharacter + "\' is an invalid token.\nStatement: " + expression);
                }

                //lexeme must be classified for lexical analysis and instruction must be built from the expression
                switch(lexeme)
                {
                    //registers are mapped to REGISTER token
                    case "R0": 
                    case "R1":
                    case "R2":
                    case "R3":
                    case "R4":
                    case "R5":
                    case "R6":
                    case "R7":
                    case "R8":
                    case "R9":
                    case "R10":
                    case "R11":
                    case "R12":
                    case "R13":
                    case "R14":
                    case "R15": 
                        nextToken = token.REGISTER;
                        break; 
                    //commands are mapped to COMMAND token
                    case "move":
                    case "add":
                    case "subtract":
                    case "multiply":
                    case "and":
                    case "or":
                    case "xor":
                    case "not":
                    case "leftshift":
                    case "rightshift":
                    case "interrupt":
                    case "halt": 
                    case "jump":
                    case "compare":
                    case "branchIfEqual":
                    case "branchIfNotEqual":
                    case "branchIfGreaterThan":
                    case "branchIfGreaterThanOrEqual":
                    case "pop":
                    case "push":
                    case "call":
                    case "return":
                        nextToken = token.COMMAND;
                        break;
                    default:
                        break;
                        
                }
                break;
                //negative symbol appends to a number
            case NEGATIVE:
            case DIGIT:
                //if a lexeme is numeric, it is identified as a integer literal
                addCharacter();
                getCharacter();
                nextToken = token.INT_LITERAL;

                //accumulate the rest of the lexeme
                while(characterClass == token.DIGIT || characterClass == token.FLOAT_CODE || characterClass ==token.LETTER)
                {
                    if(characterClass == token.FLOAT_CODE || characterClass == token.LETTER)
                    {
                        nextToken = token.UNKNOWN;
                    }

                    addCharacter();
                    getCharacter();
                }

                //lexeme stops getting accumulated once an unknown symbol is encountered so that an exception can be thrown 
                if(characterClass == token.UNKNOWN)
                {
                    throw new Exception("SyntaxErrorException: \'" + nextCharacter + "\' is an invalid token.\nStatement: " + expression);
                }
                break;
            //Unknown symbols are syntax errors so a lexeme that begins with one throws an exception
            case UNKNOWN:
                throw new Exception("SyntaxErrorException: \'" + nextCharacter + "\' is an invalid token.\nStatement: " + expression);
            default:
                //set the next token to be EOF if non alpha-numeric
                nextToken = token.EOF;
                break;

        }
    }

    private static void getCharacter()//assigns character from expression and classifies it
    {
        if(currentIndex < expression.length())//character can only be obtained from existing index
        {
            //next character needs to be obtained from the expression string
            nextCharacter = expression.charAt(currentIndex);
            currentIndex++;

            //classify character token
            if(nextCharacter >= 'A' && nextCharacter <='z')//for letters
            {
                characterClass = token.LETTER;
            }
            else if(nextCharacter >= '0' && nextCharacter <= '9')//for numbers
            {
                characterClass = token.DIGIT;
            }
            else if(nextCharacter == '-')//for indication of negative
            {
                characterClass = token.NEGATIVE;
            }
            else if(nextCharacter == '.')//for indication of attempted floating point input
            {
                characterClass = token.FLOAT_CODE;
            }
            else if(nextCharacter == ' ')
            {
                characterClass = token.EOF;//indicates the end of the lexeme
            }
            else
            {
                characterClass = token.UNKNOWN; //indicates non-alphanumeric
            }
        }
        else
        {
            characterClass = token.EOF;//indicates the end of the expression
        }
    }

    private static void addCharacter()//concatenates next character to the lexeme
    {
        lexeme += nextCharacter;
    }

    private static void getNonBlank()//finds the next non-white space character
    {
        while(nextCharacter == ' ')
        {
            getCharacter();
        }
    }

    /*
     * expression -> term
     * term -> factor
     * factor -> int_literal | register | register {term}
     */
    //implements expression rule of lexical analyzer and parser
    private static void expression() throws Exception
    {
        //Operations are specified first in instruction
        if(nextToken == token.COMMAND)
        {
            //map specific command token to further enforce syntax rules in later recursion
            //and concatenate appropriate opCode to instruction
            switch(lexeme)
            {
                case "move": opToken = token.MOVE_COMMAND;
                    instruction += "0001";
                    break;
                case "add": opToken = token.ADD_COMMAND;
                    instruction += "1110";
                    break;
                case "subtract": opToken = token.SUB_COMMAND;
                    instruction += "1111";
                    break;
                case "multiply": opToken = token.MUL_COMMAND;
                    instruction += "0111";
                    break;
                case "and": opToken = token.AND_COMMAND;
                    instruction += "1000";
                    break;
                case "or": opToken = token.OR_COMMAND;
                    instruction += "1001";
                    break;
                case "xor": opToken = token.XOR_COMMAND;
                    instruction += "1010";
                    break;
                case "not": opToken = token.NOT_COMMAND;
                    instruction += "1011";
                    break;
                case "leftshift": opToken = token.LSHIFT_COMMAND;
                    instruction += "1100";
                    break;
                case "rightshift": opToken = token.RSHIFT_COMMAND;
                    instruction += "1101";
                    break;
                case "interrupt": opToken = token.INTERRUPT_COMMAND;
                    instruction += "0010 0000";
                    break;
                case "halt": opToken = token.HALT_COMMAND;
                    instruction += "0000 0000 0000 0000";
                    break;
                case "jump": opToken = token.JUMP_COMMAND;
                    instruction += "0011";
                    break;
                case "compare": opToken = token.COMPARE_COMMAND;
                    instruction += "0100 0000";
                    break;
                case "branchIfEqual":   opToken = token.BRANCH_EQUAL_COMMAND;
                    instruction += "0101 01";
                    break;
                case "branchIfNotEqual": opToken = token.BRANCH_NOT_EQUAL_COMMAND;
                    instruction += "0101 00";
                    break;
                case "branchIfGreaterThan": opToken = token.BRANCH_GREATER_COMMAND;
                    instruction += "0101 10";
                    break;
                case "branchIfGreaterThanOrEqual": opToken = token.BRANCH_GREATER_OR_EQUAL_COMMAND;
                    instruction += "0101 11";
                    break;
                case "pop": opToken = token.POP_COMMAND;
                    instruction += "0110 0100 0000";
                    break;
                case "push": opToken = token.PUSH_COMMAND;
                    instruction += "0110 0000 0000";
                    break;
                case "call": opToken = token.CALL_COMMAND;
                    instruction += "0110 10";
                    break;
                case "return": opToken = token.RETURN_COMMAND;
                    instruction += "0110 1100 0000 0000";
                    break;
                default:
                    //any other identifiers would be a syntax error at the moment
                    throw new Exception("SyntaxErrorException: \"" + lexeme + "\" is an invalid token.\nStatement: " + expression); 
            }               
        
            //expression evaluates to term
            argumentCount++;
            lex();
            term();  
        }
        else
        {
            throw new Exception("SyntaxErrorException: Command is expected. \nStatement: " + expression);
        }
        
    }

    //implements term rule of lexical analyzer and parser
    private static void term() throws Exception
    {
        //only registers and int literals are arguments for operations for now
        switch(nextToken)
        {
            case REGISTER:
                switch(opToken)
                {
                    //halt and return take no arguments so should throw exception if any are given
                    case HALT_COMMAND:
                        throw new Exception("SyntaxErrorException: Halt takes no arguments.\nStatement: " + expression);
                    case RETURN_COMMAND:
                        throw new Exception("SyntaxErrorException: Return takes no arguments.\nStatement: " + expression);
                    //interrupt, call, jump, and branch take one argument that is not a register
                    case INTERRUPT_COMMAND:
                        throw new Exception("SyntaxErrorException: An integer argument was expected for interrupt.\nStatement: " + expression);
                    case CALL_COMMAND:
                        throw new Exception("SyntaxErrorException: An integer argument was expected for call.\nStatement: " + expression);
                    case JUMP_COMMAND:
                        throw new Exception("SyntaxErrorException: An integer argument was expected for jump.\nStatement: " + expression);
                    case BRANCH_EQUAL_COMMAND:
                    case BRANCH_GREATER_COMMAND:
                    case BRANCH_GREATER_OR_EQUAL_COMMAND:
                    case BRANCH_NOT_EQUAL_COMMAND:
                        throw new Exception("SyntaxErrorException: An integer argument was expected for branch.\nStatement " + expression);
                    default:
                        break;
                }
                factor();
                break;
            case INT_LITERAL:
                switch(opToken)
                {
                    //ALU, compare, pop, and push commands take no integer arguments
                    case ADD_COMMAND:
                    case SUB_COMMAND:
                    case LSHIFT_COMMAND:
                    case RSHIFT_COMMAND:
                    case AND_COMMAND:
                    case OR_COMMAND:
                    case NOT_COMMAND:
                    case XOR_COMMAND:
                    case MUL_COMMAND:
                    case COMPARE_COMMAND:
                    case POP_COMMAND:
                    case PUSH_COMMAND:
                        throw new Exception("SyntaxErrorException: \"" + lexeme + "\" is an invalid argument.\nStatement: " + expression);
                    //halt and return take no commands at all
                    case HALT_COMMAND:
                        throw new Exception("SyntaxErrorException: Halt takes no arguments.\nStatement: " + expression);
                    case RETURN_COMMAND:
                        throw new Exception("SyntaxErrorException: Return takes no arguments.\nStatement: " + expression);
                    default:
                        break;
                }
                //term evaluates to factor
                factor();
                break;
            //if end of the expression is reached, need to evaluate to factor to terminate
            case EOF:
                factor();
                break;
            default:
                throw new Exception("SyntaxErrorException: \"" + lexeme + "\" is an invalid argument.\nStatement: " + expression);
        }
    }

    //implements factor rules for lexical analyzer and parser
    private static void factor() throws Exception
    {
        //registers can be terminating and integer literals are terminating
        switch(nextToken)
        {
            case REGISTER:
                argumentCount++;

                //Move must take an integer for after a register argument
                if(argumentCount == 3)
                {
                    if(opToken == token.MOVE_COMMAND)
                    {
                        throw new Exception("SyntaxErrorException: An integer argument was expected for move.\nStatement: " + expression);
                    }
                }

                //concatenate appropriate code to instruction
                switch(lexeme)
                {
                    case "R0":  instruction += " 0000";
                        break;
                    case "R1":  instruction += " 0001";
                        break;
                    case "R2":  instruction += " 0010";
                        break;
                    case "R3":  instruction += " 0011";
                        break;
                    case "R4":  instruction += " 0100";
                        break;
                    case "R5":  instruction += " 0101";
                        break;
                    case "R6":  instruction += " 0110";
                        break;
                    case "R7":  instruction += " 0111";
                        break;
                    case "R8":  instruction += " 1000";
                        break;
                    case "R9":  instruction += " 1001";
                        break;
                    case "R10": instruction += " 1010";
                        break;
                    case "R11": instruction += " 1011";
                        break;
                    case "R12": instruction += " 1100";
                        break;
                    case "R13": instruction += " 1101";
                        break;
                    case "R14": instruction += " 1110";
                        break;
                    case "R15": instruction += " 1111";
                        break;
                    default:
                        throw new Exception("SyntaxErrorException: \"" + lexeme + "\" is not a valid identifier.\nStatement: " + expression);
                }

                //evaluate back to term because it is a register. If it's another register, it will call factor, and terminate if EOF
                lex();
                term();
                break;
            case INT_LITERAL:
                switch(opToken)
                {
                    case INTERRUPT_COMMAND:
                    case MOVE_COMMAND:
                        //8 bits are used to represent number in an instruction so range of values is -128 to 127
                        if(stringToDecimal(lexeme) > 127 || stringToDecimal(lexeme) < -128)
                        {
                            throw new Exception("OutOfRangeException: " + lexeme + " is out of the range of values. \nStatement: " + expression);
                        }
                        //concatenate integer converted to an 8 bit signed binary number to instruction
                        instruction += " " + convertToBinary(stringToDecimal(lexeme), 8, true);
                        break;
                    case JUMP_COMMAND:
                        //Jump can only go to accessible memory
                        if(stringToDecimal(lexeme) > 1022 || stringToDecimal(lexeme) < 0)
                        {
                            throw new Exception("OutOfRangeException: " + lexeme + " is out of the range of values of jump. \nStatement: " + expression);
                        }
                        else if((stringToDecimal(lexeme)%2 != 0 || stringToDecimal(lexeme)%-2 != 0) && stringToDecimal(lexeme) != 0)
                        {
                            throw new Exception("UnreachableAddressException: " + lexeme + " is not an accessible address. \nStatement: " + expression);
                        }
                        //concatenate integer converted to an 12 bit signed binary number to instruction
                        instruction += convertToBinary(stringToDecimal(lexeme), 12, false);
                        break;
                    case CALL_COMMAND:
                        //call can only go to accessible memory
                        if(stringToDecimal(lexeme) > 1022 || stringToDecimal(lexeme) < 0)
                        {
                            throw new Exception("OutOfRangeException: " + lexeme + " is out of the range of values of call. \nStatement: " + expression);
                        }
                        else if((stringToDecimal(lexeme)%2 != 0 || stringToDecimal(lexeme)%-2 != 0) && stringToDecimal(lexeme) != 0)
                        {
                            throw new Exception("UnreachableAddressException: " + lexeme + " is not an accessible address. \nStatement: " + expression);
                        }
                        //concatenate integer converted to an 12 bit signed binary number to instruction
                        instruction += convertToBinary(stringToDecimal(lexeme), 10, false);
                        break;
                    case BRANCH_EQUAL_COMMAND:
                    case BRANCH_GREATER_COMMAND:
                    case BRANCH_GREATER_OR_EQUAL_COMMAND:
                    case BRANCH_NOT_EQUAL_COMMAND:
                        if(stringToDecimal(lexeme)%2 != 0)//jump amount must result in PC being at equal address
                        {
                            throw new Exception("UnreachableAddressException: Jump amount must be a multiple of 2. \nStatement: " + expression);
                        }    
                        //branch takes a signed 10 bit number to instruction
                        instruction += convertToBinary(stringToDecimal(lexeme), 10, true);
                    default:
                        break;
                }
                argumentCount++;

                //integers terminate so check for nothing else after integer literal. Syntax error otherwise
                lex();
                if(nextToken != token.EOF)
                {
                    throw new Exception("SyntaxErrorException: Too many arguments.\nStatement: " + expression);
                }

                //evaluate back to factor to terminate with EOF
                factor();
                break;
            case EOF:
                switch(opToken)
                {
                    //ALU ops have an op and 3 arguments so having more or less is an error
                    case ADD_COMMAND:
                    case SUB_COMMAND:
                    case LSHIFT_COMMAND:
                    case RSHIFT_COMMAND:
                    case AND_COMMAND:
                    case OR_COMMAND:
                    case NOT_COMMAND:
                    case XOR_COMMAND:
                    case MUL_COMMAND:
                        if(argumentCount > 4)
                        {
                            throw new Exception("SyntaxErrorException: Too many arguments.\nStatement: " + expression);
                        }
                        else if(argumentCount < 4)
                        {
                            throw new Exception("SyntaxErrorException: missing arguments.\nStatement: " + expression);
                        }
                        break;
                    //Move has an op and 2 arguments so having more or less is an error
                    case COMPARE_COMMAND:
                    case MOVE_COMMAND:
                        if(argumentCount > 3)
                        {
                            throw new Exception("SyntaxErrorException: Too many arguments.\nStatement: " + expression);
                        }
                        else if(argumentCount < 3)
                        {
                            throw new Exception("SyntaxErrorException: missing arguments.\nStatement: " + expression);
                        }
                        break;
                    //Interrupt, jump, branch, call, pop, and push have an op and 1 argument so having more or less is an error
                    case BRANCH_EQUAL_COMMAND:
                    case BRANCH_GREATER_COMMAND:
                    case BRANCH_GREATER_OR_EQUAL_COMMAND:
                    case BRANCH_NOT_EQUAL_COMMAND:
                    case JUMP_COMMAND:
                    case INTERRUPT_COMMAND:
                    case POP_COMMAND:
                    case PUSH_COMMAND:
                    case CALL_COMMAND:
                        if(argumentCount > 2)
                        {
                            throw new Exception("SyntaxErrorException: Too many arguments.\nStatement: " + expression);
                        }
                        else if(argumentCount < 2)
                        {
                            throw new Exception("SyntaxErrorException: missing arguments.\nStatement: " + expression);
                        }
                        break;
                    //Halt and return has an op and no arguments so having any is an error
                    case HALT_COMMAND:
                    case RETURN_COMMAND:
                        if(argumentCount > 1)
                        {
                            throw new Exception("SyntaxErrorException: Too many arguments.\nStatement: " + expression);
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                throw new Exception("SyntaxErrorException: Unexpected argument encountered.\nStatement: " + expression);
        }   
    }

    private static int stringToDecimal(String value)//converts number in string to an integer
    {
        int sum = 0;

        //only iterate whole string if there is no decimal number to account for negative value
        for(int i = value.length() - 1 , j = 1; (value.charAt(0) == '-') ? i > 0 : i >=0; i--, j *= 10)
        {
            sum += (value.charAt(i) - '0') * j;//sum += integer equivalent * 10^i
        }

        sum *= (value.charAt(0) == '-') ? -1 : 1;//negate if value is negative

        return sum;
    }

    private static String convertToBinary(int value, int amountBits, Boolean signed)//convert integer to binary string representations with two's complement considered
    {
        longword temp = new longword(value);//set constructor converts to binary and considers two's complement
        String binaryValue = "";

        //when signed value is indicated, take representation without sign bit. Get all bits for unsigned
        for(int i = 31; ((signed) ? (i > 32 - amountBits) : (i >= 32 - amountBits)); i--)
        {
            binaryValue = ((temp.getBit(i).getValue()) ? "1" : "0") + binaryValue;

            binaryValue = ((i % 4 == 0) ? " ": "") + binaryValue;
        }

        //sign bit is inserted if signed is indicated
        binaryValue = ((signed) ? ((value < 0) ? ("1" + binaryValue) : ("0" + binaryValue)): binaryValue);

        return binaryValue;
    }

    private static void reset()//reset values for lexical analysis and parse
    {
        lexeme = "";
        instruction = "";
        currentIndex = 0;
        argumentCount = 0;
        characterClass = token.EOF;
        nextToken = token.EOF;
    }
}
