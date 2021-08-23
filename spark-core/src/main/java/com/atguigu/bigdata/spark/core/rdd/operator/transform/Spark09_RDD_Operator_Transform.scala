package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark09_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1,2,3,4,1,2,3,4))

    val rdd1 = rdd.distinct()


    rdd1.collect().foreach(println)
    sc.stop()

  }

}
