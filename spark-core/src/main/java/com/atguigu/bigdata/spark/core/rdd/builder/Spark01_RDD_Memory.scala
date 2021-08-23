package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}


object Spark01_RDD_Memory {
  def main(args: Array[String]): Unit = {

    //构建环境
    val conf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(conf)

    //创建RDD
    val seq = Seq[Int](1,2,3,4)
//    val value = sc.parallelize(seq)
    val value = sc.makeRDD(seq)
    value.collect().foreach(println)


    //关闭连接
    sc.stop()


  }

}
