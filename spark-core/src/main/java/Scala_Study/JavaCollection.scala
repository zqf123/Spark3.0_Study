package Scala_Study

object JavaCollection {

  def main(args: Array[String]): Unit = {
    //不可变集合类似java的数组
    val arr01 = new Array[Int](4)
    println(arr01.length)

    println("arr01(0)="+arr01(0))

    //数据的遍历
    for (i<-arr01)
      println(i)

    println("------------------------")
    arr01(3) = 10
    for (i<-arr01)
      println(i)
  }

}
