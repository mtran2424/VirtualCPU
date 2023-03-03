/*
Author: My Tran
Filename: longword_test.java
Description: Program performs unit testing on the methods of longword class
*/
public class longword_test 
{
    public static void main(String args[]) throws Exception
    {
        bit_test.runTests();
        runTests();
    }

    public static void runTests() throws Exception
    {
        System.out.println("Starting longword_test...\n");

        testGetBit();

        testSetBit();

        testSet();

        testGetUnsigned();

        testGetSigned();

        testToString();

        testAnd();

        testOr();

        testXor();

        testNot();
            
        testLeftShift();

        testRightShift();

        testCopy();

        System.out.println("Ending test...\n");
    }

    public static void testSetBit()
    {
        System.out.println("Testing setBit method:");

        longword testValue1 = new longword(), testValue2 = new longword();
        //testValue3 = new longword();
        
        //bit class has been well tested so a set and unset bit are created to prevent need to create
        //new bits for every test
        bit testBit1 = new bit(true), testBit2 = new bit();

        //test 1:
        //setBit on the first bit should with a set bit should result in a set first bit
        testValue1.setBit(0, testBit1);
        System.out.println((testValue1.getBit(0).getValue()) ? "setBit on first bit to true passes":
                                                                "setBit on first bit to true fails");

        //test 2:
        //setBit on the first bit should with an unset bit should result in an unset first bit
        testValue1.setBit(0, testBit2);
        System.out.println(!(testValue1.getBit(0).getValue()) ? "setBit on first bit to false passes":
                                                                "setBit on first bit to false fails");

        //test 3:
        //setBit on the last bit should with a set bit should result in a set last bit
        testValue2.setBit(31, testBit1);
        System.out.println((testValue2.getBit(31).getValue()) ? "setBit on last bit to true passes":
                                                                "setBit on last bit to true fails");

        //test 4:
        //setBit on the last bit should with an unset bit should result in an unset last bit
        testValue2.setBit(31, testBit2);
        System.out.println(!(testValue2.getBit(31).getValue()) ? "setBit on last bit to false passes":
                                                                "setBit on last bit to false fails");

        //calling setBit with out of bound indices shows that the exception handling works
        //System.out.println("Show exception handling works.");
        //testValue3.setBit(32, testBit1);
        //testValue3.setBit(-11, testBit2);

        System.out.println();
    }
    
    
    public static void testGetBit()
    {
        System.out.println("Testing getBit method:");

        boolean condition0, condition1, condition2;
        
        //initiallized to 001
        bit testArray0[] = {new bit(), new bit(), new bit(true)};
        longword testValue0 = new longword(testArray0);

        //test 1:
        //test validates getBit method by getting 3 known bits at three known indices
        condition0 = !(testValue0.getBit(0).getValue()) ? true : false;//1st bit is supposed to be false
        condition1 = !(testValue0.getBit(1).getValue()) ? true : false;//2nd bit is supposed to be false
        condition2 = (testValue0.getBit(2).getValue()) ? true : false;//3rd bit is supposed to be true
        //all three conditions need to be true to pass test
        System.out.println((condition0) ? ((condition1) ? ((condition2) ? "001 test passes." : "001 test fails.") : "001 test fails.") : "001 test fails.");

        //initiallized to 010
        bit testArray1[] = {new bit(), new bit(true), new bit()};
        longword testValue1 = new longword(testArray1);

        //test 1:
        //test validates getBit method by getting 3 known bits at three known indices
        condition0 = !(testValue1.getBit(0).getValue()) ? true : false;//1st bit is supposed to be false
        condition1 = (testValue1.getBit(1).getValue()) ? true : false;//2nd bit is supposed to be true
        condition2 = !(testValue1.getBit(2).getValue()) ? true : false;//3rd bit is supposed to be false
        //all three conditions need to be true to pass test
        System.out.println((condition0) ? ((condition1) ? ((condition2) ? "010 test passes." : "010 test fails.") : "010 test fails.") : "010 test fails.");

        //calling getBit with out of bound indices shows that the exception handling works
        //System.out.println("Show exception handling works.");
        //testValue1.getBit(32);
        //testValue1.getBit(-11);

        System.out.println();
    }

