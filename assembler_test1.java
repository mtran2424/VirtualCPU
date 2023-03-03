/*
Author: My Tran
Filename: assembler_test1.java
Description: Program performs unit testing on the methods of assembler class
*/
public class assembler_test1 
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
        runTests();
    }

    public static void runTests() throws Exception
    {
        System.out.println("Starting assembler_test1.java...\n");

        testAssemble();

        System.out.println("Ending test...");
    }

    public static void testAssemble() throws Exception
    {
        System.out.println("Test assemble method:\n");

        //assembled move commands should return matching expected values to pass
        System.out.println("Test move instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] moveCommands = {"move R1 -128",
                                "move R2 99",
                                "move R9 31",
                                "move R7 -121",
                                "move R8 -1", 
                                "move R0 0"};

        String[] expectedInstructions1 = {"0001 0001 1000 0000",
                                        "0001 0010 0110 0011",
                                        "0001 1001 0001 1111",
                                        "0001 0111 1000 0111",
                                        "0001 1000 1111 1111",
                                        "0001 0000 0000 0000"};

        //expectedInstructions1 is the same as the array that assemble returns with move
        String[] moveInstructions = assembler.assemble(moveCommands);

        for(int i = 0; i < moveCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions1[i].equals(moveInstructions[i]))? moveCommands[i] + "\t\t|\t" + expectedInstructions1[i] + " - passes.":  moveCommands[i] + "\t|\t" + expectedInstructions1[i] + " - fails.");
        }
        System.out.println();

        //assembled add commands should return matching expected values to pass
        System.out.println("Test add instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] addCommands = {"add R0 R1 R2",
                                "add R15 R14 R13",
                                "add R3 R5 R4",
                                "add R6 R7 R8",
                                "add R9 R12 R11", 
                                "add R0 R8 R15"};

        String[] expectedInstructions2 = {"1110 0000 0001 0010",
                                        "1110 1111 1110 1101",
                                        "1110 0011 0101 0100",
                                        "1110 0110 0111 1000",
                                        "1110 1001 1100 1011",
                                        "1110 0000 1000 1111"};

        //expectedInstructions2 is the same as the array that assemble returns with add
        String[] addInstructions = assembler.assemble(addCommands);

        for(int i = 0; i < addCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions2[i].equals(addInstructions[i]))? addCommands[i] + "\t\t|\t" + expectedInstructions2[i] + " - passes.":  addCommands[i] + "\t|\t" + expectedInstructions2[i] + " - fails.");
        }
        System.out.println();

        //assembled subtract commands should return matching expected values to pass
        System.out.println("Test subtract instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] subCommands = {"subtract R0 R1 R2",
                                "subtract R15 R14 R13",
                                "subtract R3 R5 R4",
                                "subtract R6 R7 R8",
                                "subtract R9 R12 R11", 
                                "subtract R0 R8 R15"};

        String[] expectedInstructions3 = {"1111 0000 0001 0010",
                                        "1111 1111 1110 1101",
                                        "1111 0011 0101 0100",
                                        "1111 0110 0111 1000",
                                        "1111 1001 1100 1011",
                                        "1111 0000 1000 1111"};

        //expectedInstructions3 is the same as the array that assemble returns with sub
        String[] subInstructions = assembler.assemble(subCommands);

        for(int i = 0; i < subCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions3[i].equals(subInstructions[i]))? subCommands[i] + "\t\t|\t" + expectedInstructions3[i] + " - passes.":  subCommands[i] + "\t|\t" + expectedInstructions3[i] + " - fails.");
        }
        System.out.println();

        //assembled multiply commands should return matching expected values to pass
        System.out.println("Test multiply instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] multiplyCommands = {"multiply R0 R1 R2",
                                "multiply R15 R14 R13",
                                "multiply R3 R5 R4",
                                "multiply R6 R7 R8",
                                "multiply R9 R12 R11", 
                                "multiply R0 R8 R15"};

        String[] expectedInstructions4 = {"0111 0000 0001 0010",
                                        "0111 1111 1110 1101",
                                        "0111 0011 0101 0100",
                                        "0111 0110 0111 1000",
                                        "0111 1001 1100 1011",
                                        "0111 0000 1000 1111"};

        //expectedInstructions4 is the same as the array that assemble returns with multiply
        String[] multiplyInstructions = assembler.assemble(multiplyCommands);

        for(int i = 0; i < multiplyCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions4[i].equals(multiplyInstructions[i]))? multiplyCommands[i] + "\t\t|\t" + expectedInstructions4[i] + " - passes.":  multiplyCommands[i] + "\t|\t" + expectedInstructions4[i] + " - fails.");
        }
        System.out.println();

        //assembled leftShift commands should return matching expected values to pass
        System.out.println("Test leftshift instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] leftShiftCommands = {"leftshift R0 R1 R2",
                                "leftshift R15 R14 R13",
                                "leftshift R3 R5 R4",
                                "leftshift R6 R7 R8",
                                "leftshift R9 R12 R11", 
                                "leftshift R0 R8 R15"};

        String[] expectedInstructions5 = {"1100 0000 0001 0010",
                                        "1100 1111 1110 1101",
                                        "1100 0011 0101 0100",
                                        "1100 0110 0111 1000",
                                        "1100 1001 1100 1011",
                                        "1100 0000 1000 1111"};

        String[] leftShiftInstructions = assembler.assemble(leftShiftCommands);//expectedInstructions5 is the same as the array that assemble returns with leftshift

        for(int i = 0; i < leftShiftCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions5[i].equals(leftShiftInstructions[i]))? leftShiftCommands[i] + "\t\t|\t" + expectedInstructions5[i] + " - passes.":  leftShiftCommands[i] + "\t|\t" + expectedInstructions5[i] + " - fails.");
        }
        System.out.println();

        //assembled rightShift commands should return matching expected values to pass
        System.out.println("Test rightshift instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] rightShiftCommands = {"rightshift R0 R1 R2",
                                "rightshift R15 R14 R13",
                                "rightshift R3 R5 R4",
                                "rightshift R6 R7 R8",
                                "rightshift R9 R12 R11", 
                                "rightshift R0 R8 R15"};

        String[] expectedInstructions6 = {"1101 0000 0001 0010",
                                        "1101 1111 1110 1101",
                                        "1101 0011 0101 0100",
                                        "1101 0110 0111 1000",
                                        "1101 1001 1100 1011",
                                        "1101 0000 1000 1111"};

        String[] rightShiftInstructions = assembler.assemble(rightShiftCommands);//expectedInstructions6 is the same as the array that assemble returns with rightshift

        for(int i = 0; i < rightShiftCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions6[i].equals(rightShiftInstructions[i]))? rightShiftCommands[i] + "\t\t|\t" + expectedInstructions6[i] + " - passes.":  rightShiftCommands[i] + "\t|\t" + expectedInstructions6[i] + " - fails.");
        }
        System.out.println();

        //assembled and commands should return matching expected values to pass
        System.out.println("Test and instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] andCommands = {"and R0 R1 R2",
                                "and R15 R14 R13",
                                "and R3 R5 R4",
                                "and R6 R7 R8",
                                "and R9 R12 R11", 
                                "and R0 R8 R15"};

        String[] expectedInstructions7 = {"1000 0000 0001 0010",
                                        "1000 1111 1110 1101",
                                        "1000 0011 0101 0100",
                                        "1000 0110 0111 1000",
                                        "1000 1001 1100 1011",
                                        "1000 0000 1000 1111"};

        String[] andInstructions = assembler.assemble(andCommands);//expectedInstructions7 is the same as the array that assemble returns with and

        for(int i = 0; i < andCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions7[i].equals(andInstructions[i]))? andCommands[i] + "\t\t|\t" + expectedInstructions7[i] + " - passes.":  andCommands[i] + "\t|\t" + expectedInstructions7[i] + " - fails.");
        }
        System.out.println();

        //assembled or commands should return matching expected values to pass
        System.out.println("Test or instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] orCommands = {"or R0 R1 R2",
                                "or R15 R14 R13",
                                "or R3 R5 R4",
                                "or R6 R7 R8",
                                "or R9 R12 R11", 
                                "or R0 R8 R15"};

        String[] expectedInstructions8 = {"1001 0000 0001 0010",
                                        "1001 1111 1110 1101",
                                        "1001 0011 0101 0100",
                                        "1001 0110 0111 1000",
                                        "1001 1001 1100 1011",
                                        "1001 0000 1000 1111"};

        String[] orInstructions = assembler.assemble(orCommands);//expectedInstructions8 is the same as the array that assemble returns with or

        for(int i = 0; i < orCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions8[i].equals(orInstructions[i]))? orCommands[i] + "\t\t|\t" + expectedInstructions8[i] + " - passes.":  orCommands[i] + "\t|\t" + expectedInstructions8[i] + " - fails.");
        }
        System.out.println();

        //assembled xor commands should return matching expected values to pass
        System.out.println("Test xor instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] xorCommands = {"xor R0 R1 R2",
                                "xor R15 R14 R13",
                                "xor R3 R5 R4",
                                "xor R6 R7 R8",
                                "xor R9 R12 R11", 
                                "xor R0 R8 R15"};

        String[] expectedInstructions9 = {"1010 0000 0001 0010",
                                        "1010 1111 1110 1101",
                                        "1010 0011 0101 0100",
                                        "1010 0110 0111 1000",
                                        "1010 1001 1100 1011",
                                        "1010 0000 1000 1111"};

        String[] xorInstructions = assembler.assemble(xorCommands);//expectedInstructions9 is the same as the array that assemble returns with xor

        for(int i = 0; i < xorCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions9[i].equals(xorInstructions[i]))? xorCommands[i] + "\t\t|\t" + expectedInstructions9[i] + " - passes.":  xorCommands[i] + "\t|\t" + expectedInstructions9[i] + " - fails.");
        }
        System.out.println();

        //assembled not commands should return matching expected values to pass
        System.out.println("Test not instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] notCommands = {"not R0 R1 R2",
                                "not R15 R14 R13",
                                "not R3 R5 R4",
                                "not R6 R7 R8",
                                "not R9 R12 R11", 

                                "not R0 R8 R15"};
        String[] expectedInstructions10 = {"1011 0000 0001 0010",
                                        "1011 1111 1110 1101",
                                        "1011 0011 0101 0100",
                                        "1011 0110 0111 1000",
                                        "1011 1001 1100 1011",
                                        "1011 0000 1000 1111"};

        String[] notInstructions = assembler.assemble(notCommands);//expectedInstructions10 is the same as the array that assemble returns with not

        for(int i = 0; i < notCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions10[i].equals(notInstructions[i]))? notCommands[i] + "\t\t|\t" + expectedInstructions10[i] + " - passes.":  notCommands[i] + "\t|\t" + expectedInstructions10[i] + " - fails.");
        }
        System.out.println();

        //assembled halt commands should return matching expected values to pass
        System.out.println("Test halt instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] haltCommand = {"halt"};
        String[] expectedInstructions11 = {"0000 0000 0000 0000"};

        String[] haltInstruction = assembler.assemble(haltCommand);//expectedInstructions11 is the same as the array that assemble returns with halt

        //compare resulting String array with expected values
        System.out.println((expectedInstructions11[0].equals(haltInstruction[0]))? haltCommand[0] + "\t\t|\t" + expectedInstructions11[0] + " - passes.":  haltCommand[0] + "\t|\t" + expectedInstructions10[0] + " - fails.");
        System.out.println();

        //assembled interrupt commands should return matching expected values to pass
        System.out.println("Test interrupt instructions");
        System.out.println("-------------------------------------------------------------------");
        String[] interruptCommands = {"interrupt 0",
                                "interrupt 1",
                                "interrupt 4",
                                "interrupt 9"};
        String[] expectedInstructions12 = {"0010 0000 0000 0000",
                                        "0010 0000 0000 0001",
                                        "0010 0000 0000 0100",
                                        "0010 0000 0000 1001"};

        String[] interruptInstructions = assembler.assemble(interruptCommands);//expectedInstructions12 is the same as the array that assemble returns with interrupt

        for(int i = 0; i < interruptCommands.length; i++)//compare resulting String array with expected values
        {
            System.out.println((expectedInstructions12[i].equals(interruptInstructions[i]))? interruptInstructions[i] + "\t\t|\t" + expectedInstructions12[i] + " - passes.":  interruptInstructions[i] + "\t|\t" + expectedInstructions12[i] + " - fails.");
        }
        System.out.println();

        System.out.println("Test assemble with CPU");
        System.out.println("-------------------------------------------------------------------");
        String[] cpuCommands = {"move R1 127",
                                "move R2 99",
                                "xor R1 R14 R13",
                                "xor R2 R3 R4",
                                "move R8 -1", 
                                "move R7 -4",
                                "and R6 R7 R8",
                                "and R9 R12 R11",
                                "leftshift R0 R1 R2",
                                "rightshift R15 R14 R13",
                                "interrupt 0",
                                "halt",
                                "interrupt 1"};
        CPU testCPU = new CPU();
        testCPU.preload(assembler.assemble(cpuCommands));//CPU should have instructions in memory corresponding to commands above
        testCPU.run();//registers should be printed becasue interrupt 0 comes before the falt
        System.out.println("-------------------------------------------------------------------");
        testCPU.run();//all memory should be printed because interrupt 1 comes after the halt

        //uncomment the block below line by line to get syntax error exceptions
        /*
        System.out.println("Test error handling in assembler");
        System.out.println("-------------------------------------------------------------------");
        String[] errorCommands = {//"move R1 R0",//invalid argument with move
                                //"move R2 99 30",//too many arguments with move
                                //"xor R1 R14 R13 R0",//too many arguments with ALU ops
                                //"xor R2 R3",//missing arguments with ALU ops
                                //"move R8",//missing argument with move
                                //"move R7 -4.0",//invalid argument integer with move
                                //"and R6 R7 R#",//invalid token with ALU
                                //"R9 R12 R11",//missing operation in command
                                //"leftshift 12",//invalid argument with ALU ops
                                //"rightshift R15 R14 R16",//invalid token for ALU
                                //"interrupt",//missing argument for interrupt
                                //"halt 0",//too many arguments for halt
                                //"interrupt 1 21",//too many arguments for interrupt
                                //"move R2 21$12"//invalid token in move
                                };
        assembler.assemble(errorCommands);
        */
        System.out.println();
    }
}
