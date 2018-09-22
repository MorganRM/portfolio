/* control module
 * 
 * This module determines which operation needs to be executed based on the
 * opcode and function code (for R-type instructions). The instruction can be
 * broken up into the apporpriate bits and compared to specific values that
 * represent specific operations. 
 * The output results in the 11 control bits in the following order:
 *	10- regDst
 *	9 - jump
 *	8 - branch
 *	7 - memRead
 *	6 - memToReg
 *	5:3 - ALUop
 *	2 - regWrite
 *	1 - ALUsrc
 *	0 - memWrite
 * Additional bits for syscalls, JR, and JAL instructions are used to trigger
 * the appropriate actions in other modules.
 */
module control(input [31:0] instr, output reg syscall, output reg [10:0] signals, output reg isJR, output reg isJAL);

  reg regDst;
  reg jump;
  reg branch;
  reg memRead;
  reg memToReg;
  reg [2:0] ALUop;
  reg regWrite;   
  reg ALUsrc;
  reg memWrite;

  always @(instr)
  begin
    // reset all control bits to 0
    regDst = 0;
    jump = 0;
    branch = 0;
    memRead = 0;
    memToReg = 0;
    ALUop = 3'b000;
    regWrite = 0;   
    ALUsrc = 0;
    memWrite = 0;
    syscall = 0;
    isJR = 0;
    isJAL = 0;
 

    case (instr[`op])
     `J:
        jump = 1;
      `JAL:
      begin
        jump = 1; regWrite = 1; isJAL = 1; // set isJAL to trigger conditional in registers.v
      end
      `SPECIAL: // represents R-type instructions and JR instruction
      begin
        regDst = 1; regWrite = 1; // same for all SPECIAL instructions
        case (instr[`function]) // ALUop important for operation in alu.v
          `ADD:
            ALUop = 3'b010;
          `OR:
            ALUop = 3'b001;
          `AND:
            ALUop = 3'b000;
          `SUB:
            ALUop = 3'b110;
          `SLT: 
            ALUop = 3'b111;
	  `JR:
          begin
	    jump = 1; regWrite = 1; isJR = 1; // set isJR to use in branchMUX
          end
	  6'b000000: // nop
	    $display("");
	  `SYSCALL:
          begin
            // set all bits except for regWrite to 0
            regDst = 0; jump = 0; branch = 0; memRead = 0; memToReg = 0; ALUop = 3'b000; regWrite = 1; ALUsrc = 0; memWrite = 0; syscall = 1;
          end
          default:
            $display("R-type not yet completed\n");
        endcase
      end
      `BEQ, `BNE:
      begin
        branch = 1; ALUop = 3'b110;
      end
      `ADDIU, `ADDI:
      begin
        regWrite = 1; ALUop = 3'b010; ALUsrc = 1;
      end
      `ORI:
      begin
        regWrite = 1; ALUop = 3'b001; ALUsrc = 1; 
      end
      `LW:
      begin
        memRead = 1; memToReg = 1; ALUop = 3'b010; regWrite = 1; ALUsrc = 1;
      end
      `SW:
      begin
        ALUop = 3'b010; ALUsrc = 1; memWrite = 1;
      end
      `LUI: // used for LI, opcode is created in alu.v
      begin
	regWrite = 1; ALUop = 3'b011; ALUsrc = 1;
      end
      default:
        $display("Command not found");
    endcase

    signals = {regDst, jump, branch, memRead, memToReg, ALUop, regWrite, ALUsrc, memWrite};
  
  end
endmodule

