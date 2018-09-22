/* registers module
 * 
 * This module handles all of the logic dealt with in the registers block. Its
 * inputs are the register addresses (read1, read2, and write each as 5 bit
 * representation), and the writeData from the data memory. The regWrite
 * control bit determines when the registers need to write from writeData. 
 * When reading, the values from the registers defined by their 5 bit are set
 * into these read datas to be sent to the ALU. 
 *
 * Three additional registers v0, a0, and ra are always set for the event of
 * a syscall or JR instruction.
 *
 * For JAL instructions, the the return address register is set to teh JAL
 * address from the program counter + 8. Otherwise, the destination register
 * gets set to the writeData.
 */
module registers(input clk, input isJAL, input [31:0] JALaddress, input [4:0] readReg1, input [4:0] readReg2, input [4:0] writeReg, input [31:0] writeData, input regWrite, output reg [31:0] readOut1, output reg [31:0] readOut2, output wire [31:0] v0, output wire [31:0] a0, output wire [31:0] ra);

  reg [31:0] reggies[0:31]; // set up 32 registers, each 32 bits
  integer i;

  initial begin
    for(i = 0; i < 32; i += 1) begin // initialize all registers to value 0
      reggies[i] = 32'b0;
    end
  end

  // constant register output assignments
  assign v0 = reggies[`v0];
  assign a0 = reggies[`a0];
  assign ra = reggies[`ra];

  // read block
  always @(readReg1, readReg2) begin
    readOut1 = reggies[readReg1];
    readOut2 = reggies[readReg2];
  end

  // write block
  always @(negedge clk) // write on the negative edge of a clock to ensure proper data has been determined
  begin
    if ((regWrite == 1) & (writeReg != `zero)) begin
      if (isJAL == 1)
        reggies[`ra] = JALaddress; // set return address for JAL instructions
      else
        reggies[writeReg] = writeData;
    end
  end
endmodule
