# VirtualCPU
Virtual CPU is a Java-based program is a Java-based virtual machine simulating a basic RISC CPU architecture, capable of executing simple instructions in a virtual assembly language. This program was designed to assist in understanding concepts of computer architecture, instruction sets, machine language, and architecture design tradeoffs.

Getting Started:
---------------------------------------------------------------------------------------------------------------------
To run the virtual CPU program, you will need java installed on your computer. With Java installed, you can use the assembler and CPU classes to run virtual assemly instructions. 

Virtual Assembly Language:
---------------------------------------------------------------------------------------------------------------------
The virtual assembly language used by the CPU is a RISC instruction set. It includes basic intructions for moving data, arithmetic operations via an ALU, and control flow. The CPU has 16 named registers (R0-R15) and 21 available instructions.
Here is a list of the available instructions:

- "move": Assigns a given value to a register. Takes a register argument and numeric value. i.e. move register value

- "add" : Adds the values stored in two registers and stores it in a destination register. i.e. add register1 register2 destination

- "sub" : Subtracts the values stored in two registers and stores it in a destination register. i.e. sub register1 register2 destination

- "multiply" : Multiplies the values stored in two registers and stores it in a destination register. i.e. multiply register1 register2 destination

- "leftshift": Performs a leftshift on value in a register and stores it in destination register. i.e. leftshift register shiftamount destination

- "rightshift": Performs a rightshift on value in a register and stores it in a destination register. i.e. rightshift register shiftamount destination

- "and" : Performs a bitwise logical and on value in two register and stores it in destination register. i.e. and register1 register2 destination

- "or" : Performs a bitwise logical or on value in two register and stores it in destination register. i.e. or register1 register2 destination

- "xor" : Performs a bitwise logical xor on value in two register and stores it in destination register. i.e. xor register1 register2 destination

- "not" : Performs a bitwise logical not on value in first register and stores it in destination register. i.e. not register1 register2 destination

- "interrupt" : If odd number is given for argument, prints out all memory. If even number is passed in as argument, prints the contents of all registers. 
  i.e. interrupt 0 or interrupt 1
  
- "jump" : Jumps to given memory address. i.e. jump memoryaddress

- "compare" : Sets branch flags by comparing the value in two registers. If register1 == register 2, set equal flag. If register1 > register2, clear equal flag, set greater than flag. If register1 < register2, clear both flags. If register  i.e. compare register1 register2

- "branchIfEqual" : Conditional jump if equal flag is set. Does nothing otherwise. i.e. branchIfEqual memoryaddress

- "branchIfNotEqual" : Conditional jump if equal flag is not set. Does nothing otherwise. i.e. branchIfNotEqual memoryaddress

- "branchIfGreaterThan" : Conditional jump if greater than flag is set. Does nothing otherwise. i.e. branchIfGreaterThan memoryaddress

- "branchIfGreaterThanOrEqual" : Conditional jump if greater than flag or equal flag is set. Does nothing otherwise. i.e. branchIfGreaterThanOrEqual memoryaddress

- "push" : Pushes value in given value onto system stack. i.e. push register

- "pop" : Pops value on top of system stack onto given register. i.e. pop stack

- "call" : Pushes current instruction address onto system stack and jumps to given address. i.e. call memoryaddress

- "return" : Returns to instruction at memory address on top of system stack. i.e. return

Usage:
---------------------------------------------------------------------------------------------------------------------
In another Java file, you can use pass in instructions in a string array to the assembler to be assembled into "machine language" that can be loaded into the virtual CPU object and run.

i.e.
String[] instructions = {..., ...};
CPU example = new CPU();

example.preload(assembler.assemble(instructions));
example.run();

Limitations:
---------------------------------------------------------------------------------------------------------------------
The CPU is a simulation of a architecture design architecture of our choosing. Therefore there are some limitations:

- The program memory is limited to a fixed size of 1024 "bytes"
- Only one 32 "bit" numerical datatype ("longword")

Conclusion:
The virtual CPU is a simple and excellent learning tool computer architecture concepts and machine language programming. It provides useful insight toward the process in which CPUs take in assembly instructions, convert them to machine language, read them into memory, and execute.
