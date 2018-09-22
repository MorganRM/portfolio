`include "mips.h"

/* sign extend module
 *
 * This module takes in the current instruction and extends the immediate from
 * the lower 16 bits. For ORI instructions, the immediate is padded with
 * zeros, while all other commands are sign extended.
 */
module signExtend(input [31:0] instr, output reg [31:0] extendedImmediate);

  always @(*)
  begin
    if (instr[`op] == `ORI) // pad with zeros
      extendedImmediate = { 16'b0, instr[15:0] };
    else // sign extend all else
      extendedImmediate = { {16{instr[15]}}, instr[15:0]};
  end
endmodule
