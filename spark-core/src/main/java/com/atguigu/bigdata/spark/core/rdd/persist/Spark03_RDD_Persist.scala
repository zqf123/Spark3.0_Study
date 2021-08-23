package com.atguigu.bigdata.spark.core.rdd.persist

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object Spark03_RDD_Persist {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    val list = List("Hello Scala","Hello Spark")

    val rdd = sc.makeRDD(list)

    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(word =>{
      println("@@@@@@@@@@@@@@@@@@")
      (word,1)
    })

    //cache默认持久化的操作，智能将数据保存在内存中，如果想要保存到磁盘文件，需要更改存储级别
    mapRDD.cache()

    //持久化操作必须在行动算子执行时完成的
//    mapRDD.persist(StorageLevel.DISK_ONLY)

    val reduceRDD = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("************************************")

    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    sc.stop()
  }

}
