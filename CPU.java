/*
 * Author: My Tran
 * Filename: CPU.java
 * Description: Definition of methods and members of computer class
 */
public class CPU 
{
    //Private data members:

    private bit haltBit;
    private memory systemMemory;
    private longword PC;    //program counter
    private longword SP;    //stack pointer
    private longword currentInstruction;
    private longword[] register;
    private longword op1, op2, result;
    private bit[] branchCondition;

    //Methods:

    public CPU()//default constructor
    {
        haltBit = new bit(false);//default false for the program to run
        
        systemMemory = new memory();

        op1 = new longword();
        op2 = new longword();
        result = new longword();

        PC = new longword();//default to 0 and increments in fetch/execute cycle loop
        SP = new longword(1020);//default at 1020 because stack starts at the end of memory on the last 4 bytes
        register = new longword[16];

        branchCondition = new bit[2];
        branchCondition[0] = new bit();
        branchCondition[1] = new bit();

        //populate registers with memory
        for(int i = 0; i < 16; i++)
        {
            register[i] = new longword();
        }
    }

    //loops fetch exexute cycle until halted bit indicates computer should stop
    public void run() throws Exception
    {
        while(!haltBit.getValue())
        {
            fetch();

            decode();

            execute();

            store();
        }

        haltBit.clear();
    }

    //fetches instruction from memory and increments PC by 2
    private void fetch() throws Exception
    {
        currentInstruction = systemMemory.read(PC);
        PC = (rippleAdder.add(PC, new longword(2)));
    }

    //grabs values from registers or instruction necessary to perform operation
    private void decode() throws Exception
    {
        if(currentInstruction.getBit(0).getValue())//1xxx - some ALU  op
        {
            //rightShift by 24 to get bits 4-7 to be the last 4 and mask with 15 (00...01111) to unset all but the last 4 bits
            op1 = register[(int) (currentInstruction.rightShift(24)).and(new longword(15)).getUnsigned()];

            //rightShift by 20 to get bits 8-11 to be the last 4 and mask with 15 (00...01111) to unset all but the last 4 bits
            op2 = register[(int) (currentInstruction.rightShift(20)).and(new longword(15)).getUnsigned()];
        
        }
        else //0xxx
        {
            if(currentInstruction.getBit(1).getValue())//01xx
            {
                if(currentInstruction.getBit(2).getValue())//011x
                {
                    if(currentInstruction.getBit(3).getValue())//0111 - multiply
                    {
                        //rightShift by 24 to get bits 4-7 to be the last 4 and mask with 15 (00...01111) to unset all but the last 4 bits
                        op1 = register[(int) (currentInstruction.rightShift(24)).and(new longword(15)).getUnsigned()];

                        //rightShift by 20 to get bits 8-11 to be the last 4 and mask with 15 (00...01111) to unset all but the last 4 bits
                        op2 = register[(int) (currentInstruction.rightShift(20)).and(new longword(15)).getUnsigned()];
                    }
                    else//0110 stack commands
                    {
                        if(currentInstruction.getBit(4).getValue())//0110 1x...
                        {
                            if(!currentInstruction.getBit(5).getValue())//0110 10... call
                            {
                                //jump address specified in instruction needed in call operation
                                op1 = currentInstruction.rightShift(16).and(new longword(1023));
                            }
                            //0110 11... return does nothing in execute
                        }
                        else//0110 0x...
                        {
                            if(!currentInstruction.getBit(5).getValue())//0110 00... push
                            {
                                //value from specified register is needed to be written into stack memory
                                op1.copy(register[(int) (currentInstruction.rightShift(16).and(new longword(15)).getUnsigned())]);
                            }
                            //0110 01... pop does nothing in decode
                        }
                    }
                }
                else//010x
                {
                    if(!currentInstruction.getBit(3).getValue())//0100 - compare
                    {
                        //rightShift by 20 to get bits 8-11 to be the last 4 and mask with 15 (00...01111) to unset all but the last 4 bits
                        op1 = register[(int) (currentInstruction.rightShift(20)).and(new longword(15)).getUnsigned()];

                        //rightShift by 16 to get bits 12-15 to be the last 4 and mask with 15 (00...01111) to unset all but the last 4 bits
                        op2 = register[(int) (currentInstruction.rightShift(16)).and(new longword(15)).getUnsigned()];
                    }
                    //0101 - branch does nothing in decode
                }
            }
            else//00xx
            {
                //0010 is interrupt which doesn't do anything in decode. Other 001x operations have not been specified yet
                if(!currentInstruction.getBit(2).getValue())//000x
                {
                    if(currentInstruction.getBit(3).getValue())//0001 - Move
                    {
                        //op1 stores the operation register in move rather than the value in the register
                        op1 = currentInstruction.rightShift(24).and(new longword(15));
                        
                        //0...0 1111 1111 internal value in the mask
                        //op2 stores the 8 bit number specified in the last Byte of the instruction
                        op2 = signExtend(8, 16, new longword(255), currentInstruction);
                    }
                    //0000 is halt which does nothing in decode
                }
                //001x - 0011 is jump which doesn't do anything in decode
                //0010 is interrupt which doesn't do anything in decode
            }
        }
    }

