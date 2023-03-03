/*
Author: My Tran
Filename: CPU_test1.java
Description: Program performs unit testing on the methods of CPU class
*/
public class CPU_test1 
{
    public static void main(String args[]) throws Exception
    {
        bit_test.runTests();
        longword_test.runTests();
        rippleAdder_test.runTests();
        multiplier_test.runTests();
        ALU_test.runTests();
        memory_test.runTests();
        runTests();
    }    

    public static void runTests() throws Exception
    {
        System.out.println("Starting CPU_test1");

        testCPU();

        System.out.println("Ending test...");
    }

    public static void testCPU() throws Exception
    {
        System.out.println("Testing CPU:");

        //interrupt 1 is tested while testing preload
        System.out.println("Test Preload -");
        String[] instructionArray0 = {"0001 0000 1101 1011", //Move R0 -37
                                    "0001 0001 0010 0111",  //Move R1 39
                                    "0001 0010 0000 0111",  //Move R2 7
                                    "0001 0011 0001 1111",  //Move R3 31
                                    "0001 0100 0010 0101",  //Move R4 37
                                    "0001 0101 1001 0111",  //Move R5 -105
                                    "0001 0110 0111 0111",  //Move R6 119
                                    "0001 0111 0100 0111",  //Move R7 71
                                    "0001 1000 1000 0001",  //Move R8 -127
                                    "0010 0000 0000 0001"};  //print all memory to screen
        //these first instructions should appear in the first bytes of memory, the rest being 0
        CPU testPreload = new CPU();
        testPreload.preload(instructionArray0);
        testPreload.run();
        System.out.println();

        //interrupt 0 is tested while testing Move
        System.out.println("Test Move instruction -");
        String[] instructionArray1 = {"0001 0000 1101 1011", //Move R0 -37
                                    "0001 0001 0010 0111",  //Move R1 39
                                    "0001 0010 0000 0111",  //Move R2 7
                                    "0001 0011 0001 1111",  //Move R3 31
                                    "0001 0100 0010 0101",  //Move R4 37
                                    "0001 0101 1001 0111",  //Move R5 -105
                                    "0001 0110 0111 0111",  //Move R6 119
                                    "0001 0111 0100 0111",  //Move R7 71
                                    "0001 1000 1000 0001",  //Move R8 -127
                                    "0010 0000 0000 0000",  //print registers to screen
                                    "0000 0000 0000 0000",  //halt
                                    "0010 0000 0000 0001",  //print all memory to screen
                                    "0001 1011 0000 0111",  //Move R11 7
                                    "0001 1100 0000 0111",  //Move R12 7
                                    "0010 0000 0000 0001",  //print all memory to screen
                                    "0000 0000 0000 0000",  //halt
                                    "0010 0000 0000 0000"}; //print registers to screen
        //only R0-R8 should be filled in this loop if halt works correctly and output should reflect comments above up to R8
        CPU testMove = new CPU();
        testMove.preload(instructionArray1);
        testMove.run();
        System.out.println();

        System.out.println("Test ALU instructions -");
        //testing ALU operations
        String[] instructionArray2 = {"0001 0000 0001 1111", //Move R0 31   R0 = 31
                                    "0001 0001 0000 0101",  //Move R1 5     R1 = 5
                                    "1110 0000 0001 0010",  //Add R0 R1 R2  R2 = 36
                                    "1111 0000 0001 0011",  //Sub R0 R1 R3  R3 = 26
                                    "1100 0000 0001 0100",  //SHL R0 R1 R4   R4 = 992
                                    "1101 0000 0001 0101",  //SHR R0 R1 R5   R5 = 0
                                    "1010 0000 0000 0110",  //XOR R0 R1 R6  R6 = 0
                                    "1011 0000 0001 0111",  //NOT R0 R1 R7  R7 = -32
                                    "0001 1000 1111 1111",  //Move R8 -1    R8 = -1
                                    "1000 0000 1000 1001",  //AND R0 R8 R9  R9 = 31
                                    "1001 0000 1000 1010",  //OR R0 R8 R10  R10 = -1
                                    "0111 0000 1000 1011",  //Mul R0 R8 R11 R11 = -31
                                    "0010 0000 0000 0000"}; //print registers to screen
        //register values should reflect comments above
        CPU testALU = new CPU();
        testALU.preload(instructionArray2);
        testALU.run();
        System.out.println();

        System.out.println();
    }
}
