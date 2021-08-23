package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark10_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1,2,3,4,5,6),3)

//    coalescce方法默认情况下不会将分区的数据打乱重新组合
//    这种情况下的缩减分区可能会导致数据不均衡，出现数据倾斜
    val newRDD = rdd.coalesce(2, true)

    newRDD.saveAsTextFile("output")

    sc.stop()

  }

}