    //sign extends and returns a longword value given the place of the sign bit in the input, the amount to right shift
    //for the value to occupy the least significant bits, a mask, and the input value
    private longword signExtend(int signBit, int shiftAmount, longword mask, longword input) throws Exception
    {
        if(input.getBit(signBit).getValue())//if sign bit is set, number is negative
        {
            return input.rightShift(shiftAmount).or(mask.not());//mask sets leading zeros to ones and preserves value bits
        }
        else
        {
            return input.rightShift(shiftAmount).and(mask);//mask clears everything but value bits
        }
    }

    //executes operation specified by instruction
    private void execute() throws Exception
    {
        //bits 0-3 of currentInstruction contain the operation code corresponding to the ALU. Array required in ALU anyways
        bit[] operationCode = {currentInstruction.getBit(0), currentInstruction.getBit(1), currentInstruction.getBit(2), currentInstruction.getBit(3)};

        if(operationCode[0].getValue())//1xxx - Some ALU operation
        {
            result = ALU.doOp(operationCode, op1, op2);
        }
        else //0xxx
        {
            if(operationCode[1].getValue())//01xx
            {
                if(operationCode[2].getValue())//011x
                {
                    if(operationCode[3].getValue())//0111 - multiply
                    {
                        result = ALU.doOp(operationCode, op1, op2);
                    }
                    //0110 stack commands do nothing in execute
                }
                else//010x
                {
                    if(currentInstruction.getBit(3).getValue())//0101 - branch
                    {
                        //0101 CCxx xxxx xxxx is the branch instruction format so bits 4 and 5 comprises the condition code
                        bit[] conditionCode = {currentInstruction.getBit(4), currentInstruction.getBit(5)};
                        //0...0 0011 1111 1111 internal value in the mask
                        longword mask = new longword(1023);

                        result = new longword();
                        
                        if(!conditionCode[0].getValue())//CC = 0x ifEqual/ifNotEqual
                        {
                            if(conditionCode[1].getValue())//CC = 01 ifEqual
                            {
                                if(branchCondition[1].getValue())
                                {
                                    //jump amount for branch is sign extended
                                    result = signExtend(6, 16, mask, currentInstruction);
                                }
                            }
                            else//CC = 00 ifNotEqual
                            {
                                if(!branchCondition[1].getValue())
                                {
                                    //jump amount for branch is sign exteneded
                                    result = signExtend(6, 16, mask, currentInstruction);
                                }
                            }
                        }
                        else//CC = 1x ifGreaterThan/ifGreaterThanOrEqual
                        {
                            if(conditionCode[1].getValue())//CC = 11 ifGreaterThanOrEqual
                            {
                                if(branchCondition[0].or(branchCondition[1]).getValue())
                                {
                                    result = signExtend(6, 16, mask, currentInstruction);
                                }
                            }
                            else//CC = 10 ifGreaterThan
                            {
                                if(branchCondition[0].getValue())
                                {
                                    result = signExtend(6, 16, mask, currentInstruction);
                                }
                            }
                        }   
                    }
                    else//0100 - compare
                    {
                        //compare register values in op1 and op2 by subtracting them
                        result = rippleAdder.subtract(op1, op2);

                        //branchCondition[0] = set if >= | clear if <
                        //branchCondition[1] = set if == | clear if !=
                        if(result.getBit(0).getValue())//X - Y = negative if X < Y
                        {
                            branchCondition[0].clear();
                            
                            //less than also implies that they were not equal
                            branchCondition[1].clear();
                        }
                        else//X - Y = positive if X > Y
                        {
                            //positive result implies greater than
                            branchCondition[0].set();

                            if(result.getSigned() == 0)//if the result is 0, X =+ Y
                            {
                                branchCondition[1].set();
                            }
                            else
                            {
                                branchCondition[1].clear();
                            }
                        }
                    }
                }
            }
            else//00xx
            {
                if(operationCode[2].getValue())//001x
                {
                    if(!operationCode[3].getValue())//0010 - interrupt
                    {
                        if(currentInstruction.getBit(15).getValue())//0010...1 indicates print all 1024 bytes of memory to screen
                        {
                            for(int i = 0; i < 1024; i+= 4)
                            {
                                System.out.println(systemMemory.read(new longword(i)));
                            }
                        }
                        else//0010...0 indicates print all registers to screen
                        {
                            for(int i = 0; i < register.length; i++)
                            {
                                System.out.println(register[i]);
                            }
                        }
                    }
                    //0011 - jump doesn't do anything in execute
                }
                else//000x
                {
                    if(operationCode[3].getValue())//0001 - Move
                    {
                        //op2 should contain the value to be moved to register from instruction
                        result = op2;
                    }
                    else//0000 - Halt
                    {
                        haltBit.set();
                    }
                }
            }
        }
        

    }

