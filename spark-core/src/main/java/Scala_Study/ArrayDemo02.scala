package Scala_Study

object ArrayDemo02 {
  def main(args: Array[String]): Unit = {
    val arr02 = Array(1, 3, "xx")
    arr02(1) = "xx"
    for (i<-arr02)
      println(i)

    for (index <- 0 until arr02.length)
      println(arr02(index))
  }

}
