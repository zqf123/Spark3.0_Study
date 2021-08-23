package Scala_Study

object StreamDemo {

  def main(args: Array[String]): Unit = {
    def multiple(num:Int):Int = {
      num
    }

    //如果这个数，逆序后和原来数相等，就返回true 否则返回false
    def eq(i:Int):Boolean = {
      println("eq被调用....")
      i.toString.equals(i.toString.reverse)
    }
    val viewSquares1 = (1 to 100).filter(eq)
    println(viewSquares1)

    //使用 view，来完成这个问题,程序中，对集合进行 map,filter,reduce,fold...
    //你并不希望立即执行，而是在使用到结果才执行，则可以使用 view 来进行优化.
    val viewSquares2 = (1 to 100).view.filter(eq)
    println(viewSquares2)

    //遍历
    for (item <- viewSquares2)
      println("item="+item)
  }

}
