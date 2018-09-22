/* pc module
 * 
 * This module is the program counter. It changes at the positive edge of the
 * clock to ensure that it runs at the beginning of each clock cycle. The
 * current program counter then changes to be the next program counter, so
 * that a new instruction can be read from the memory module. 
 *
 * NOTE: the current PC is set to 32'h00400020 and the conditional logic in
 * the always block deals with the test bench clock starting at 1 instead of
 * 0. This way no instructions are lost. 
 */
module pc(input clk, input [31:0] nextPC, output reg [31:0] currPC);

initial
begin
    currPC = 32'h00400020;
end

always @(posedge clk)
begin
  if($time != 0) // accounts for testbench clock starting at t=1
    currPC = nextPC;
end

endmodule
