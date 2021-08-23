package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark06_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List("Hello","Spark","Scala","Hadoop"),2)

    val groupRDD = rdd.groupBy(_.charAt(0))
    
    groupRDD.collect().foreach(println)


    sc.stop()

  }

}
