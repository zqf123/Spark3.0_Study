import util.control.Breaks._

object test2 {
  def main(args: Array[String]): Unit = {

    implicit class DB1(val m:MySQL1){
      def addSuffix():String={
        m+"scala"
      }
    }

    //创建一个MySQL1实例
    val mySql = new MySQL1
    mySql.sayOk()
    println(mySql.addSuffix())
  }

}


class DB1{}

class MySQL1{
  def sayOk(): Unit ={
    println("sayOk")
  }
}


