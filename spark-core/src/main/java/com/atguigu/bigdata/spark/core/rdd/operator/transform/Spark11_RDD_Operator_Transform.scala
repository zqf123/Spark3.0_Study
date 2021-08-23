package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark11_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1,2,3,4,5,6),2)

    //coalesce算子可以扩大分区，但是如果不进行shuffle操作，是没有意义的
    //所以如果想要扩大分区的效果，需要使用shuffle操作
    //spark提供了一个简化的操作
    //缩减分区：coalesce，如果想要数据均衡，可以采用shuffle
    //扩大分区：repartition，底层代码调用的就是coalesce，而且肯定采用shuffle
    val newRDD = rdd.repartition(3)

    newRDD.saveAsTextFile("output")

    sc.stop()

  }

}
