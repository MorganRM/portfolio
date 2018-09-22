/* Statistics module
 * 
 * This module outputs statistics on the total execution of a given program.
 * Its inputs are the clock signal and a single bit to know when to print the
 * statistics. Instruction count is totaled from the memory module and sent
 * here as an input. 
 *
 * At the start of each clock cycle (positive edge of a clock), the clock
 * count is incremented by one. 
 *
 * The four statistics output are:
 * 	- time units
 * 	- clock cycles
 * 	- number of instructions
 * 	- instructions per clock cycle
 *
 * NOTE: IPC is determined by instructions/clock count. For a single cycle
 * cpu, this value should always be 1.0.
 */
module statistics(input clk, input runStats, input [31:0] instructionCount);

  reg [31:0] clkCount;
  real ipc;

  initial
  begin
    clkCount = 0;
  end

  always @(posedge clk)
  begin
    clkCount += 1;
  end

  always @(runStats)
  begin
    ipc = instructionCount / clkCount;
    $display($time, " time units,\n clock cycles: %d,\n number of instructions: %d,\n Instructions per Clock Cycle: %f", clkCount, instructionCount, ipc);
  end
endmodule
