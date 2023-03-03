/*
Author: My Tran
Filename: CPU_test2.java
Description: Program performs unit testing on the methods of CPU class
*/
public class CPU_test2 
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
        System.out.println("Starting CPU_test2");

        testCPU();

        System.out.println("Ending test...");
    }

    public static void testCPU() throws Exception
    {
        System.out.println("Testing CPU:");

        //Upon run... jump to Byte 8 so moves to R0-R2 should be skipped. Then jump to Byte 18 so moves to R4-R6 should be skipped
        //so R3, R7, and R8 should be filled and registers should print to screen to indicate jump forward works
        System.out.println("Test Jump 1 -");
        String[] instructionArray0 = {"0011 0000 0000 1000", //jump 8
                                    "0001 0000 1101 1011", //Move R0 -37
                                    "0001 0001 0010 0111",  //Move R1 39
                                    "0001 0010 0000 0111",  //Move R2 7
                                    "0001 0011 0001 1111",  //Move R3 31
                                    "0011 0000 0001 0010",  //jump 18
                                    "0001 0100 0010 0101",  //Move R4 37
                                    "0001 0101 1001 0111",  //Move R5 -105
                                    "0001 0110 0111 0111",  //Move R6 119
                                    "0001 0111 0100 0111",  //Move R7 71
                                    "0001 1000 1000 0001",  //Move R8 -127
                                    "0010 0000 0000 0000"};  //print registers to screen
        CPU testJump1 = new CPU();
        testJump1.preload(instructionArray0);
        testJump1.run();
        System.out.println("If jump works, registers should be printed above with R3 = -37, R7 = 71, R8 = -127, the rest are 0.\n");

        //Upon run... PC should jump to 6 to segment of memory with move instructions. Then R2-R8 are filled in
        //and then jumps back to Byte 2 to print. Should print registers with registers R2-R8 filled corresponding
        //to values given below to show jump backwards works
        System.out.println("Test Jump 2 -");
        String[] instructionArray1 = {"0011 0000 0000 0110", //jump 6
                                    "0010 0000 0000 0000", //print registers to screen
                                    "0000 0000 0000 0000",  //halt
                                    "0001 0010 1111 1111",  //Move R2 -1
                                    "0001 0011 0001 1111",  //Move R3 31
                                    "0001 0100 0010 0101",  //Move R4 37
                                    "0001 0101 1001 0111",  //Move R5 -105
                                    "0001 0110 0111 0111",  //Move R6 119
                                    "0001 0111 0100 0111",  //Move R7 71
                                    "0001 1000 1000 0001",  //Move R8 -127
                                    "0011 0000 0000 0010"};  //jump 2
        CPU testJump2 = new CPU();
        testJump2.preload(instructionArray1);
        testJump2.run();
        System.out.println("If jump works, registers should be printed above with R2 = -1, R3 = 31, R4 = 37, R5 = -105, R6 = 119, R7 = 71, R8 = 127.\n");

        //two registers are filled with same number and branchIfEqual jumps to the interrupt after the halt.
        //If registers are printed, branch ifEqual works and jump to interrupt happened.
        System.out.println("Test Compare and branchIfEqual 1-");
        String[] instructionArray2 = {"0001 0000 0001 0000",    //move R0 16
                                    "0001 0001 0001 0000",      //move R1 16
                                    "0100 0000 0000 0001",      //compare R0 R1
                                    "0101 0100 0000 0010",     //branchIfEqual 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testEqual1 = new CPU();
        testEqual1.preload(instructionArray2);
        testEqual1.run();
        System.out.println("If branchIfEquals works, registers should be printed above with R0 = 16, R1 = 16, and the rest are 0.\n");

        //two registers are filled with the different number and branchIfEqual jumps to the jumps to the interrupt after the halt.
        //If no registers are printed, branchIfEqual works and jump did not happen.
        System.out.println("Test Compare and branchIfEqual 2-");
        String[] instructionArray3 = {"0001 0010 0000 1111",    //move R2 15
                                    "0001 0001 0010 0001",      //move R1 31
                                    "0100 0000 0010 0001",      //compare R2 R1
                                    "0101 0100 0000 0010 ",     //branchIfEqual 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testEqual2 = new CPU();
        testEqual2.preload(instructionArray3);
        testEqual2.run();
        System.out.println("If branchIfEquals works, nothing should be printed above.\n");

        //69 is moved into R4 and -1 into R3. 69 > -1 so branch should happen and jump to interrupt. So if registers are printed,
        //branchIfGreaterThan works for greater value in first register.
        System.out.println("Test Compare and branchIfGreaterThan 1-");
        String[] instructionArray4 = {"0001 0100 0100 0101",    //move R4 69
                                    "0001 0011 1111 1111",      //move R3 -1
                                    "0100 0000 0100 0011",      //compare R4 R3
                                    "0101 1000 0000 0010 ",     //branchIfGreaterThan 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testGreater1 = new CPU();
        testGreater1.preload(instructionArray4);
        testGreater1.run();
        System.out.println("If branchIfGreater works, registers should be printed above with R4 = 69, R3 = -1, and the rest are 0.\n");

        //1 is moved into R5 and 127 into R7. 1 < 127 so branch should not happen and halt should be reached. So if no registers
        //are printed, branchIfGreaterThan works for lesser value in first register.
        System.out.println("Test Compare and branchIfGreaterThan 2-");
        String[] instructionArray5 = {"0001 0101 0000 0001",    //move R5 1
                                    "0001 0111 0111 1111",      //move R7 127
                                    "0100 0000 0101 0111",      //compare R5 R7
                                    "0101 1000 0000 0010 ",     //branchIfGreaterThan 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testGreater2 = new CPU();
        testGreater2.preload(instructionArray5);
        testGreater2.run();
        System.out.println("If branchIfGreaterThan works, nothing should be printed above.\n");

        //36 is moved into R8 and R10. 36 = 36 so branch should happen and jump to interrupt. So if no registers are printed, 
        //branchIfGreaterThan works for equal values in first register.
        System.out.println("Test Compare and branchIfGreaterThan 3-");
        String[] instructionArray6 = {"0001 1000 0010 0100",    //move R8 36
                                    "0001 1010 0010 0100",      //move R10 36
                                    "0100 0000 1000 1010",      //compare R8 R10
                                    "0101 1000 0000 0010 ",     //branchIfGreaterThan 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testGreater3 = new CPU();
        testGreater3.preload(instructionArray6);
        testGreater3.run();
        System.out.println("If branchIfGreaterThan works, nothing should be printed above.\n");

        //19 is moved into R11 and R12. 19 = 19 so branchIfNotEqual should not jump to the interrupt after the halt.
        //If no registers are printed, branchIfNotEqual works and jump did not happen.
        System.out.println("Test Compare and branchIfNotEqual 1-");
        String[] instructionArray7 = {"0001 1011 0001 0011",    //move R11 19
                                    "0001 1100 0001 0011",      //move R12 19
                                    "0100 0000 1011 1100",      //compare R11 R12
                                    "0101 0000 0000 0010",     //branchIfNotEqual 1
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testNotEqual1 = new CPU();
        testNotEqual1.preload(instructionArray7);
        testNotEqual1.run();
        System.out.println("If branchIfNotEqual works, nothing should be printed above.\n");

        //15 is moved into R15 and 48 is moved into R12. 15 != 48 so branchIfNotEqual should jump to the interrupt after the halt.
        //If registers are printed, branch ifNotEqual works and jump to interrupt happened.
        System.out.println("Test Compare and branchIfNotEqual 2-");
        String[] instructionArray8 = {"0001 1111 0000 1111",    //move R15 15
                                    "0001 1110 0011 0000",      //move R14 48
                                    "0100 0000 1111 1110",      //compare R15 R14
                                    "0101 0000 0000 0010 ",     //branchIfNotEqual 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testNotEqual2 = new CPU();
        testNotEqual2.preload(instructionArray8);
        testNotEqual2.run();
        System.out.println("If branchIfNotEqual works, registers should be printed above with R15 = 15, R14 = 48, and the rest are 0.\n");

         //64 is moved into R13 and -3 into R12. 16 >= 15 so branch should happen and jump to interrupt. So if registers are printed,
        //branchIfGreaterThanOrEqual works for greater value in first register.
        System.out.println("Test Compare and branchIfGreaterThanOrEqual 1-");
        String[] instructionArray9 = {"0001 1101 0100 0000",    //move R13 64
                                    "0001 1100 1111 1101",      //move R12 -3
                                    "0100 0000 1101 1100",      //compare R13 R12
                                    "0101 1100 0000 0010 ",     //branchIfGreaterThanOrEqual 1
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testGreaterEqual1 = new CPU();
        testGreaterEqual1.preload(instructionArray9);
        testGreaterEqual1.run();
        System.out.println("If branchIfGreaterThanOrEqual works, registers should be printed above with R13 = 64, R12 = -3, and the rest are 0.\n");

        //1 is moved into R2 and 125 into R1. 1 < 125 so branch should not happen and halt should be reached. So if no registers
        //are printed, branchIfGreaterThanOrEqual works for lesser value in first register.
        System.out.println("Test Compare and branchIfGreaterThanOrEqual 2-");
        String[] instructionArray10 = {"0001 0010 0000 0001",    //move R2 1
                                    "0001 0001 0111 1101",      //move R1 125
                                    "0100 0000 0010 0001",      //compare R2 R1
                                    "0101 1100 0000 0010 ",     //branchIfGreaterThanOrEqual 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testGreaterEqual2 = new CPU();
        testGreaterEqual2.preload(instructionArray10);
        testGreaterEqual2.run();
        System.out.println("If branchIfGreaterThanOrEqual works, nothing should be printed above.\n");

        //4 is moved into R2 and R1. 4 >= 4 so branch should happen and jump to interrupt. So if no registers are printed, 
        //branchIfGreaterThanOrEqual works for equal values in first register.
        System.out.println("Test Compare and branchIfGreaterThanOrEqual 3-");
        String[] instructionArray11 = {"0001 0010 0000 0100",    //move R2 4
                                    "0001 0001 0000 0100",      //move R1 4
                                    "0100 0000 0010 0001",      //compare R2 R1
                                    "0101 1100 0000 0010 ",     //branchIfGreaterThanOrEqual 2
                                    "0000 0000 0000 0000",      //halt
                                    "0010 0000 0000 0000"};     //print registers to screen
        CPU testGreaterEqaul3 = new CPU();
        testGreaterEqaul3.preload(instructionArray11);
        testGreaterEqaul3.run();
        System.out.println("If branchIfGreaterThanOrEqual works, registers should be printed above with R2 = 4, R1 = 4, and the rest are 0.\n");

        System.out.println();
    }
}
