/*
Author: My Tran
Filename: rippleAdder_test.java
Description: Program performs unit testing on the methods of rippleAdder class
*/
public class rippleAdder_test 
{
    public static void main(String args[]) throws Exception
    {
        bit_test.runTests();
        longword_test.runTests();
        runTests(); 
    }  
    
    public static void runTests()
    {
        System.out.println("Starting rippleAdder_test...\n");

        addTest();

        subtractTest();

        System.out.println("Ending test...");
    }

    public static void addTest()
    {
        System.out.println("Testing add method:");

        //2000 + 48 should = 2048 to pass
        System.out.println(rippleAdder.add(new longword(2000), new longword(48)).getSigned() == 2048 ? "Adding two positive numbers passes." : "Adding two positive numbers fails.");

        //-2000 + -48 should = -2048 to pass
        System.out.println(rippleAdder.add(new longword(-2000), new longword(-48)).getSigned() == -2048 ? "Adding two negative numbers passes." : "Adding two negative numbers fails.");
        
        //2000 + -48 should = 2000 to pass
        System.out.println(rippleAdder.add(new longword(2048), new longword(-48)).getSigned() == 2000 ? "Adding a positive and negative number passes." : "Adding a postive and negative fails.");

        //-1 is a longword where all the bits are set which is equivalent to unsigned int max so adding one should overflow causing 0 representation
        System.out.println(rippleAdder.add(new longword(-1), new longword(1)).getSigned() == 0 ? "Adding 1 to max unsigned int passes." : "Adding 1 to max unsigned int fails.");

        //-2147483648 is 1000...0000 and 2147483647 is 0111...1111 so adding them together is 1111....1111 which is a signed -1
        System.out.println(rippleAdder.add(new longword(-2147483648), new longword(2147483647)).getSigned() == -1 ? "Adding int max to negative int max passes." : "Adding int max to negative int max fails.");

        System.out.println();
    }

    public static void negateTest()
    {
        System.out.println("Testing negate method:");

        System.out.println();
    }

    public static void subtractTest()
    {
        System.out.println("Testing subtract method:");
        
        //2048 - 48 should = 2000 to pass
        System.out.println(rippleAdder.subtract(new longword(2048), new longword(48)).getSigned() == 2000 ? "Subtracting positive from passes." : "Subtracting positive from positive fails.");

        //-2000 + -48 should = -2048 to pass
        System.out.println(rippleAdder.subtract(new longword(-2048), new longword(-48)).getSigned() == -2000 ? "Subtracting negative from negative passes." : "Subtracting negative from negative fails.");
        
        //2048 + -48 should = 2000 to pass
        System.out.println(rippleAdder.subtract(new longword(2000), new longword(-48)).getSigned() == 2048 ? "Subtracting negative from positive passes." : "Subtracting negatice from fails.");

        //-1 is a longword where all bits are 
        System.out.println(rippleAdder.subtract(new longword(-1), new longword(1)).getSigned() ==  -2? "Subtracting 1 from -1 passes." : "Subtracting 1 from -1 fails.");

        //int max - int max should = 0 to pass
        System.out.println(rippleAdder.subtract(new longword(2147483647), new longword(2147483647)).getSigned() == 0 ? "Subtracting int max from itself passes." : "Subtracting int max from itself fails.");

        System.out.println();
    }
}
