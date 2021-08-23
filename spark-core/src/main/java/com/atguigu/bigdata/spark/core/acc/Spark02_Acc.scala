package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_Acc {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    //获取系统累加器
    // Spark默认就提供简单数据聚合的累加器
    val sumAcc = sc.longAccumulator("sum")
    rdd.foreach(
      num =>{
        //使用累加器
        sumAcc.add(num)
      }
    )
    //获取累加器的值
    println(sumAcc.value)
    sc.stop()
  }

}
