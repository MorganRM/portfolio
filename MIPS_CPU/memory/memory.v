/* memory module
 *
 * This module receives the current program counter from the PC and determines
 * the instruction. This instruction is sent to the rest of the cpu to execute
 * the comand appropriately.
 * instructionCount is added to get statistics on the performance of the cpu.
 * This output is sent to the statistics module.
 */
module memory(input [31:0] currPC, output reg [31:0] instr, output reg [31:0] instructionCount);

    reg [31:0] mem[29'h00100000:29'h00100100];

    initial begin
	instructionCount = 0;
        $readmemh("fibonacciRefined/fibonacciRefined.v", mem);
    end

    always @(currPC) begin
	instructionCount += 1; // used in statistics for IPC
        instr = mem[currPC[31:2]]; // removes the last two offset bits
    end

endmodule
