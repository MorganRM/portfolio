/* ALU module
 *
 * This module takes the alu op control bits and determines what operation to
 * execute. The input values are read data1 from the registers module and the
 * output of the alu mux. The outputs are the result of the completed
 * operation in the alu as well as a single bit "zero" that is sent to the
 * branch and gate.
 */
module alu(input [31:0] data1, input [31:0] data2, input [2:0] aluOp, output reg [31:0] address, output reg zero);

// output zero can be a continuous result where the ALU result == 0

always @(*)
begin
  // make this a case statement
  case (aluOp)
  3'b000: // bitwise and operation
    address = data1 & data2;
  3'b001: // bitwise or operation
    address = data1 | data2;
  3'b010: // add
    address = data1 + data2;
  3'b110: // subtract
    address = data1 - data2;
  3'b111: // slt oepration
  begin
    if (data1 < data2)
      address = 1;
    else
      address = 0; 
  end
  3'b011: // LUI instruction
    address = {data2, 16'b0};
 default:
    $display("command not found");
  endcase

  zero = (address == 0) ? 1 : 0; // determine whether result is 0 and set output bit
end
endmodule
