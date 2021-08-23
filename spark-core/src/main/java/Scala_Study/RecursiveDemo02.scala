package Scala_Study

import java.text.SimpleDateFormat
import java.util.Date

object RecursiveDemo02 {
  def main(args: Array[String]): Unit = {

    //传统方法完成1-50的求和任务
    val now = new Date()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = dateFormat.format(now)
    println("date="+date)

    def mx(num:BigInt,sum:BigInt):BigInt = {
      if (num<=99999999l) return mx(num+1,sum+num)
      else return sum
     }

    var num = BigInt(1)
    var sum = BigInt(0)
    var res = mx(num,sum)
    println("res="+res)

    val now2 = new Date()
    val date2 = dateFormat.format(now2)
    println("date2="+date2)
  }

}
