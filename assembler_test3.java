/*
Author: My Tran
Filename: assembler_test3.java
Description: Program performs unit testing on the methods of assembler class
*/
public class assembler_test3
{
    public static void main(String args[]) throws Exception
    {
        // bit_test.runTests();
        // longword_test.runTests();
        // rippleAdder_test.runTests();
        // multiplier_test.runTests();
        // ALU_test.runTests();
        // memory_test.runTests();
        // CPU_test1.runTests();
        // CPU_test2.runTests();
        // CPU_test3.runTests();
        // assembler_test1.runTests();
        // assembler_test2.runTests();
        runTests();
    }

    public static void runTests() throws Exception
    {
        System.out.println("Starting assembler_test3.java...\n");

        testAssemble();

        System.out.println("Ending test...");
    }

    public static void testAssemble() throws Exception
    {
        System.out.println("Test assemble method:\n");

        //assembled push commands should return matching expected values to pass
        System.out.println("Test push instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] pushCommands = {"push R0",
                                "push R3",
                                "push R2",
                                "push R7",
                                "push R10", 
                                "push R13"};

        String[] expectedInstructions1 = {"0110 0000 0000 0000",
                                        "0110 0000 0000 0011",
                                        "0110 0000 0000 0010",
                                        "0110 0000 0000 0111",
                                        "0110 0000 0000 1010",
                                        "0110 0000 0000 1101"};

        //expectedInstructions1 is the same as the array that assemble returns with push
        String[] pushInstructions = assembler.assemble(pushCommands);

        for(int i = 0; i < pushCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions1[i].equals(pushInstructions[i]))? pushCommands[i] + "\t\t|\t" + expectedInstructions1[i] + " - passes.":  pushCommands[i] + "\t\t|\t" + expectedInstructions1[i] + " - fails.");
        }
        System.out.println();

        //assembled pop commands should return matching expected values to pass
        System.out.println("Test pop instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] popCommands = {"pop R0",
                                "pop R3",
                                "pop R2",
                                "pop R7",
                                "pop R10", 
                                "pop R13"};

        String[] expectedInstructions2 = {"0110 0100 0000 0000",
                                        "0110 0100 0000 0011",
                                        "0110 0100 0000 0010",
                                        "0110 0100 0000 0111",
                                        "0110 0100 0000 1010",
                                        "0110 0100 0000 1101"};

        //expectedInstructions2 is the same as the array that assemble returns with pop
        String[] popInstructions = assembler.assemble(popCommands);

        for(int i = 0; i < popCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions2[i].equals(popInstructions[i]))? popCommands[i] + "\t\t|\t" + expectedInstructions2[i] + " - passes.":  popCommands[i] + "\t\t|\t" + expectedInstructions2[i] + " - fails.");
        }
        System.out.println();
        
        //assembled call commands should return matching expected values to pass
        System.out.println("Test call instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] callCommands = {"call 12",
                                "call 0",
                                "call 48",
                                "call 1020",
                                "call 1022", 
                                "call 262"};

        String[] expectedInstructions3 = {"0110 1000 0000 1100",
                                        "0110 1000 0000 0000",
                                        "0110 1000 0011 0000",
                                        "0110 1011 1111 1100",
                                        "0110 1011 1111 1110",
                                        "0110 1001 0000 0110"};

        //expectedInstructions3 is the same as the array that assemble returns with call
        String[] callInstructions = assembler.assemble(callCommands);

        for(int i = 0; i < callCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions3[i].equals(callInstructions[i]))? callCommands[i] + "\t\t|\t" + expectedInstructions3[i] + " - passes.":  callCommands[i] + "\t\t|\t" + expectedInstructions3[i] + " - fails.");
        }
        System.out.println();

        //assembled return commands should return matching expected values to pass
        System.out.println("Test return instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] returnCommand = {"return"};

        String[] expectedInstructions4 = {"0110 1100 0000 0000"};

        //expectedInstructions4 is the same as the array that assemble returns with return
        String[] returnInstruction = assembler.assemble(returnCommand);

        for(int i = 0; i < returnCommand.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions4[i].equals(returnInstruction[i]))? returnCommand[i] + "\t\t|\t" + expectedInstructions4[i] + " - passes.":  returnCommand[i] + "\t\t|\t" + expectedInstructions4[i] + " - fails.");
        }
        System.out.println();            

        //since stacks are LIFO, pushing registers onto the stack and popping them off in the same order should reverse the order in which the values were stored in R0-2
        System.out.println("Test push/pop with CPU-");
        String[] instructionArray1 = {"move R0 -1",     //R0 = -1
                                    "move R1 123",      //R1 = 123
                                    "move R2 68",       //R2 = 68
                                    "push R0",          //stack: -1
                                    "push R1",          //stack: -1 123
                                    "push R2",          //stack: -1 123 68
                                    "pop R0",           //R0 = 68       stack: -1 123
                                    "pop R1",           //R1 = 123      stack: -1
                                    "pop R2",           //R2 = -1       stack:
                                    "interrupt 0"};     //print registers
        CPU testCPU1 = new CPU();
        testCPU1.preload(assembler.assemble(instructionArray1));
        testCPU1.run();
        System.out.println("If assembler works, registers should be printed above with:\nR0 = 123, R1 = 68, R2 = -1, and the rest are 0.\n");
        System.out.println();

        //Demonstrates that call and return work as intended in tandem. Call 4 pushes halt address onto stack and jumps to call 14. Call 14 should jump to return which goes
        //back to the move and subtract commands, print the registers and then return again to halt. R7=114, R2=123, R0=-9, and registers should be printed, not memory.
        System.out.println("Test call/return with CPU-");
        String[] instructionArray2 = {"call 4",                 //0: jump to byte 4     stack: 2
                                    "halt",                     //2: 
                                    "call 14",                  //4: jump to byte 14     stack: 2 6
                                    "move R7 114",              //6: R7 = 114
                                    "move R2 123",              //8: R2 = 123
                                    "subtract R7 R2 R0",        //10: R0 = 114-123 = -9
                                    "interrupt 0",              //12: print registers to screen
                                    "return",                   //14: returns to instruction at byte six first pass, then halt at byte 2 second time
                                    "interrupt 1"};             //16: print memory to screen. Should never be reached
        CPU testCPU2 = new CPU();
        testCPU2.preload(assembler.assemble(instructionArray2));
        testCPU2.run();
        System.out.println("If assembler works, registers should be printed above with:\nR7 = 114, R2 = 123, R0 = -9, and the rest are 0.\n");
        System.out.println();

        //uncomment the block below line by line to get syntax error exceptions
        
        System.out.println("Test error handling in assembler");
        System.out.println("-------------------------------------------------------------------");
        String[] errorCommands = {"call -12",//call with negative arguments
                                //"call R1",//call with register argument
                                //"call 2 13",//call with too many arguments
                                //"call 3",//call with odd number
                                //"call",//call with no arguments
                                //"push",//push with missing argument
                                //"push R",//push with invalid token
                                //"push 13",//push with number
                                //"push R1 R2",//push with too many arguments
                                //"pop",//pop with missing argument
                                //"pop R",//pop with invalid token
                                //"pop 13",//pop with number
                                //"pop R1 R2",//pop with too many arguments
                                //"return 0",//too many arguments for halt
                                };
        assembler.assemble(errorCommands);
        

        /*
        //exception should be thrown because stack is empty upon initialization
        System.out.println("Test empty stack-");
        String[] instructionArray3 = {"pop R0"};           
        CPU testCPU3 = new CPU();
        testCPU3.preload(assembler.assemble(instructionArray3));
        testCPU3.run();
        System.out.println();
        */
    }
}