    public static void testSet()
    {
        System.out.println("Testing set method:");

        //test 1:
        //Setting the longword value to 19 should set the least significant byte to 0001 0011
        longword testValue1 = new longword();
        testValue1.set(19);

        //array representing byte is populated with expected 0001 0011 to compare with test value
        bit expectedByte1[] = {new bit(), new bit(), new bit(), new bit(true),
                                new bit(), new bit(), new bit(true), new bit(true)};
        bit testByte1[] = new bit[8];

        //Byte taken from bit 24 to 31 taken to be compared
        for(int i = 24, j = 0; i < 32; i++, j++)
        {
            testByte1[j] = testValue1.getBit(i);
        }

        boolean conditionFlag1 = true;

        for(int i = 0; i < 8; i++)//if the bits in the expected don't match those of the test, the test fails
        {
            if(testByte1[i].getValue() != expectedByte1[i].getValue())
            {
                conditionFlag1 = false;
                break;
            }
        }
        System.out.println(conditionFlag1 ? "Byte from 19 passes." : "Byte from 19 fails.");

        //test 2:
        //Setting the longword value to 4608 third byte from most significant to 0001 0010
        longword testValue2 = new longword();
        testValue2.set(4608);

        //expected byte array populated with 0001 0010
        bit expectedByte2[] = {new bit(), new bit(), new bit(), new bit(true),
                                new bit(), new bit(), new bit(true), new bit()};
        bit testByte2[] = new bit[8];

        //Byte taken from bit 16 to 24 taken to be compared
        for(int i = 16, j = 0; i < 24; i++, j++)
        {
            testByte2[j] = testValue2.getBit(i);
        }

        boolean conditionFlag2 = true;
        for(int i = 0; i < 8; i++)//if the bits in the expected don't match those of the test, the test fails
        {
            if(testByte2[i].getValue() != expectedByte2[i].getValue())
            {
                conditionFlag2 = false;
                break;
            }
        }

        System.out.println(conditionFlag2 ? "Byte from 4608 passes." : "Byte from 4608 fails.");

        //test 3:
        //Setting the longword value to 4608 third byte from most significant to 0001 0010
        longword testValue3 = new longword();
        testValue3.set(-1);

        //expected byte array populated with 0001 0010
        bit expectedByte3[] = {new bit(true), new bit(true), new bit(true), new bit(true),
                                new bit(true), new bit(true), new bit(true), new bit(true)};
        bit testByte3[] = new bit[8];

        for(int i = 0; i < 8; i++)
        {
            testByte3[i] = testValue3.getBit(i);
        }

        boolean conditionFlag3 = true;
        for(int i = 0; i < 8; i++)//if the bits in the expected don't match those of the test, the test fails
        {
            if(testByte3[i].getValue() != expectedByte3[i].getValue())
            {
                conditionFlag3 = false;
                break;
            }
        }

        System.out.println(conditionFlag3 ? "Byte from -1 passes." : "Byte from -1 fails.");

        System.out.println();
    }

    public static void testGetUnsigned()
    {
        System.out.println("Test getUnsigned method:");

        //test 1:
        //Longword is set to 8 so getUnsigned should return 8 to pass test
        longword testValue1 = new longword();
        testValue1.set(8);

        System.out.println((testValue1.getUnsigned() == 8) ? "getUnsigned on 8 passes." : "getUnsigned on 8 fails.");

        //test 2:
        ////Longword is set to 2147483647 so getUnsigned should return 2147483647 to pass test
        longword testValue2 = new longword();
        testValue2.set(2147483647);

        System.out.println((testValue2.getUnsigned() == 2147483647) ? "getUnsigned on 2147483647 passes." : "getUnsigned on 2147483647 fails.");

        //test 3:
        ////Longword is set to 64206 so getUnsigned should return 64206 to pass test
        longword testValue3 = new longword();
        testValue3.set(64206);

        System.out.println((testValue3.getUnsigned() == 64206) ? "getUnsigned on 64206 passes." : "getUnsigned on 64206 fails.");
        
        //test 4:
        ////Longword is set to 11328528 so getUnsigned should return 11328528 to pass test
        longword testValue4 = new longword();
        testValue4.set(11328528);

        System.out.println((testValue4.getUnsigned() == 11328528) ? "getUnsigned on 11328528 passes." : "getUnsigned on 11328528 fails.");
        
        //test 5:
        ////Longword is set to 0 so getUnsigned should return 0 to pass test
        longword testValue5 = new longword();
        testValue5.set(0);

        System.out.println((testValue5.getUnsigned() == 0) ? "getUnsigned on 0 passes." : "getUnsigned on 0 fails.");

        System.out.println();
    }

