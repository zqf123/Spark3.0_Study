package Scala_Study

import java.text.SimpleDateFormat
import java.util.Date

object RecursiveDemo01 {
  def main(args: Array[String]): Unit = {

    //传统方法完成1-50的求和任务
    val now = new Date()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = dateFormat.format(now)
    println("date="+date)

    var res = BigInt(0)
    var num = BigInt(1)
    var maxVal = BigInt(99999999l)
    while (num<=maxVal){
      res += num
      num += 1
    }
    println("res="+res)
    val now2 = new Date()
    val date2 = dateFormat.format(now2)
    println("date2="+date2)
  }

}
