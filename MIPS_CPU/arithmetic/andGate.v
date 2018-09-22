/* andGate module
 *
 * The andGate module is used for branch instructions. It takes the bitwise
 * and result of the branch control signal and zero signal from the ALU.
 * The output is a single bit that is used for a mux that determines the jump
 * address.
 */
module andGate(input branch, input zero, output reg andResult);

  always @(*)
  begin
    andResult = 0;

    if((branch == 1) & (zero == 1))
      andResult = 1; 
  end

endmodule
