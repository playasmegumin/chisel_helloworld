import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class miscTest extends AnyFlatSpec with ChiselScalatestTester { 
    "miscTest" should "pass" in { 
        test(new DC1_Mux2b4to1()) { dut => 
            dut.io.input.In_A.poke(0.U)
            dut.io.input.In_B.poke(1.U)
            dut.io.input.In_C.poke(2.U)
            dut.io.input.In_D.poke(3.U)
            dut.io.sel.poke(0.U) 
            dut.io.flat_input.expect(0x1B.U)
            dut.io.output.expect(0x0.U)
            dut.io.sel.poke(1.U) 
            dut.io.output.expect(0x1.U) // fail, Found 10 Expect 01
            dut.io.sel.poke(2.U) 
            dut.io.output.expect(0x2.U)
            dut.io.sel.poke(3.U) 
            dut.io.output.expect(0x3.U)
        } 
    } 
}