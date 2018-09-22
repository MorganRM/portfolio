/* getJumpAddr module
 *
 * This module takes the instruction as well as the pcPlus4 value and sets the
 * new jump address. The first four bits are taken from PCPlus4 value and the
 * rest is taken from the last 26 bits of the instruction, shifted left for the
 * 2 bit offset.
 */
module getJumpAddr(input [31:0] instr, input [31:0] PCplus4, output wire [31:0] jumpAddr);

    assign jumpAddr = {PCplus4[31:28], instr[25:0] << 2};

endmodule
