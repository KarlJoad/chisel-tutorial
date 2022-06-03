// See LICENSE.txt for license details.
package problems

import chisel3._
import scala.collection.mutable.ArrayBuffer

// Problem:
//
// Implement a four-by-four multiplier using a look-up table.
//
class Mul(inWidth: Int) extends Module {
  val io = IO(new Bundle {
    val x   = Input(UInt(inWidth.W))
    val y   = Input(UInt(inWidth.W))
    val z   = Output(UInt((inWidth * 2).W))
  })
  val mulsValues = new ArrayBuffer[UInt]()

  // Calculate io.z = io.x * io.y by generating a "table" of values for mulsValues
  for (i <- 0 until (1 << inWidth))
    for (j <- 0 until (1 << inWidth))
      mulsValues += (i * j).asUInt((inWidth * 2).W)

  // Initialize the table of values
  val tbl_vals = VecInit(mulsValues)

  // Index into the "table" by using the inputs
  io.z := tbl_vals((io.x << inWidth) | io.y)
}
