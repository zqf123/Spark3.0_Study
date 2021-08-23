package Scala_Study

import scala.collection.mutable.ArrayBuffer

object ArrayBufferDemo01 {
  def main(args: Array[String]): Unit = {
    //创建ArrayBuffer
    val arr01 = ArrayBuffer[Any](3, 2, 5)

    for (i<-arr01)
      println(i)

    println(arr01.length)

    println("arr01.hash="+arr01.hashCode())

    arr01.append(90.0,13)
    println("arr01.hash="+arr01.hashCode())

    println("=========================")

    arr01(1) = 89
    println("-------------------------")
    for (i<-arr01)
      println(i)

    //删除
    arr01.remove(0)
    for (i<-arr01)
      println(i)

    println(arr01.length)
  }

}
