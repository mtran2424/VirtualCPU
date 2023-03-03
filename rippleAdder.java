/*
 * Author: My Tran
 * Filename: rippleAdder.java
 * Description: Definition of methods and members of rippleAdder class
 */
public class rippleAdder 
{
    public static longword add(longword a, longword b)//performs binary addition between longwords a and b
    {
        longword sum = new longword();
        bit carryBit = new bit(), trueBit = new bit(true), falseBit = new bit(false);
        for(int i = 31; i >= 0; i--)
        {
            if(a.getBit(i).xor(b.getBit(i)).getValue())//if 0 + 1 or 1 + 0
            {
                //a XOR b on bit i should be 1 in this path so if carryBit is true, 1 + 1 = 10 so carryBit is set
                if(carryBit.getValue())
                {
                    sum.setBit(i, falseBit);
                    carryBit.set();
                }
                else//otherwise, carryBit is cleared and bit i in sum is set
                {
                    sum.setBit(i, trueBit);
                    carryBit.clear();
                }
            }
            else
            {
                //this path is either 0 + 0 or 1 + 1
                if(a.getBit(i).and(b.getBit(i)).getValue())//1 + 1 = 10
                {
                    if(carryBit.getValue())//if the carry bit is set coming in, 10 + 01 = 11
                    {
                        sum.setBit(i, trueBit);
                    }
                    else
                    {
                        sum.setBit(i, falseBit);
                    }

                    carryBit.set();//1 + 1 = 10 so carryBit will be set after

                }
                else//0 + 0 = 0
                {
                    if(carryBit.getValue())//if the carry bit is set, 00 + 01 = 01
                    {
                        sum.setBit(i, trueBit);
                        carryBit.clear();//carryBit was applied so it must be cleared
                    }
                    else
                    {
                        sum.setBit(i, falseBit);
                    }   
                }
            }
        }
        return sum;
    }

    public static longword subtract(longword a, longword b)//performs subtraction between longwords a and b
    {
        //subtraction is a + -b where -b is two's complement representation
        return add(a, negate(b));
    }

    private static longword negate(longword current)//perform two's complement negation on given longword
    {
        //flip the bits in a new longword
        longword negatedLong = current.not();
        longword one = new longword();
        one.setBit(31, new bit(true));

        //return and add one to the negative 1, returning a two's complement negated longword
        return add(negatedLong, one);
    }
}
