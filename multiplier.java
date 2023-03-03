/*
 * Author: My Tran
 * Filename: multiplier.java
 * Description: Definition of methods and members of rippleAdder class
 */
public class multiplier
{
    public static longword multiply(longword a, longword b) throws Exception
    {
        longword product = new longword();

        //implements the add and shift method to multiply a by b
        for(int i = 31; i >= 0; i--)//loop looks for set bits in b, appends "a", left shifted by that bit's two's place to result to get product
        {
            if(b.getBit(i).getValue())
            {
                product = rippleAdder.add(a.leftShift(31-i), product);
            }
        }
        
        return product;
    }
}
