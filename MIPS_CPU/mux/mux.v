/* mux module for 32 bit inputs
 *
 * This module represents a simple two input mux where the output is
 * determined by the input control signal. This mux is used for 32 bit inputs.
 */
module mux(input controlSignal, input [31:0] input0, input [31:0] input1, output reg [31:0] muxOut);

always @(*)
begin
  if (controlSignal == 0)
    muxOut = input0;
  else
    muxOut = input1;
end

endmodule
