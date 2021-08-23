package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_File1 {
  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(conf)

    //读取结果为元组，读取第一个元素为文件路径，第二个元素为内容
    val rdd = sc.wholeTextFiles("datas")

    rdd.collect().foreach(println)

    sc.stop()

  }

}
