/* memReadWrite module
 *
 * This module is the data memory actions. The inputs are the clock value,
 * control bits for memWrite and memRead, the address which is a result from
 * the alu, and writeData from the registers module. This module initializes
 * some memory which will start at the top of the stack and allow for up to
 * 256 words.
 * As seen in the sensitivity statements, this module is constantly reading,
 * but will only write on the negative edge of the clock. This is to ensure
 * that we are not writing before all of the other modules are completed so
 * that the proper address can be written. The output of the readData is sent
 * into a mux which eventually sends information to the write data of the
 * registes module.
 */
module memReadWrite(input clk, input memWrite, input memRead, input [31:0] address, input [31:0] writeData, output reg [31:0] readData);

  reg [31:0] mem [32'h7ffffffc >> 2: (32'h7ffffffc >> 2) - 256];

  always @(*)
  begin
    if (memRead == 1)
      readData = mem[address >> 2];
  end

  always @(negedge clk)
  begin
    if (memWrite == 1)
      mem[address >> 2] = writeData;
  end
endmodule

