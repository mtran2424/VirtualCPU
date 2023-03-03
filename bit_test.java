/*
Author: My Tran
Filename: bit_test.java
Description: Program performs unit testing on the methods of bit class
*/
public class bit_test 
{
    public static void main(String args[])
    {
        runTests();
    }

    public static void runTests()//calls the independent test methods to fully test bit class
    {
        System.out.println("Starting bit_test...\n");

        testGetValue();

        testSet();

        testToggle();

        testClear();
        
        testAnd();

        testOr();

        testXor();

        testNot();

        testToString();

        System.out.println("Ending test...\n");
    }

    public static void testGetValue()//unit testing for getValue method
    {
        System.out.println("Test getValue method:");

        //test 1: get false value from object
        //default constructor has default false so getValue should return false to pass test
        System.out.println(!(new bit().getValue()) ? "Getting false value passes" : "Getting false value fails");

        //test 2: get true value from object
        //new instance should store true value so getValue should return true to pass test
        System.out.println((new bit(true).getValue()) ? "Getting true value passes" : "Getting true value fails");

        System.out.println();
    }

    public static void testSet()//unit testing for default and non-default set method
    {
        System.out.println("Test set method:");

        bit testBit1 = new bit(), testBit2 = new bit(true), 
            testBit3 = new bit(), testBit4 = new bit(true), 
            testBit5 = new bit(), testBit6 = new bit(true);

        //set() test:
        //test 1: calling default set on an unset bit
        testBit1.set();
        //testBit1 was initially false and then set was called so bit should return true to pass
        System.out.println((testBit1.getValue()) ? "Setting 0 to 1 passes" : "Setting 0 to 1 fails");

        //test 2: calling default set on set bit
        testBit2.set();
        //testBit2 was initially true and then set was called so bit should return true to pass
        System.out.println((testBit2.getValue()) ? "Setting 1 to 1 passes" : "Setting 1 to 1 fails");

        //set(...) test:
        //test 1: setting unset bit
        testBit3.set(true);
        //testBit3 was initially false and then set was called (given true) so bit should return true to pass
        System.out.println((testBit3.getValue()) ? "Setting 0 to 1 passes" : "Setting 0 to 1 fails");

        //test 2: unsetting set bit
        testBit4.set(false);
        //testBit4 was initially true and then set was called (given false) so bit should return false to pass
        System.out.println(!(testBit4.getValue()) ? "Setting 1 to 0 passes" : "Setting 1 to 0 fails");

        //test 3: unsetting unset bit
        testBit5.set(false);
        //testBit5 was initially false and then set (given false) was called so bit should return false to pass
        System.out.println(!(testBit5.getValue()) ? "Setting 0 to 0 passes" : "Setting 0 to 0 fails");

        //test 4: setting set bit
        testBit6.set(true);
        //testBit4 was initially true and then set was called (given true) so bit should return true to pass
        System.out.println((testBit6.getValue()) ? "Setting 1 to 1 passes" : "Setting 1 to 1 fails");

        System.out.println();
    }

    public static void testToggle()//unit testing for toggle method
    {
        System.out.println("Test toggle method:");
        
        bit testBit1 = new bit(), testBit2 = new bit(true);

        //test 1: toggle on unset bit
        testBit1.toggle();
        //testBit1 was false by default so it should store true after toggle to pass
        System.out.println((testBit1.getValue()) ? "Toggling from 0 passes" : "Toggling from 0 fails");

        //test 2: toggle on set bit
        testBit2.toggle();
        //testBit2 was instantiated given true so it should store false after toggle to pass
        System.out.println(!(testBit2.getValue()) ? "Toggling from 0 passes" : "Toggling from 0 fails");

        System.out.println();
    }

    public static void testClear()//unit testing for clear method
    {
        System.out.println("Test set method:");

        bit testBit1 = new bit(), testBit2 = new bit(true);

        //test 1: clear on unset bit 
        testBit1.clear();
        //testBit1 was false by default so it should still return false after clear to pass
        System.out.println(!(testBit1.getValue()) ? "Clearing 0 to 0 passes" : "Clearing 0 to 0 fails");

        //test 2: clear on set bit
        testBit2.clear();
        //testBit2 was instantiated given true so it should store false after clear to pass
        System.out.println(!(testBit2.getValue()) ? "Clearing 1 to 0 passes" : "Clearing 1 to 0 fails");

        System.out.println();
    }

