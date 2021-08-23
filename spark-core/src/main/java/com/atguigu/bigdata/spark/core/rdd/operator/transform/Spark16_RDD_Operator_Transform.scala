package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark16_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(("a", 1), ("a", 2),("a",3) ,("b", 4)))

    val groupRDD = rdd.groupByKey()
    groupRDD.collect().foreach(println)

    val groupRDD1 = rdd.groupBy(_._1)
    groupRDD1.collect().foreach(println)

    sc.stop()

  }

}
