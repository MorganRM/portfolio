/* regMux module
 *
 * This mux is the same as the other mux, but takes 5 bit inputs. Its only use
 * is to determine which register becomes the Write Register. This register is
 * sent as an output to the registers module. 
 *
 */

module regMux(input controlSignal, input [4:0] input0, input [4:0] input1, output reg [4:0] muxOut);

always @(*)
begin
  if (controlSignal == 0)
    muxOut = input0;
  else
    muxOut = input1;
end

endmodule
