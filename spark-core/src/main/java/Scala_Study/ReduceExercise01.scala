package Scala_Study

object ReduceExercise01 {

  def main(args: Array[String]): Unit = {
    val list =List(1,2,3,4,5)

    def minus(num1:Int,num2:Int) : Int = {
      num1-num2
    }

    println(list.reduceLeft(minus))

    println(list.reduceRight(minus))
  }

}
