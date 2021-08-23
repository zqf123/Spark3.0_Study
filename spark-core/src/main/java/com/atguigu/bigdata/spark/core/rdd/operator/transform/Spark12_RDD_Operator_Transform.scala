package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark12_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(4,5,6,1,2,3),2)

    val newRDD = rdd.sortBy(num => num)

    newRDD.saveAsTextFile("output")
    sc.stop()

  }

}