    public static void testAnd()//unit testing for and method
    {
        System.out.println("Test AND method:");

        //test 1: 0 AND 0 case
        //false AND false should return false to pass
        System.out.println(!(new bit().and(new bit()).getValue()) ? "0 AND 0 passes" : "0 AND 0 fails");

        //test 2: 1 AND 0 case
        //true AND false should return false to pass
        System.out.println(!(new bit(true).and(new bit()).getValue()) ? "1 AND 0 passes" : "1 AND 0 fails");

        //test 3: 0 AND 1 case
        //false AND true should return false to pass
        System.out.println(!(new bit().and(new bit(true)).getValue()) ? "0 AND 1 passes" : "0 AND 1 fails");

        //test 3: 1 AND 1 case
        //true AND true should return true to pass
        System.out.println((new bit(true).and(new bit(true)).getValue()) ? "1 AND 1 passes" : "1 AND 1 fails");

        System.out.println();
    }

    public static void testOr()//unit testing for or method
    {
        System.out.println("Test OR method:");

        //test 1: 0 OR 0 case
        //false OR false should return false to pass
        System.out.println(!(new bit().or(new bit()).getValue()) ? "0 OR 0 passes" : "0 OR 0 fails");

        //test 2: 1 OR 0 case
        //true OR false should return true to pass
        System.out.println((new bit(true).or(new bit()).getValue()) ? "1 OR 0 passes" : "1 OR 0 fails");

        //test 3: 0 OR 1 case
        //false OR true should return true to pass
        System.out.println((new bit().or(new bit(true)).getValue()) ? "0 OR 1 passes" : "0 OR 1 fails");

        //test 4: 1 OR 1 case
        //true OR true should return true to pass
        System.out.println((new bit(true).or(new bit(true)).getValue()) ? "1 OR 1 passes" : "1 OR 1 fails");

        System.out.println();
    }


    public static void testXor()
    {
        System.out.println("Test XOR method:");

        //test 1: 0 XOR 0 case
        //false OR false should return false to pass
        System.out.println(!(new bit().xor(new bit()).getValue()) ? "0 XOR 0 passes" : "0 XOR 0 fails");

        //test 2: 1 XOR 0 case
        //true OR false should return true to pass
        System.out.println((new bit(true).xor(new bit()).getValue()) ? "1 XOR 0 passes" : "1 XOR 0 fails");

        //test 3: 0 XOR 1 case
        //false OR true should return true to pass
        System.out.println((new bit().xor(new bit(true)).getValue()) ? "0 XOR 1 passes" : "0 XOR 1 fails");

        //test 4: 1 XOR 1 case
        //true OR true should return false to pass
        System.out.println(!(new bit(true).xor(new bit(true)).getValue()) ? "1 XOR 1 passes" : "1 XOR 1 fails");

        System.out.println();
    }

    public static void testNot()//unit testing for not method
    {
        System.out.println("Test NOT method:");
        
        //test 1: NOT on unset bit object
        //NOT on unset object should return set object so true should be returned to pass
        System.out.println((new bit().not().getValue()) ? "NOT on 0 passes" : "NOT on 0 fails");

        //test 2: NOT on set bit object
        //NOT on set object should return unset object so false should be returned to pass
        System.out.println(!(new bit(true).not().getValue()) ? "NOT on 1 passes" : "NOT on 1 fails");

        System.out.println();
    }

    public static void testToString()
    {
        System.out.println("Test overridden toString method:");

        bit testBit1 = new bit(), testBit2 = new bit(true);

        //test 1: explicit and implicit toString on unset bit object
        //testBit1 is default false so "f" should be returned to pass
        System.out.println((testBit1.toString() == "f") ? "toString on unset bit passes - " + testBit1 + " returned" : 
                                                            "toString on unset bit fails - " + testBit1 + "returned");

        //test 2: explicit and implicit toString on set bit object
        //testBit2 instance is given true so "t" should be returned to pass
        System.out.println((testBit2.toString() == "t") ? "toString on set bit passes - " + testBit2 + " returned" : 
                                                            "toString on set bit fails - " + testBit2+ " returned");

        System.out.println();
    }
}
