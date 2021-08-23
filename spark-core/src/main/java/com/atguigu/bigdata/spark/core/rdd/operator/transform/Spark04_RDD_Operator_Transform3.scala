package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark04_RDD_Operator_Transform3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List( List(1,2),List(3,4),1))

    val flatrdd = rdd.flatMap(
      data => {
        data match {
          case list: List[_] =>list
          case value:Int => List(value)
        }
      }
    )

    flatrdd.collect().foreach(println)

  }

}
