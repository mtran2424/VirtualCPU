/*
 * Author: My Tran
 * Filename: memory.java
 * Description: Definition of methods and members of memory class
 */
public class memory 
{
    //Private data members:
    private bit[][] storage;

    //Methods:

    public memory()//Default Constructor
    {
        storage = new bit[1024][8];//storage set up to represent 8 bits for each of 1024 Bytes

        //need to populate bytes with bits
        for(int i = 0; i < 1024; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                storage[i][j] = new bit();
            }
        }

    }

    //read and return longword from memory address
    public longword read(longword address) throws Exception
    {
        //Uses unsigned representation to eliminate possibility for negatives.
        //Must also have enough Bytes to populate a longword
        if((int) address.getUnsigned() > 1021)
        {
            throw new Exception("OutOfMemoryException: Invalid address");
        }
        else
        {
            longword output = new longword();

            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    //reads bit from memory at address to output. When a byte from memory is read, next byte is read, until 4 Bytes are read.
                    output.setBit((i*8)+j, storage[((int) address.getUnsigned())+i][j]);
                }
            }

            return output;
        }
    }

    //write longword value to memory at address
    public void write(longword address, longword value) throws Exception
    {
        //Uses unsigned representation to eliminate possibility for negatives.
        //Must also have enough Bytes for value to populate
        if((int) address.getUnsigned() > 1021)
        {
            throw new Exception("OutOfMemoryException: Invalid address");
        }
        else
        {
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    //reads bits from longword to storage at address. Once 8 bits is written to memory, moves to next Byte in storage to continue writing.
                    storage[(int) address.getUnsigned() + i][j].set(value.getBit((i*8)+j).getValue());
                }
                
            }
        }
    }

    //for development only
    /*
    public void printMemory()
    {
        for(int i = 0; i < 1024; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                System.out.print(storage[i][j]);
            }
            System.out.println();
        }
    }
    */
}
