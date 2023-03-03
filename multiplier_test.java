/*
Author: My Tran
Filename: multiplier_test.java
Description: Program performs unit testing on the methods of multiplier class
*/
public class multiplier_test
{
    public static void main(String args[]) throws Exception
    {
        bit_test.runTests();
        longword_test.runTests();
        rippleAdder_test.runTests();;
        runTests();
    }

    public static void runTests() throws Exception
    {
        System.out.println("Starting multiplier_test...\n");

        multiplyTest();

        System.out.println("Ending test...");
    }

    public static void multiplyTest() throws Exception
    {
        System.out.println("Testing multiply method:");
        //Demonstrates multiplication works between wide range of positive and negative values
        for(int i = 0, j = 3; i < 32; i++, j*= -2)
            System.out.println(multiplier.multiply(new longword(i), new longword(j)).getSigned() == i*j ? i + " * " + j + " passes." : i + " * " + j + " fails.");
        
        System.out.println();
    }
}
