package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark22_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(("a", 1), ("a", 2), ("c", 3)))
    val rdd2 = sc.makeRDD(List(("a", 5), ("c", 6), ("a", 4)))

    val leftJoinRDD = rdd1.leftOuterJoin(rdd2)

    val rightJoinRDD = rdd1.rightOuterJoin(rdd2)
    
    rightJoinRDD.collect().foreach(println)

    sc.stop()
  }

}
