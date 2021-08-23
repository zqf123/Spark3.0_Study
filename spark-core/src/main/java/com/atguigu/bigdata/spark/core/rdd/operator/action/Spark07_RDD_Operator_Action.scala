package com.atguigu.bigdata.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

object Spark07_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List[Int](1,2))

    val user = new User()

    //RDD算子中传递的函数若是包含闭包操作，那么就会进行检测功能
    //闭包检测
    rdd.foreach(
      num => {
        println("age = "+(user.age+num))
      }
    )

    sc.stop()
  }

}

case class User(){
  var age : Int = 30
}
