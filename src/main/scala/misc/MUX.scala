package misc

import chisel3._
import javax.print.MultiDoc

// 这里可不可以赋默认值呢？
class MuxGenerator(data_width: Int, key_width: Int) extends Module {
    val io = IO(new Bundle {
        val key         = Input(UInt(key_width.W))
        val data_in     = Input(UInt(((2^key_width)*data_width).W))
        val out         = Output(UInt(data_width.W))
    })

    val lut = Wire(Vec(2^key_width, UInt(data_width.W)))
    for(i <- 1 to 2^key_width)
        lut(i) := io.data_in(i*data_width-1, (i-1)*data_width)
    
    io.out := lut(io.key)
}

// 怎么写个对拍？
class Mux2b4to1 extends Module {
    val io = IO(new Bundle{
        val key         = Input(UInt(2.W))
        val data_in     = Input(UInt(8.W))
        val out         = Output(UInt(2.W))
    })

    val inner_Mux = Module(new MuxGenerator(2, 2))
    inner_Mux.io.key       := io.key
    inner_Mux.io.data_in   := io.data_in
    io.out                 := inner_Mux.io.out
}

object Mux2b4to1 extends App {
    emitVerilog(new Mux2b4to1())
}