    //stores result of operation from execute to register if applicable
    private void store() throws Exception
    {
        if(currentInstruction.getBit(0).getValue())//1xxx - some ALU op
        {
            //right shift 16 to get bits 12-15 (Destination register) to be the last 4 and mask with 15 (00...01111) to unset all but the last 4
            register[(int) (currentInstruction.rightShift(16)).and(new longword(15)).getUnsigned()] = result;
        
        }
        else //0xxx
        {
            if(currentInstruction.getBit(1).getValue())//01xx
            {
                if(currentInstruction.getBit(2).getValue())//011x
                {
                    if(currentInstruction.getBit(3).getValue())//0111 - multiply
                    {
                        //right shift 16 to get bits 12-15 (Destination register) to be the last 4 and mask with 15 (00...01111) to unset all but the last 4
                        register[(int) (currentInstruction.rightShift(16)).and(new longword(15)).getUnsigned()].copy(result);
                    }
                    else//0110 stack commands
                    {
                        if(currentInstruction.getBit(4).getValue())//0110 1x...
                        {
                            if(currentInstruction.getBit(5).getValue())//0110 11... return
                            {
                                //return pops address value from the top of the stack and sets PC to that address
                                SP = rippleAdder.add(SP, new longword(4));

                                //Stack cannot perform operation when it is empty. Stack is empty when the stack pointer is pointing beyond addressable memory
                                if((int) SP.getUnsigned() > 1020)
                                {
                                    throw new Exception("EmptyStackException: Stack is currently empty.");
                                }

                                PC.copy(systemMemory.read(SP));
                            }
                            else//0110 10... call
                            {
                                //PC is already incremented to next instruction in fetch
                                systemMemory.write(SP, PC);
                                
                                SP = rippleAdder.subtract(SP, new longword(4));
                                //Stack cannot be written onto if memory capacity of the stack has been exceeded. Stack is full when the stack pointer points to zero
                                if((int) SP.getUnsigned() < 0)
                                {
                                    throw new Exception("StackOverflowException: Access of memory beyond the bounds of the stack has been attempted.");
                                }

                                //return address on stack needs to be retrieved for PC to return to
                                PC.copy(op1);
                            }
                        }
                        else//0110 0x...
                        {
                            if(currentInstruction.getBit(5).getValue())//0110 01... pop stores value from stack in destination register and then moves the stack pointer to the stack value below the current
                            {
                                //SP must be incremented before accessing to move onto the stack value we intend to address
                                SP = rippleAdder.add(SP, new longword(4));

                                //Stack cannot perform operation when it is empty. Stack is empty when the stack pointer is pointing beyond addressable memory
                                if((int) SP.getUnsigned() > 1020)
                                {
                                    throw new Exception("EmptyStackException: Stack is currently empty.");
                                }

                                register[(int) (currentInstruction.rightShift(16).and(new longword(15))).getUnsigned()].copy(systemMemory.read(SP));
                            }
                            else//0110 00... push
                            {
                                //push writes value from source register to stack and then moves the stack pointer back 4 bytes in memory
                                systemMemory.write(SP, op1);

                                SP = rippleAdder.subtract(SP, new longword(4));

                                //Stack cannot be written onto if memory capacity of the stack has been exceeded. Stack is full when the stack pointer points to zero
                                if((int) SP.getUnsigned() < 0)
                                {
                                    throw new Exception("StackOverflowException: Access of memory beyond the bounds of the stack has been attempted.");
                                }
                            }
                        }
                    }
                }
                else//010x
                {
                    if(currentInstruction.getBit(3).getValue())//1010 - branch
                    {
                        //value needs to be checked before assigning to new address to program counter
                        longword postJumpAddress = rippleAdder.add(PC, result);

                        //jump amount cannot result in negative address or push the PC over 1022 because memory doesn't exist to be accessed in these instances
                        if(postJumpAddress.getSigned() < 0 || postJumpAddress.getSigned() > 1022)
                        {
                            throw new Exception("OutOfMemoryException: Jump by " + postJumpAddress.getSigned() + " is not possible from current memory.");
                        }
                        else
                        {
                            PC = postJumpAddress;
                        }
                    }
                }
            }
            else//00xx
            {
                if(currentInstruction.getBit(2).getValue()) 
                {
                    if(currentInstruction.getBit(3).getValue())//0011 - jump
                    {
                        longword mask = new longword(4095);

                        PC.copy(currentInstruction.rightShift(16).and(mask));
                    }
                    //0010 - interrupt does nothing in store
                }
                else//000x
                {
                    if(currentInstruction.getBit(3).getValue())//0001 - Move
                    {
                        register[(int) op1.getUnsigned()] = result;
                    }
                    //0000 would be halt so no values altered
                }
            }
        }
    }

    //preload memory with instructions
    public void preload(String[] bitString) throws Exception
    {
        longword temp = new longword();

        //false and true bits premade to reduce redudancy in making bit instances in loop
        bit falseBit = new bit(), trueBit = new bit(true);

        for(int i = 0, j = 0; i < bitString.length; i++, j=0)
        {
            for(int k = 0; k < bitString[i].length(); k++)
            {
                //0 becomes false bit and 1 becomes true bit in temp
                switch(bitString[i].charAt(k))
                {
                    case '0': 
                        temp.setBit(j, falseBit);
                        j++;
                        break;
                    case '1': 
                        temp.setBit(j, trueBit);
                        j++;
                        break;
                    case ' ': break;//spaces need to get skipped over
                    default:
                    //exception is thrown if character token is not 1, 0, or a space
                    throw new Exception("InvalidTokenException: Token can not be identified.");
                }
            }
            systemMemory.write(new longword(i*2), temp);
        }
    }
}
