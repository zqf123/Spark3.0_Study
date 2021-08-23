package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object Spark15_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(("a", 1), ("a", 2),("a",3) ,("b", 4)))

    //reduceByKey:相同的key的数据进行value数据的聚合操作
    //scala语言中一般的聚合操作都是两两聚合，spark基于scala开发的
    //【1,2,3】
    //【3,3】
    //【6】
    //reduceByKey中如果key的数据只有一个，是不会参与运算的
    val reduceRDD = rdd.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)

    sc.stop()

  }

}
