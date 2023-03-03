/*
Author: My Tran
Filename: ALU_test.java
Description: Program performs unit testing on the methods of ALU class
*/
public class ALU_test 
{
    public static void main(String args[]) throws Exception
    {
        bit_test.runTests();
        longword_test.runTests();
        rippleAdder_test.runTests();;
        multiplier_test.runTests();
        runTests();
    }

    public static void runTests() throws Exception
    {
        System.out.println("Starting ALU_test...");

        doOpTest();

        System.out.println("Endng test...");
    }

    public static void doOpTest() throws Exception
    {
        System.out.println("Testing doOp method:");
        //operationCode[0]: 1000 – and
        //operationCode[1]: 1001 – or
        //operationCode[2]: 1010 – xor
        //operationCode[3]: 1011 – not (not “a”; ignore b)
        //operationCode[4]: 1100 – left shift ( “a” is the value to shift, “b” is the amount to shift it; ignore all but the lowest 5 bits)
        //operationCode[5]: 1101 – right shift ( “a” is the value to shift, “b” is the amount to shift it; ignore all but the lowest 5 bits)
        //operationCode[6]: 1110 – add
        //operationCode[7]: 1111 – subtract
        //operationCode[8]: 0111 - multiply

        bit[][] operationCode = {{new bit(true), new bit(), new bit(), new bit()}, {new bit(true), new bit(), new bit(), new bit(true)},
                            {new bit(true), new bit(), new bit(true), new bit()}, {new bit(true), new bit(), new bit(true), new bit(true)}, 
                            {new bit(true), new bit(true), new bit(), new bit()}, {new bit(true), new bit(true), new bit(), new bit(true)},
                            {new bit(true), new bit(true), new bit(true), new bit()}, {new bit(true), new bit(true), new bit(true), new bit(true)},
                            {new bit(), new bit(true), new bit(true), new bit(true)}};

        //test and
        System.out.println("ALU AND tests-");
        //-1 has all bits set and 0 is all bits unset so results match the AND truth table
        System.out.println(ALU.doOp(operationCode[0], new longword(-1), new longword(-1)).getSigned() == -1 ? "-1 AND -1 passes." : "-1 AND -1 fails");
        System.out.println(ALU.doOp(operationCode[0], new longword(-1), new longword()).getSigned() == 0 ? "-1 AND 0 passes." : "-1 AND 0 fails");
        System.out.println(ALU.doOp(operationCode[0], new longword(), new longword(-1)).getSigned() == 0 ? "0 AND -1 passes." : "0 AND -1 fails");
        System.out.println(ALU.doOp(operationCode[0], new longword(), new longword()).getSigned() == 0 ? "0 AND 0 passes." : "0 AND 0 fails");
        System.out.println();
        
        //test or
        System.out.println("ALU OR tests-");
        //-1 has all bits set and 0 is all bits unset so results match the OR truth table
        System.out.println(ALU.doOp(operationCode[1], new longword(-1), new longword(-1)).getSigned() == -1 ? "-1 OR -1 passes." : "-1 OR -1 fails");
        System.out.println(ALU.doOp(operationCode[1], new longword(-1), new longword()).getSigned() == -1 ? "-1 OR 0 passes." : "-1 OR 0 fails");
        System.out.println(ALU.doOp(operationCode[1], new longword(), new longword(-1)).getSigned() == -1 ? "0 OR -1 passes." : "0 OR -1 fails");
        System.out.println(ALU.doOp(operationCode[1], new longword(), new longword()).getSigned() == 0 ? "0 OR 0 passes." : "0 OR 0 fails");
        System.out.println();

        //test xor
        System.out.println("ALU XOR tests-");
        //-1 has all bits set and 0 is all bits unset so results match the XOR truth table
        System.out.println(ALU.doOp(operationCode[2], new longword(-1), new longword(-1)).getSigned() == 0 ? "-1 XOR -1 passes." : "-1 XOR -1 fails");
        System.out.println(ALU.doOp(operationCode[2], new longword(-1), new longword()).getSigned() == -1 ? "-1 XOR 0 passes." : "-1 XOR 0 fails");
        System.out.println(ALU.doOp(operationCode[2], new longword(), new longword(-1)).getSigned() == -1 ? "0 XOR -1 passes." : "0 XOR -1 fails");
        System.out.println(ALU.doOp(operationCode[2], new longword(), new longword()).getSigned() == 0 ? "0 XOR 0 passes." : "0 XOR 0 fails");
        System.out.println();

        //test not
        System.out.println("ALU NOT tests-");
        //NOT on all unset bits will return a longword with all bits set which is -1. NOT on all set bits returns all unset bits which is zero. Plugged in for b to show that it is ignored.
        System.out.println(ALU.doOp(operationCode[3], new longword(), new longword(123123)).getSigned() == -1 ? "0 NOT passes." : "0 NOT fails");
        System.out.println(ALU.doOp(operationCode[3], new longword(-1), new longword()).getSigned() == 0 ? "-1 NOT passes." : "-1 NOT fails");
        System.out.println();

        //test left shift
        //Demonstrating left shift works for up to amount of 32 by shifting a 1
        System.out.println("ALU left shift tests-");
        for(int i = 0, j = 1; i < 32; i++, j*=2)
        {
            System.out.println(ALU.doOp(operationCode[4], new longword(1), new longword(i)).getSigned() == j ? "1 left shift " + i + " passes." : "1 left shift " + i + " fails.");
        }
        //Demonstrating only the lowest 5 bits are considered so 32 would show 00000, 33 would show 00001, and so on
        for(int i = 32, j = 1; i < 35; i++, j*=2)
        {
            System.out.println(ALU.doOp(operationCode[4], new longword(1), new longword(i)).getSigned() == j ? "1 left shift " + i + " passes." : "1 left shift " + i + " fails.");
        }
        System.out.println();

        //test right shift
        System.out.println("ALU right shift tests-");
        //Demonstrating right shift works for up to amount of 32 by shifting the max unsigned integer value which is all bits set
        for(long i = 0, j = (new longword(-1)).getUnsigned(); i < 32; i++, j/=2)
        {
            System.out.println(ALU.doOp(operationCode[5], new longword(-1), new longword((int) i)).getUnsigned() == j ? "2147483648 right shift " + i + " passes." : "2147483648 right shift " + i + " fails.");
        }
        //Demonstrating only the lowest 5 bits are considered so 32 would show 00000, 33 would show 00001, and so on    
        for(long i = 32, j = (new longword(-1)).getUnsigned(); i < 35; i++, j/=2)
        {
            System.out.println(ALU.doOp(operationCode[5], new longword(-1), new longword((int) i)).getUnsigned() == j ? "2147483648 right shift " + i + " passes." : "2147483648 right shift " + i + " fails.");
        }
        System.out.println();

        //test add
        System.out.println("ALU add tests-");
        //Demonstrates addition works between wide range of positive and negative values
        for(int i = 0, j = 15; i < 32; i++, j*= -2)
        {
            System.out.println(ALU.doOp(operationCode[6], new longword(i), new longword(j)).getSigned() == i+j ? i + " + " + j + " passes." : i + " + " + j + " fails.");
        }

        System.out.println();

        //test subtract
        System.out.println("ALU subtract tests-");
        //Demonstrates subtraction works between wide range of positive and negative values
        for(int i = 0, j = 26; i < 32; i++, j*= -2)
        {
            System.out.println(ALU.doOp(operationCode[7], new longword(i), new longword(j)).getSigned() == i-j ? i + " - " + j + " passes." : i + " - " + j + " fails.");
        }

        System.out.println();

        //test multiply
        System.out.println("ALU multiply tests-");
        //Demonstrates multiplication works between wide range of positive and negative values
        for(int i = 0, j = 3; i < 32; i++, j*= -2)
        {
            System.out.println(ALU.doOp(operationCode[8], new longword(i), new longword(j)).getSigned() == i*j ? i + " * " + j + " passes." : i + " * " + j + " fails.");
        }
        System.out.println();

        //test exception handling
        //demonstrates non-existing operation codes are handled
        //bit[] nonOperation1 = {new bit(), new bit(), new bit(), new bit()};
        //System.out.println((new ALU()).doOp(nonOperation1, new longword(), new longword()));
        //System.out.println();

        //demonstrates invalid operation codes are handled
        //bit[] nonOperation2 = {new bit(), new bit(), new bit(), new bit(), new bit()};
        //System.out.println((new ALU()).doOp(nonOperation2, new longword(), new longword()));
        //System.out.println();

        System.out.println();
    }
}
