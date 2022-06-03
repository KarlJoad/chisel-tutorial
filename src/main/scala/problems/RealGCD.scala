// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.util.{Valid, DeqIO}

// Problem:
// Implement a GCD circuit (the greatest common divisor of two numbers).
// Input numbers are bundled as 'RealGCDInput' and communicated using the Decoupled handshake protocol
//
class RealGCDInput extends Bundle {
  val a = UInt(16.W)
  val b = UInt(16.W)
}

class RealGCD extends Module {
  val io  = IO(new Bundle {
    val in  = DeqIO(new RealGCDInput())
    val out = Output(Valid(UInt(16.W)))
  })

  val x = Reg(UInt())
  val y = Reg(UInt())
  // We need this control register to handle the difference between reading
  // values in for GCD-ing and actually performing the GCD.
  // Remember, we are writing an HDL here, so all "when" blocks will technically
  // "run" simultaneously. We need a way to ctonrol which one runs.
  val should_gcd = RegInit(false.B)

  // We need to signal when this GCD module is ready to consume data from the queue
  io.in.ready := !should_gcd

  // Read in the values
  // The DeqIO class comes with 'valid' and 'ready' flags for synchronization
  when (io.in.valid && !should_gcd) {
    x := io.in.bits.a
    y := io.in.bits.b
    should_gcd := true.B
  }

  when (should_gcd) {
    when (x > y) { x := y; y := x }
      .otherwise   { y := y - x }
  }

  // The Valid class adds a valid bit to some bits that are data.
  io.out.bits := x
  // Output is only valid once y has reached 0, while should_gcd is true
  io.out.valid := (y === 0.U) && should_gcd
  when (io.out.valid) {
    should_gcd := false.B // Reset control bit once we have finished the process
  }
}
