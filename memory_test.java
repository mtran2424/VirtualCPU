/*
Author: My Tran
Filename: memory_test.java
Description: Program performs unit testing on the methods of memory class
*/
public class memory_test 
{
    public static void main(String args[]) throws Exception
    {
        bit_test.runTests();
        longword_test.runTests();
        rippleAdder_test.runTests();;
        multiplier_test.runTests();
        ALU_test.runTests();
        runTests();
    }

    public static void runTests() throws Exception
    {
        System.out.println("Starting memory_test...");

        writeTest();

        readTest();

        System.out.println("Ending test...");
    }

    public static void writeTest() throws Exception
    {
        System.out.println("Test write method:");

        memory testStorage = new memory();

        for(int i = 0, j = 1; i < 32; i++, j += 31)
        {
            testStorage.write(new longword(j), new longword(i));//write to i in 4 Bytes to Bytes addressed at j

            //Bytes at j should be set to i to pass
            System.out.println((testStorage.read(new longword(j)).getSigned() == i) ? "Write to Byte " + j + " passes." : "Write to Byte " + j + " fails.");
        }

        //demonstrating exception
        //testStorage.write(new longword(1022), new longword(-1));

        System.out.println();
    }

    public static void readTest() throws Exception
    {
        System.out.println("Test read method:");

        memory testStorage = new memory();

        for(int i = 0, j = 1; i < 32; i++, j += 23)
        {
            testStorage.write(new longword(j), new longword(i));//write to i in 4 Bytes to Bytes addressed at j

            //Bytes at j should be set to i to pass
            System.out.println((testStorage.read(new longword(j)).getSigned() == i) ? "Read Byte " + j + " passes." : "Read Byte " + j + " fails.");
        }

        //demonstrating exception
        //testStorage.read(new longword(1022));

        System.out.println();
    }

}
