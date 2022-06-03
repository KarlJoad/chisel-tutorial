// See LICENSE.txt for license details.
package problems

import chisel3._
import scala.collection.mutable.ArrayBuffer

// Problem:
//
// Implement a four-by-four multiplier using a look-up table.
//
class Mul extends Module {
  val io = IO(new Bundle {
    val x   = Input(UInt(4.W))
    val y   = Input(UInt(4.W))
    val z   = Output(UInt(8.W))
  })
  val mulsValues = new ArrayBuffer[UInt]()

  // Calculate io.z = io.x * io.y by generating a "table" of values for mulsValues
  for (i <- 0 until (1 << io.x.getWidth))
    for (j <- 0 until (1 << io.y.getWidth))
      mulsValues += (i * j).asUInt(8.W)

  // Initialize the table of values
  val tbl_vals = VecInit(mulsValues)

  // Index into the "table" by using the inputs
  io.z := tbl_vals((io.x << io.x.getWidth) | io.y)
}
