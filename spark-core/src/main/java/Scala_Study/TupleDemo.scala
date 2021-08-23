package Scala_Study

import scala.collection.mutable

object TupleDemo {

  def main(args: Array[String]): Unit = {
//    val t1 = (1,"a","b",true,2)
//    println(t1._1)

    val map1 = mutable.Map(("a", 1), ("b", 2))

    println(map1.getOrElse("c",0))


  }

}
