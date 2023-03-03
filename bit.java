/*
Author: My Tran
Filename: bit.java
Description: Definition of methods and members for class representing a bit value. 
*/
public class bit
{
    //Private data members:

    private boolean bitValue;//Member uses true or false to represent set or unset bit respectively

    //Methods:

    public bit()//default constructor
    {  
        bitValue = false;//bit is false by default
    }

    public bit(boolean value)//non-default constructor
    {
        bitValue = value;
    }

    public bit(bit other)
    {
        bitValue = other.getValue();
    }

    public void set(boolean value)//sets the value of the bit
    {
        bitValue = value;
    }

    public void toggle()//changes the value from true to false or false to true
    {
        bitValue = (bitValue) ? false : true;
    }

    public void set()//sets the bit to true
    {
        bitValue = true;
    }

    public void clear()//sets the bit to false
    {
        bitValue = false;
    }

    public boolean getValue()//returns the current value
    {
        return bitValue;
    }

    public bit and(bit other)//performs and on two bits and returns a new bit set to the result
    {
        bit result = new bit();//and needs to return variable of type bit
        
        //the current bit and other bit are compared using AND logic to assign value to result
        //current AND other is only true when both are true. False otherwise.
        result.bitValue = (bitValue) ? ((other.bitValue) ? true : false) : false;  
       
        return result;
    }

    public bit or(bit other)//performs or on two bits and returns a new bit set to the result
    {
        bit result = new bit();//or needs to return variable of type bit

        //the current bit and other bit are compared using OR logic to assign value to result
        //current OR other is only false when both are false. True otherwise.
        result.bitValue = (bitValue) ? true : ((other.bitValue) ? true : false);

        return result;
    }

    public bit xor(bit other)//performs xor on two bits and returns a new bit set to result
    {
        bit result = new bit();//xor needs to return variable of type bit
        
        //the current bit and other bit are compared using XOR logic to assign value to result
        //current XOR other is only true when there is a true and a false. False otherwise
        result.bitValue = (bitValue) ? ((!other.bitValue) ? true : false) : ((other.bitValue) ? true : false);
        
        return result;
    }

    public bit not()//performs not on existing bit, returning the result as a new bit
    {
        bit result = new bit();//not needs to return variable of type bit

        //current bit value must be compared so that result is assigned the opposite
        result.bitValue = (bitValue) ? false : true;

        return result;
    }

    @Override
    public String toString()//returns string literal representation of the bit's value
    {
        return bitValue ? "t" : "f";
    }
}
