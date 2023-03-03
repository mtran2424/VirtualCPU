/*
 * Author: My Tran
 * Filename: ALU.java
 * Description: Definition of methods and members of ALU class
 */
public class ALU 
{
    public static longword doOp(bit[] operation, longword a, longword b) throws Exception
    {
        if(operation.length != 4)//if the length of the op code is not 4 long, then it is invalid
        {
            throw new Exception("InvalidOperationException: Invalid operation code.");
        }
        else
        {
            if(operation[0].getValue())
            {
                if(operation[1].getValue())//integer arithmetic is indicated by 11xx
                {            
                    if(operation[2].getValue())//integer arithmetic is indicated by 11xx
                    {
                        if(operation[3].getValue())//1111 – subtract
                        {
                            return rippleAdder.subtract(a, b);   
                        }
                        else//1110 - add
                        {
                            return rippleAdder.add(a, b);
                        }
                    }
                    else//110x indicates shifts
                    {
                        //31 is all bits unset except the last 5 bits
                        longword mask = new longword(31);

                        //1101 – right shift ( “a” is the value to shift, “b” is the amount to shift it; ignore all but the lowest 5 bits)
                        if(operation[3].getValue())
                        {
                            return a.rightShift((int) b.and(mask).getUnsigned());
                        }
                        else//1100 – left shift ( “a” is the value to shift, “b” is the amount to shift it; ignore all but the lowest 5 bits)
                        {
                            return a.leftShift((int) b.and(mask).getUnsigned());
                        }
                    }
                }
                else//logic is indicated by 10xx
                {
                    if(operation[2].getValue())//101x is XOR or NOT
                    {
                        if(operation[3].getValue())//1011 – not (not “a”; ignore b)
                        {
                            return a.not();   
                        }
                        else//1010 – xor
                        {
                            return a.xor(b);
                        }
                    }
                    else//100x indicates AND or OR
                    {
                
                        if(operation[3].getValue())//1001 – or
                        {
                            return a.or(b);
                        }
                        else//1000 – and
                        {
                            return a.and(b);
                        }
                    }
                }
                
            }
            else
            {
                if(operation[1].getValue())
                {            
                    if(operation[2].getValue())
                    {
                        if(operation[3].getValue())//0111 - multiply
                        {
                            return multiplier.multiply(a, b); 
                        }
                    }
                }
                //code only reaches this point when no matching operation code is found
                throw new Exception("OperationDoesNotExistException: Operation does not exist.");
            }
        }
        
    }
}
    