    public static void testGetSigned()
    {
        System.out.println("Test getSigned method:");

        //test 1:
        //testing long variable is set to -1 so getSigned should return -1 to pass
        longword testValue1 = new longword();
        testValue1.set(-1);
        System.out.println((testValue1.getSigned() == -1) ? "getSigned on -1 passes." : "getSigned on -1 fails.");

        //test 2:
        //testing long variable is set to max int value so getSigned should return max signed int value to pass
        longword testValue2 = new longword();
        testValue2.set(2147483647);
        System.out.println((testValue2.getSigned() == 2147483647) ? "getSigned on max int value passes." : "getSigned on max int value fails.");

        //test 3:
        //testing long variable is set to random negative so getSigned should return same value to pass
        longword testValue3 = new longword();
        testValue3.set(-64206);
        System.out.println((testValue3.getSigned() == -64206) ? "getSigned on -64206 passes." : "getSigned on -64206 fails.");
        
        //test 4:
        //testing long variable is set to random positive so getSigned should return same value to pass
        longword testValue4 = new longword();
        testValue4.set(11328528);
        System.out.println((testValue4.getSigned() == 11328528) ? "getSigned on 11328528 passes." : "getSigned on 11328528 fails.");

        //test 5:
        //testing long variable is set to 0 so getSigned should return 0 to pass
        longword testValue5 = new longword();
        testValue5.set(0);
        System.out.println((testValue5.getSigned() == 0) ? "getSigned on 0 passes" : "getSigned on 0 fails");
 
        System.out.println();
    }
    
    public static void testToString()
    {
        System.out.println("Test toString method:");

        //test 1:
        //all f and comma separated is the expected output for default longword so toString should return matching output
        String expectedText1 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f";
        longword testValue1 = new longword();
        System.out.println(testValue1.toString().equals(expectedText1) ? "Outputting 0 passes." : "Outputting 0 fails.");
        System.out.println(testValue1);

        //test 2:
        //same output as above, but the last bit is set to true is expected so toString should return matching output
        String expectedText2 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t";
        longword testValue2 = new longword();
        testValue2.set(1);
        System.out.println(testValue2.toString().equals(expectedText2) ? "Outputting 1 passes." : "Outputting 1 fails.");
        System.out.println(testValue2);

        //test 3:
        //all false, but the 1's bit and 1024's bit is set to true is expected so toString should return matching output
        String expectedText3 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f,f,f,f,f,f,f,t";
        longword testValue3 = new longword();
        testValue3.set(1025);
        System.out.println(testValue3.toString().equals(expectedText3) ? "Outputting 1025 passes." : "Outputting 1025 fails.");
        System.out.println(testValue3);

        //test 4:
        //all true, but most significant is unset is expected for max int value so toString should return matching output
        String expectedText4 = "f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t";
        longword testValue4 = new longword();
        testValue4.set(2147483647);
        System.out.println(testValue4.toString().equals(expectedText4) ? "Outputting 2147483647 passes." : "Outputting 2147483647 fails.");
        System.out.println(testValue4);

        //test 4:
        //all true is expected so toString should return matching output
        String expectedText5 = "t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t";
        longword testValue5 = new longword();
        testValue5.set(-1);
        System.out.println(testValue5.toString().equals(expectedText5) ? "Outputting -1 passes." : "Outputting -1 fails.");
        System.out.println(testValue5);

        System.out.println();
    }

    public static void testAnd()
    {
        System.out.println("Test AND method:");

        //test 1:
        //setting both values to -1 sets all bits to true to AND between 1 and 1 bits
        longword testValue1 = new longword(), testValue2 = new longword();
        testValue1.set(-1);
        testValue2.set(-1);
        //all bits in returned long word should be set so it should have a -1 for test to pass
        System.out.println(testValue1.and(testValue2).getSigned() == -1 ? "AND with -1 and -1 passes.":"AND with -1 and -1 fails.");

        //test 2:
        //setting the other value to 0 sets all of the other's bits to false to AND between 0 and 1 bits
        testValue2.set(0);
        //all bits in returned long word should be unset so it should have a 0 for test to pass
        System.out.println(testValue1.and(testValue2).getSigned() == 0 ? "AND with -1 and 0 passes.":"AND with -1 and 0 fails.");

        //test 3:
        //setting the first value to 0 sets all of the first's bits to false to AND between 1 and 0 bits
        testValue1.set(0);
        testValue2.set(-1);
         //all bits in returned long word should be unset so it should have a 0 for test to pass
         System.out.println(testValue1.and(testValue2).getSigned() == 0 ? "AND with 0 and -1 passes.":"AND with 0 and -1 fails.");

        //test 4:
        //setting both 0 to and between all 0 and 0 bits
        testValue2.set(0);
         //all bits in returned long word should be unset so it should have a 0 for test to pass
         System.out.println(testValue1.and(testValue2).getSigned() == 0 ? "AND with 0 and 0 passes.":"AND with 0 and 0 fails.");

        System.out.println();
    }

