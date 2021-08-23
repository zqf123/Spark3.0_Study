package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark21_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(("a", 1), ("a", 2), ("c", 3)))
    val rdd2 = sc.makeRDD(List(("a", 5), ("c", 6), ("a", 4)))

    //join:两个不同数据源的数据，相同的key的value会连接在一起，形成元组
    //      如果两个数据源中的key没有匹配上，那么数据不会出现在结果中
    //      如果两个数据源中可以有多个相同的，会依次匹配，可能会出现笛卡尔积，数据会几何性增长，会导致性能降低
    val joinRDD = rdd1.join(rdd2)

    joinRDD.collect().foreach(println)

    sc.stop()
  }

}
