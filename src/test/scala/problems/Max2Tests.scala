// See LICENSE.txt for license details.
package problems

import chisel3.iotesters.PeekPokeTester

// Problem:
//
// Implement test with PeekPokeTester
//
class Max2Tests(c: Max2) extends PeekPokeTester(c) {
  for (i <- 0 until 1 << 8) {
    for (j <- 0 until 1 << 8) {
      poke(c.io.in0, i)
      poke(c.io.in1, j)
      step(1)
      expect(c.io.out, (i).max(j))
    }
  }
}