    public static void testOr()
    {
        System.out.println("Test OR method:");

        //test 1:
        //setting both values to -1 sets all bits to true to OR between 1 and 1 bits
        longword testValue1 = new longword(), testValue2 = new longword();
        
        testValue1.set(-1);
        testValue2.set(-1);
        //all bits in returned long word should be set so it should have a -1 for test to pass
        System.out.println(testValue1.or(testValue2).getSigned() == -1 ? "OR with -1 and -1 passes.":"OR with -1 and -1 fails.");

        //test 2:
        //setting the other value to 0 sets all of the other's bits to false to OR between 0 and 1 bits
        testValue2.set(0);
        //all bits in returned long word should be set so it should have a -1 for test to pass
        System.out.println(testValue1.or(testValue2).getSigned() == -1 ? "OR with -1 and 0 passes.":"OR with -1 and 0 fails.");

        //test 3:
        //setting the first value to 0 sets all of the first's bits to false to OR between 1 and 0 bits
        testValue1.set(0);
        testValue2.set(-1);
        //all bits in returned long word should be set so it should have a -1 for test to pass
        System.out.println(testValue1.or(testValue2).getSigned() == -1 ? "OR with 0 and -1 passes.":"OR with 0 and -1 fails.");

        //test 4:
        //setting both 0 to OR between all 0 and 0 bits
        testValue2.set(0);
        //all bits in returned long word should be unset so it should have a 0 for test to pass
        System.out.println(testValue1.or(testValue2).getSigned() == 0 ? "OR with 0 and 0 passes.":"OR with 0 and 0 fails.");

        System.out.println();
    }

    public static void testXor()
    {
        System.out.println("Test XOR method:");

        //test 1:
        //setting both values to -1 sets all bits to true to OR between 1 and 1 bits
        longword testValue1 = new longword(), testValue2 = new longword();
        testValue1.set(-1);
        testValue2.set(-1);
        //all bits in returned long word should be unset so it should have a 0 for test to pass
        System.out.println(testValue1.xor(testValue2).getSigned() == 0 ? "XOR with -1 and -1 passes.":"XOR with -1 and -1 fails.");

        //test 2:
        //setting the other value to 0 sets all of the other's bits to false to OR between 0 and 1 bits
        testValue2.set(0);
        //all bits in returned long word should be set so it should have a -1 for test to pass
        System.out.println(testValue1.xor(testValue2).getSigned() == -1 ? "XOR with -1 and 0 passes.":"XOR with -1 and 0 fails.");

        //test 3:
        //setting the first value to 0 sets all of the first's bits to false to OR between 1 and 0 bits
        testValue1.set(0);
        testValue2.set(-1);
        //all bits in returned long word should be set so it should have a -1 for test to pass
        System.out.println(testValue1.xor(testValue2).getSigned() == -1 ? "XOR with 0 and -1 passes.":"XOR with 0 and -1 fails.");

        //test 4:
        //setting both 0 to OR between all 0 and 0 bits
        testValue2.set(0);
        //all bits in returned long word should be unset so it should have a 0 for test to pass
        System.out.println(testValue1.xor(testValue2).getSigned() == 0 ? "XOR with 0 and 0 passes.":"XOR with 0 and 0 fails.");

        System.out.println();
    }

    public static void testNot()
    {
        System.out.println("Test NOT method:");

        //test 1:
        //default longword should be zero so NOT should return a -1 (all bits set) to pass test
        longword testValue1 = new longword();
        System.out.println(testValue1.not().getSigned() == -1 ? "NOT on 0 passes." : "NOT on 0 fails.");

        //test 2:
        //setting value to -1 sets all bits so NOT should return 0 (all bits unset) to pass test
        longword testValue2 = new longword();
        testValue2.set(-1);
        System.out.println(testValue2.not().getSigned() == 0 ? "NOT on -1 passes." : "NOT on -1 fails.");

        System.out.println();
    }

