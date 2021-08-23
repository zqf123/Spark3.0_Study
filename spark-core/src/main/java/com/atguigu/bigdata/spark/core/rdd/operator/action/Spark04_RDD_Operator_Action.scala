package com.atguigu.bigdata.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

object Spark04_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(sparkConf)

//    val rdd = sc.makeRDD(List(1, 2, 3, 4),2)

    val rdd = sc.makeRDD(List(("a", 1), ("a", 2), ("a", 3)))
    val intTOLong = rdd.countByValue()
    val stringToLong = rdd.countByKey()
    println(stringToLong)
    println(intTOLong)


    sc.stop()
  }

}
