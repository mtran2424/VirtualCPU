/*
Author: My Tran
Filename: CPU_test3.java
Description: Program performs unit testing on the methods of CPU class
*/
public class CPU_test3
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
        runTests();
    }    

    public static void runTests() throws Exception
    {
        System.out.println("Starting CPU_test3");

        testCPU();

        System.out.println("Ending test...");
    }

    public static void testCPU() throws Exception
    {
        System.out.println("Testing CPU:");

        //R0-3 are pushed onto the stack in order and stacks are LIFO so bottom of memory should be values in R3-0 consecutively
        System.out.println("Test push-");
        String[] instructionArray0 = {"0001 0000 1101 1011", //Move R0 -37
                                    "0001 0001 0010 0001",  //Move R1 1
                                    "0001 0010 0111 1111",  //Move R2 127
                                    "0001 0011 0001 1111",  //Move R3 31
                                    "0110 0000 0000 0000",  //push R0       stack -37
                                    "0110 0000 0000 0001",  //push R1       stack -37 1
                                    "0110 0000 0000 0010",  //push R2       stack -37 1 127
                                    "0110 0000 0000 0011",  //push R3       stack -37 1 127 31
                                    "0010 0000 0000 0001"};  //print memory to screen
        CPU testPush = new CPU();
        testPush.preload(instructionArray0);
        testPush.run();
        System.out.println("Memory should be printed above. If push works, at the bottom of memory, from the bottom up should read in longword form, -37, 1, 127, 31\n");

        //Pushing R3-5 and then popping them into R0-2 should result in R0=R5, R1=R4, R2=R3 because the top of the stack is R5, followed by R4, then R3
        System.out.println("Test pop-");
        String[] instructionArray1 = {"0001 0011 0011 1111",  //Move R3 63
                                    "0001 0100 0010 1000",  //Move R4 40
                                    "0001 0101 1001 0111",  //Move R5 -105
                                    "0110 0000 0000 0011",  //push R3       stack 63
                                    "0110 0000 0000 0100",  //push R4       stack 63 40
                                    "0110 0000 0000 0101",  //push R5       stack 63 40 -105
                                    "0110 0100 0000 0000",  //pop R0        stack 63 40         R0 = -105
                                    "0110 0100 0000 0001",  //pop R1        stack 63            R1 = 40
                                    "0110 0100 0000 0010",  //pop R2        stack               R2 = 63
                                    "0010 0000 0000 0000"}; //print registers to screen
        CPU testPop = new CPU();
        testPop.preload(instructionArray1);
        testPop.run();
        System.out.println("Registers should be printed above. If pop works, R0=-105, R1=40, R2=63, R3=63, R4=40, R5=-105.\n");

        //Call works properly if no halts happen until the end because the calls are jumping to eachother and the last call is jumping to the interrupt in the middle.
        //The stack should from top to bottom have the values 2 (call 6 is at 0), 8 (call 10 is at 6), and 10 (call 2 is at 12)
        System.out.println("Test call-");
        String[] instructionArray2 = {"0110 1000 0000 0110",    //0:call 6              //stack 2   jumps to call 10
                                    "0010 0000 0000 0001",      //2:interrupt 1     //print memory to screen
                                    "0000 0000 0000 0000",      //4:halt    //if reached before call 2, interrupt never happens
                                    "0110 1000 0000 1010",      //6:call 10             //stack 2 8     jumps to call 2
                                    "0000 0000 0000 0000",      //8:halt    //if reached, interrupt never happens
                                    "0110 1000 0000 0010",      //10:call 2             //stack 2 8 12      jumps to interrupt 1
                                    "0000 0000 0000 0000"};     //12:halt   //if reached, interrupt never happens
        CPU testCall = new CPU();
        testCall.preload(instructionArray2);
        testCall.run();
        System.out.println("Memory should be printed above. If call works, at the bottom of memory, from the bottom up should read in longword form, 2, 8, 12.\n");

        //Return is tested by pushing an address onto the stack and using return to jump to the address on top of the stack. If return works properly, then move R7 69
        //should happen, return should jump back to interrupt 1 and halt after. This means that move R13 42 after jump and interrupt at the end should never happen.
        //R15=8 and R7=69
        System.out.println("Test return-");
        String[] instructionArray3 = {"0001 1111 0000 1000",    //0:move R15 8
                                    "0110 0000 0000 1111",      //2:push R15        //stack 8
                                    "0011 0000 0000 1100",      //4:jump 12         //jumps to move R7 69
                                    "0001 1101 0010 1010",      //6:move R13 42     //should be skipped
                                    "0010 0000 0000 0000",      //8:interrupt 1     //print register to screen
                                    "0000 0000 0000 0000",      //10:halt    //should be where run is halted
                                    "0001 0111 0100 0101",      //12:move R7 69
                                    "0110 1100 0000 0010",      //14:return         //jumps to interrupt 1
                                    "0010 0000 0000 0001"};     //16:interrupt 0    //print memory to screen. Should not be reached.
        CPU testReturn = new CPU();
        testReturn.preload(instructionArray3);
        testReturn.run();
        System.out.println("Registers should be printed above. If return works, R15=8, R7=69, and everything else should be 0.\n");

        //Call works with return if move R2 127 is never reached, R1 = -1 and R15 = 1, and interrupt 0 happens. Call 6 jumps to the move commands, and returns to 
        //the point after call 6 with is an interrupt and then a halt so move R2 127 is never reached.
        System.out.println("Test call and return-");
        String[] instructionArray4 = {"0110 1000 0000 0110",    //0:call 6      //stack 2
                                    "0010 0000 0000 0000",      //2:interrupt 0     //print registers to screen
                                    "0000 0000 0000 0000",      //4:halt
                                    "0001 1111 0000 0001",      //6:move R15 1
                                    "0001 0001 1111 1111",      //8:move R1 -1
                                    "0110 1100 0000 0000",      //10:return     //stack     jump to 2
                                    "0001 0010 0111 1111"};     //12:move R2 127
        CPU testCallReturn1 = new CPU();
        testCallReturn1.preload(instructionArray4);
        testCallReturn1.run();
        System.out.println("Registers should be printed above. If call and return work, R1=-1, R15=1, and the rest are 0\n");

        System.out.println();

        int k = 49;
        int i = 3;
        System.out.println(((k % 7) + (i * (1 + (k % 13))))%7);
    }
}