    public static void testLeftShift() throws Exception
    {
        System.out.println("Test leftShift method:");

        //test 1:
        longword testValue1 = new longword();
        testValue1.set(-2048);
        int expectedValue1 = -2048*2;
        //shifting a longword left should perform integer multiplication by a factor of 2 per shift so -4096 should be returned
        System.out.println(testValue1.leftShift(1).getSigned() == expectedValue1 ? "Right shift on negative passes." : "Right shift on negative fails.");

        //test 2:
        longword testValue2 = new longword();
        testValue2.set(2048);
        int expectedValue2 = 2048*8;
        //shifting left 3 should multiply the value by 2^3
        System.out.println(testValue2.leftShift(3).getSigned() == expectedValue2 ? "Right shift 3 bits passes." : "Right shift 3 bits fails.");

        //test 3:
        longword testValue3 = new longword();
        testValue3.set(-1);
        int expectedValue3 = 0;
        //shifting all the bits should result in a zero result
        System.out.println(testValue3.leftShift(32).getSigned() == expectedValue3 ? "Right shift all bits passes." : "Right shift all bits fails.");

        //calling leftShift with out of bound amounts shows that the exception handling works
        //System.out.println("Show exception handling works - ");
        //System.out.println(testValue1.leftShift(-11).getSigned() == expectedValue3 ? "Right shift all bits passes." : "Right shift all bits fails.");
        //System.out.println(testValue1.leftShift(33).getSigned() == expectedValue3 ? "Right shift all bits passes." : "Right shift all bits fails.");
        
        System.out.println();
    }

    public static void testRightShift() throws Exception
    {
        System.out.println("Test rightShift method:");

        //test 1:
        //shifting a longword with all bits set to the right will result in the max signed int value so if rightShift returns that value, it passes
        longword testValue1 = new longword();
        testValue1.set(-1);
        int expectedValue1 = 2147483647;
        System.out.println(testValue1.rightShift(1).getSigned() == expectedValue1 ? "Right shift on negative passes." : "Right shift on negative fails.");

        //test 2:
        //shifting a longword representing 2048 by 3 should divide the value by 2^3 if rightShift returns that value, it passes
        longword testValue2 = new longword();
        testValue2.set(2048);
        int expectedValue2 = 2048/8;
        System.out.println(testValue2.rightShift(3).getSigned() == expectedValue2 ? "Right shift 3 bits passes." : "Right shift 3 bits fails.");

        //test 3:
        //shifting all the bits should result in a zero result
        longword testValue3 = new longword();
        testValue3.set(-1);
        int expectedValue3 = 0;
        System.out.println(testValue3.rightShift(32).getSigned() == expectedValue3 ? "Right shift all bits passes." : "Right shift all bits fails.");

        //calling rightShift with out of bound amounts shows that the exception handling works
        //System.out.println("Show exception handling works - ");
        //longword testValue4 = new longword();
        //System.out.println(testValue4.rightShift(-11).getSigned() == expectedValue3 ? "Right shift all bits passes." : "Right shift all bits fails.");
        //System.out.println(testValue4.rightShift(33).getSigned() == expectedValue3 ? "Right shift all bits passes." : "Right shift all bits fails.");
        
        System.out.println();
    }

    public static void testCopy() throws Exception
    {
        System.out.println("Test copy method:");

        //test 1:
        //copy over longword set to -1 to show copy works over every bit
        longword testValue1 = new longword(), testValue2 = new longword();
        testValue1.set(-1);
        testValue2.copy(testValue1);
        System.out.println(testValue2.getSigned() == -1 ? "Copy -1 passes." : "Copy -1 fails.");

        //test 2:
        //copy over bits from longword set to max int value to show copy works over a lot of bits
        longword testValue3 = new longword(), testValue4 = new longword();
        testValue3.set(2147483647);
        testValue4.copy(testValue3);
        System.out.println(testValue4.getSigned() == 2147483647 ? "Copy 2147483647 passes." : "Copy 2147483647 fails.");

        //test 3:
        //copy over bits after setting current to max int value to show copy works to unset all bits
        longword testValue5 = new longword(), testValue6 = new longword();
        testValue5.set(2147483647);
        testValue5.copy(testValue6);
        System.out.println(testValue5.getSigned() == 0 ? "Copy 0 passes." : "Copy 0 fails.");

        System.out.println();
    }
}