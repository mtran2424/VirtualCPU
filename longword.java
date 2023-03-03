/*
 * Author: My Tran
 * Filename: longword.java
 * Description: Definition of methods and members of class representing a 32 bit longword
 */
public class longword 
{
    //Private data members:
    private bit bitArray[];

    //Methods:

    public longword()//default constructor
    {
        bitArray = new bit[32];

        //indices need to be initiallized with bit type objects
        for(int i = 0; i < 32; i++)
        {
            bitArray[i] = new bit();
        }
    }

    public longword(int value)//non-default constructor that takes int parameter
    {
        bitArray = new bit[32];

        bitArray[0] = ((value >= 0) ? new bit() : new bit(true));//the sign bit for the given value must be determined for correct representation

        value = (value >= 0) ? value : -(value + 1);//if the value is negative, two's complement requires the number to be flipped and +1

        for(int i = 31; i>0; i--)//conversion to binary is just repeated division by two, taking the remainders for values
        {
            //Starting from the least significant bit, remainders indicate a set bit the value is halved to and bits are set/unset to convert
            //When the value is negative (determined by flipped sign bit) bits must be flipped so the opposite of the positive representation is set.
            bitArray[i] = (value % 2 == 1) ? (bitArray[0].getValue() ? new bit() : new bit(true)) : (bitArray[0].getValue() ? new bit(true) : new bit());
                                            
            value /=2;
        }
    }

    public longword(bit otherArray[])//non-default constructor
    {
        bitArray = new bit[32];

        for(int i = otherArray.length; i < 32; i++)
        {
            bitArray[i] = new bit();
        }
        try
        {
            //sets the long word to the value of another bit array
            for(int i = 0; i < 32 ; i++)
            {
                bitArray[i] = new bit(otherArray[i]);
            }
        }
        catch(IndexOutOfBoundsException e)//user should be able to pass in mismatched size arrays
        {
            System.out.println("\nWarning: Array sizes not compatible. Bits partially initiallized...\n");
        }
        catch(NullPointerException e)
        {
            System.out.println("\nWarning: Passed in array was not initiallized...\n");
        }
    }

    public longword(longword other)//copy constructor
    {
        copy(other);
    }

    public bit getBit(int i)//get bit i's value (0 based indexing)
    {
        try
        {
            return bitArray[i];
        }
        catch (IndexOutOfBoundsException e)//user can not pass in a value outside of the array
        {
            throw new IndexOutOfBoundsException("Bit " + i + " does not exist within the longword.");
        }
    }

    public void setBit(int i, bit value)//set bit i's value
    {
        try
        {
            bitArray[i].set(value.getValue());
        }
        catch (IndexOutOfBoundsException e)//user can not pass in a value outside of the array
        {
            throw new IndexOutOfBoundsException("Bit " + i + " does not exist within the longword.");
        }
    }

    public longword and(longword other)//AND two longwords, returning a third
    {
        longword result = new longword();

        for(int i = 0; i < 32; i++)//third longword bits get the bits resulting from and between the first and second
        {
            result.setBit(i, bitArray[i].and(other.getBit(i)));
        }
        return result;
    }

    public longword or(longword other)//OR two longwords, returning a third
    {
        longword result = new longword();

        for(int i = 0; i < 32; i++)//third longword bits get the bits resulting from or between the first and second
        {
            result.setBit(i, bitArray[i].or(other.getBit(i)));
        }

        return result;
    }

    public longword xor(longword other)//XOR two longwords, returning a third
    {
        longword result = new longword();

        for(int i = 0; i < 32; i++)//third longword bits get the bits resulting from xor between the first and second
        {
            result.setBit(i, bitArray[i].xor(other.getBit(i)));
        }

        return result;
    }

    public longword not()//negating this longword, returning another
    {
        longword result = new longword();

        for(int i = 0; i < 32; i++)//resulting longword gets the bits resulting from notting this
        {
            result.setBit(i, bitArray[i].not());
        }

        return result;
    }

