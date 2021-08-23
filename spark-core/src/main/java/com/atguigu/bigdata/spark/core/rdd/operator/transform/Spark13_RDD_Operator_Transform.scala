package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark13_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6))
    val rdd3 = sc.makeRDD(List("3", "4", "5", "6"))

    //交集：【3.4】
    val rdd4 = rdd1.intersection(rdd2)
    println(rdd4.collect().mkString(","))

    //并集:[1,2,3,4,3,4,5,6]
    val rdd5 = rdd1.union(rdd2)
    println(rdd5.collect().mkString(","))

    //差集：【1,2】
    val rdd6 = rdd1.subtract(rdd2)
    println(rdd6.collect().mkString(","))

    //拉链：【1-3，2-4,3-5,4-6】
    val rdd7 = rdd1.zip(rdd2)
    println(rdd7.collect().mkString(","))

    val rdd8 = rdd1.zip(rdd3)
    println(rdd8.collect().mkString(","))


    sc.stop()

  }

}
