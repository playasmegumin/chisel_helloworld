// package misc

import chisel3._

class DivPass extends Module {
    val io = IO(new Bundle{
        val input       = Input(UInt(8.W))
        val out_high    = Output(UInt(4.W))
        val out_low     = Output(UInt(4.W))
    })

    io.out_high := io.input(7,4)
    io.out_low  := io.input(3,0)
}

// 所以这个东西就是同名伴生对象吗？
object DivPass extends App {
  emitVerilog(new DivPass())
}

class myIO extends Bundle {
    val In_A = Input(UInt(2.W))
    val In_B = Input(UInt(2.W))
    val In_C = Input(UInt(2.W))
    val In_D = Input(UInt(2.W))
}

class DC1_Mux2b4to1 extends Module {
    val io = IO(new Bundle {
        val sel         = Input(UInt(2.W))
        // 说起来 Bundle 应该有一个可以变成 UInt 的方法
        val input       = new myIO
        val output      = Output(UInt(2.W))
        val flat_input  = Output(UInt(8.W))
    })

    val mux_output = Mux(io.sel(0), 
        // x1
        Mux(io.sel(1), 
        // 11
            io.input.In_D, 
        // 01
            io.input.In_B
        ), 
        // x0
        Mux(io.sel(1), 
        // 10
            io.input.In_C,
        // 00
            io.input.In_A
    ))
    
    // 直接用 io.output := new Mux(...) 会提示出错
    // 直接用 io.output := Wire(Mux(...)) 也会出错
    io.output := mux_output
    io.flat_input := io.input.asUInt
}

// 如果没有下面这个 伴生对象 的代码， `sbt run` 的时候就不会提示有 `DC1_Mux2b4to1` 的部分
object DC1_Mux2b4to1 extends App {
    emitVerilog(new DC1_Mux2b4to1())
}