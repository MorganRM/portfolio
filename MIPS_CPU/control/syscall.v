/* syscall module
 *
 * The syscall module takes inputs of two registers, v0 and a0. A conditional
 * statement is used to determine what the value of v0 is and take the
 * appropiate action.
 * Current functionality only implements syscall 1 (print) and syscall 10
 * (exit)
 * runStats is an output here to determine when the program is terminating. At
 * termination, the statistics module will display the output.
 */
module callSys(input syscall, input [31:0] v, input [31:0] a, output reg runStats);

initial begin
  runStats = 0;
end

always @(*)
begin
  if(syscall == 1) begin // print value in a0
    if(v == 1) begin
      $display("print: %d", a);
    end
    else if(v == 10) begin // terminate program
      $display("killing program");
      runStats = 1; 
      #1; $finish;
    end
  end
end
endmodule
