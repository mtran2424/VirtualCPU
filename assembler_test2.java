/*
Author: My Tran
Filename: assembler_test2.java
Description: Program performs unit testing on the methods of assembler class
*/
public class assembler_test2 
{
    public static void main(String args[]) throws Exception
    {
        bit_test.runTests();
        longword_test.runTests();
        rippleAdder_test.runTests();
        multiplier_test.runTests();
        ALU_test.runTests();
        memory_test.runTests();
        CPU_test1.runTests();
        CPU_test2.runTests();
        assembler_test1.runTests();
        runTests();
    }

    public static void runTests() throws Exception
    {
        System.out.println("Starting assembler_test2.java...\n");

        testAssemble();

        System.out.println("Ending test...");
    }

    public static void testAssemble() throws Exception
    {
        System.out.println("Test assemble method:\n");

        //assembled jump commands should return matching expected values to pass
        System.out.println("Test jump instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] jumpCommands = {"jump 128",
                                "jump 800",
                                "jump 0",
                                "jump 6",
                                "jump 1020", 
                                "jump 1022"};

        String[] expectedInstructions1 = {"0011 0000 1000 0000",
                                        "0011 0011 0010 0000",
                                        "0011 0000 0000 0000",
                                        "0011 0000 0000 0110",
                                        "0011 0011 1111 1100",
                                        "0011 0011 1111 1110"};

        //expectedInstructions1 is the same as the array that assemble returns with jump
        String[] jumpInstructions = assembler.assemble(jumpCommands);

        for(int i = 0; i < jumpCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions1[i].equals(jumpInstructions[i]))? jumpCommands[i] + "\t\t|\t" + expectedInstructions1[i] + " - passes.":  jumpCommands[i] + "\t\t|\t" + expectedInstructions1[i] + " - fails.");
        }
        System.out.println();

        //assembled compare commands should return matching expected values to pass
        System.out.println("Test compare instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] compareCommands = {"compare R1 R2",
                                "compare R2 R0",
                                "compare R4 R10",
                                "compare R6 R1",
                                "compare R0 R15", 
                                "compare R5 R8"};

        String[] expectedInstructions2 = {"0100 0000 0001 0010",
                                        "0100 0000 0010 0000",
                                        "0100 0000 0100 1010",
                                        "0100 0000 0110 0001",
                                        "0100 0000 0000 1111",
                                        "0100 0000 0101 1000"};

        //expectedInstructions2 is the same as the array that assemble returns with compare
        String[] compareInstructions = assembler.assemble(compareCommands);

        for(int i = 0; i < compareCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions2[i].equals(compareInstructions[i]))? compareCommands[i] + "\t\t|\t" + expectedInstructions2[i] + " - passes.":  compareCommands[i] + "\t\t|\t" + expectedInstructions2[i] + " - fails.");
        }
        System.out.println();
        
        //assembled branchIfEqual commands should return matching expected values to pass
        System.out.println("Test branchIfEqual instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] ifEqualCommands = {"branchIfEqual 12",
                                "branchIfEqual 0",
                                "branchIfEqual -4",
                                "branchIfEqual 1022",
                                "branchIfEqual -1022", 
                                "branchIfEqual 262"};

        String[] expectedInstructions3 = {"0101 0100 0000 1100",
                                        "0101 0100 0000 0000",
                                        "0101 0111 1111 1100",
                                        "0101 0101 1111 1110",
                                        "0101 0110 0000 0010",
                                        "0101 0101 0000 0110"};

        //expectedInstructions3 is the same as the array that assemble returns with branchIfEqual
        String[] ifEqualInstructions = assembler.assemble(ifEqualCommands);

        for(int i = 0; i < ifEqualCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions3[i].equals(ifEqualInstructions[i]))? ifEqualCommands[i] + "\t\t|\t" + expectedInstructions3[i] + " - passes.":  ifEqualCommands[i] + "\t\t|\t" + expectedInstructions3[i] + " - fails.");
        }
        System.out.println();

        //assembled branchIfNotEqual commands should return matching expected values to pass
        System.out.println("Test branchIfNotEqual instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] ifNotEqualCommands = {"branchIfNotEqual 12",
                                "branchIfNotEqual 0",
                                "branchIfNotEqual -4",
                                "branchIfNotEqual 1022",
                                "branchIfNotEqual -1022", 
                                "branchIfNotEqual 262"};

        String[] expectedInstructions4 = {"0101 0000 0000 1100",
                                        "0101 0000 0000 0000",
                                        "0101 0011 1111 1100",
                                        "0101 0001 1111 1110",
                                        "0101 0010 0000 0010",
                                        "0101 0001 0000 0110"};

        //expectedInstructions4 is the same as the array that assemble returns with branchIfNotEqual
        String[] ifNotEqualInstructions = assembler.assemble(ifNotEqualCommands);

        for(int i = 0; i < ifNotEqualCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions4[i].equals(ifNotEqualInstructions[i]))? ifNotEqualCommands[i] + "\t\t|\t" + expectedInstructions4[i] + " - passes.":  ifNotEqualCommands[i] + "\t\t|\t" + expectedInstructions4[i] + " - fails.");
        }
        System.out.println();

        System.out.println("Test branchIfGreaterThanOrEqual instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] ifGreaterThanOrEqualCommands = {"branchIfGreaterThanOrEqual 12",
                                "branchIfGreaterThanOrEqual 0",
                                "branchIfGreaterThanOrEqual -4",
                                "branchIfGreaterThanOrEqual 1022",
                                "branchIfGreaterThanOrEqual -1022", 
                                "branchIfGreaterThanOrEqual 262"};

        String[] expectedInstructions5 = {"0101 1100 0000 1100",
                                        "0101 1100 0000 0000",
                                        "0101 1111 1111 1100",
                                        "0101 1101 1111 1110",
                                        "0101 1110 0000 0010",
                                        "0101 1101 0000 0110"};

        //expectedInstructions5 is the same as the array that assemble returns with branchIfGreaterThanOrEqual
        String[] ifGreaterThanOrEqualInstructions = assembler.assemble(ifGreaterThanOrEqualCommands);

        for(int i = 0; i < ifGreaterThanOrEqualCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions5[i].equals(ifGreaterThanOrEqualInstructions[i]))? ifGreaterThanOrEqualCommands[i] + "\t\t|\t" + expectedInstructions5[i] + " - passes.":  ifGreaterThanOrEqualCommands[i] + "\t\t|\t" + expectedInstructions5[i] + " - fails.");
        }
        System.out.println();

        //assembled branchIfGreaterThan commands should return matching expected values to pass
        System.out.println("Test branchIfGreaterThan instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] ifGreaterThanCommands = {"branchIfGreaterThan 12",
                                "branchIfGreaterThan 0",
                                "branchIfGreaterThan -4",
                                "branchIfGreaterThan 1022",
                                "branchIfGreaterThan -1022", 
                                "branchIfGreaterThan 262"};

        String[] expectedInstructions6 = {"0101 1000 0000 1100",
                                        "0101 1000 0000 0000",
                                        "0101 1011 1111 1100",
                                        "0101 1001 1111 1110",
                                        "0101 1010 0000 0010",
                                        "0101 1001 0000 0110"};

        //expectedInstructions6 is the same as the array that assemble returns with branchIfGreaterThan
        String[] ifGreaterThanInstructions = assembler.assemble(ifGreaterThanCommands);

        for(int i = 0; i < ifGreaterThanCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions6[i].equals(ifGreaterThanInstructions[i]))? ifGreaterThanCommands[i] + "\t\t|\t" + expectedInstructions6[i] + " - passes.":  ifGreaterThanCommands[i] + "\t\t|\t" + expectedInstructions6[i] + " - fails.");
        }
        System.out.println();

        //Byte 8 in memory is loaded as the only interrupt and halts are placed after every branch so registers are only printed when conditional jumps
        //work properly and skip over them. Once the end of the commands are reached, an unconditional jump goes back to the interrupt.
        System.out.println("Test Compare and Branch with CPU-");
        String[] instructionArray1 = {"move R2 64",
                                    "move R1 -3",
                                    "compare R2 R1", 
                                    "branchIfGreaterThanOrEqual 4",
                                    "interrupt 0",
                                    "halt",
                                    "move R3 125",
                                    "move R4 1",
                                    "compare R2 R1", 
                                    "branchIfGreaterThan 2",
                                    "halt",
                                    "move R5 -1",
                                    "move R6 -1",
                                    "compare R5 R6",
                                    "branchIfEqual 2",
                                    "halt",
                                    "move R7 120",
                                    "move R8 19",
                                    "compare R7 R8",
                                    "branchIfNotEqual 2",
                                    "halt",
                                    "jump 8",};
        CPU testCPU1 = new CPU();
        testCPU1.preload(assembler.assemble(instructionArray1));
        testCPU1.run();
        System.out.println("If assembler works, registers should be printed above with:\nR2 = 64, R1 = -3, R3 = 125, R4 = 1, R5 = -1, R6 = -1, R7 = 120, R8 = 19, and the rest are 0.\n");

        //uncomment the block below line by line to get syntax error exceptions
        /*
        System.out.println("Test error handling in assembler");
        System.out.println("-------------------------------------------------------------------");
        String[] errorCommands = {//"jump -12",//jump with negative arguments
                                //"jump R1",//jump with register argument
                                //"jump 2 13",//jump with too many arguments
                                //"jump 3",//jump with odd number
                                //"jump",//jump with no arguments
                                //"compare R2 99",//compare with integer argument
                                //"compare R1 R14 R13 R0",//compare too many arguments
                                //"compare R3",//compare with missing arguments
                                //"compare",//compare with no arguments
                                //"branchIfGreaterThan",//branchIfGreaterThan with missing argument
                                //"branchIfGreaterThan R1",//branchIfGreaterThan with register
                                //"branchIfGreaterThan 13",//branchIfGreaterThan with odd number
                                //"branchIfGreaterThan 12 R2",//branchIfGreaterThan with too many arguments
                                //"branchIfEqual",//branchIfEqual with missing argument
                                //"branchIfEqual R1",//branchIfEqual with register
                                //"branchIfEqual 13",//branchIfEqual with odd number
                                //"branchIfEqual 12 R2",//branchIfEqual with too many arguments
                                //"branchIfGreaterThanOrEqual",//branchIfGreaterThanOrEqual with missing argument
                                //"branchIfGreaterThanOrEqual R1",//branchIfGreaterThanOrEqual with register
                                //"branchIfGreaterThanOrEqual 13",//branchIfGreaterThanOrEqual with odd number
                                //"branchIfGreaterThanOrEqual 12 R2",//branchIfGreaterThanOrEqual with too many arguments
                                //"branchIfNotEqual",//branchIfNotEqual with missing argument
                                //"branchIfNotEqual R1",//branchIfNotEqual with register
                                //"branchIfNotEqual 13",//branchIfNotEqual with odd number
                                //"branchIfNotEqual 12 R2",//branchIfNotEqual with too many arguments
                                };
        assembler.assemble(errorCommands);
        */

        System.out.println();
    }
}