    //leftshift this longword by amount bits, creating a new longword
    public longword leftShift(int amount) throws Exception
    {
        if(amount <= 32)
        {
            if(amount >= 0)
            {
            
                longword result = new longword();

            
                //bits in new longwords are default false from constructor so the amount bit in this is copied as the first in result
                for(int i = amount, j = 0; i < 32; i++, j++)
                {
                    result.setBit(j, bitArray[i]);
                }
            
                return result;
            }
            else
            {
                throw new Exception("InvalidInputException: Bits cannot be shifted by a negative value.");
            }
        }
        else//shifting all the bits makes a 0
        {
            return new longword();
        }
    }

    //rightshift this longword by amount bits, creating a new longword
    public longword rightShift(int amount) throws Exception
    {
        if(amount <= 32)
        {
            if(amount >= 0)
            {
                longword result = new longword();

                for(int i = 0; i < amount; i++)//the recorded sign bit is used to fill the new bits from the shift with the sign
                {
                    result.setBit(i, new bit());
                }

                for(int i = amount, j = 0; i < 32; i++, j++)//the rest of the bits are filled until the end is reached so it cuts off this
                {
                    result.setBit(i, bitArray[j]);
                }
                return result;
            }
            else
            {
                throw new Exception("InvalidInputException: Bits cannot be shifted by a negative value.");
            }
        }
        else//shifting all the bits or more makes a 0
        {
            return new longword();
        }
    }

    @Override
    public String toString()//returns comma separated string of 0's and 1's
    {
        
        //priming operation is necessary to properly output comma separated list
        String output = bitArray[0].toString();

        for(int i = 1; i < 32; i++)//starting at bit number 1, the rest of the comma separated list can be generated
        {
            output += "," + bitArray[i].toString();
        }
        
        return output;
    }

    public long getUnsigned()//return the value of this longword as a long
    {
        long unsignedValue = 0;

        for(int i = 0; i < 32; i++)
        {
            //doubling the value at every bit and then adding 1 if the next bit is set, the unsigned value is found 
            //once the longword is iterated through
            unsignedValue *=2;
            unsignedValue += (bitArray[i].getValue()) ? 1 : 0;
        }

        return unsignedValue;
    }

    public int getSigned()//returns the value of this longword as an int
    {
        int signedValue = 0;

        if(!bitArray[0].getValue())//if the sign bit is not set, then the decimal value can be found normally
        {
            signedValue = (int) getUnsigned();
        }
        else//if set, two's complement must be considered, the bits must be flipped, 1 must be added, and decimal is negative
        {
            longword temp = not();

            signedValue = (int) temp.getUnsigned();

            signedValue++;
            signedValue *= -1;
        }

        return signedValue;
    }

    public void copy(longword other)//copies the value of bits from another longword into this one
    {
        for(int i = 0; i < 32; i++)
        {
            setBit(i, other.getBit(i));
        }
    }

    public void set(int value)//set the value of the bits of this longword
    {
        //premade bits are needed to reduce need to create new objects in loop below
        bit trueBit = new bit(true), falseBit = new bit();

        setBit(0, (value >= 0) ? falseBit : trueBit);//the sign bit for the given value must be determined for correct representation

        value = (value >= 0) ? value : -(value + 1);//if the value is negative, two's complement requires the number to be flipped and +1

        for(int i = 31; i>0; i--)//conversion to binary is just repeated division by two, taking the remainders for values
        {
            //Starting from the least significant bit, remainders indicate a set bit the value is halved to and bits are set/unset to convert
            //When the value is negative (determined by flipped sign bit) bits must be flipped so the opposite of the positive representation is set.
            setBit(i, (value % 2 == 1) ? (bitArray[0].getValue() ? falseBit : trueBit) : (bitArray[0].getValue() ? trueBit : falseBit));
                                            
            value /=2;
        }
    }

    @Override public boolean equals(Object o)
    {
        if(this.getClass() == o.getClass())
        {
            longword temp = (longword) o;

            //internal bit representations are the same if signed representations are equal
            if(getSigned() == temp.getSigned())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            //objects of same type can't be equal
            return false;
        }
    }

}
