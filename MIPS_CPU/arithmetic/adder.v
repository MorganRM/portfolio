/* adder module
 *
 * This module performs simple addition of the pcPlus4 address and the signed
 * immediate value from I type instructions. This signedImmediate value must
 * first be shifted left by two bits before performing the addition.
 * The result of this addition is sent into a mux input for the branch and
 * possibly jump address.
 */
module adder(input [31:0] pcPlus4, input [31:0] signedImmediate, output reg [31:0] adderResult);


  always @(*)
  begin
    adderResult = pcPlus4 + {signedImmediate << 2};
  end

endmodule
