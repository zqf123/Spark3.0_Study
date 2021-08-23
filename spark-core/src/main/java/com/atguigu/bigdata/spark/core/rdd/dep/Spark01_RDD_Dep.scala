package com.atguigu.bigdata.spark.core.rdd.dep

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_Dep {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("datas/1.txt")
    println(lines.toDebugString)
    println("********************")

    val words = lines.flatMap(_.split(" "))
    println(words.toDebugString)
    println("*********************")
    val wordToOne = words.map(word => (word, 1))
    println(wordToOne.toDebugString)
    println("**********************")
    val wordToSum = wordToOne.reduceByKey(_ + _)
    println(wordToSum.toDebugString)
    println("***********************")
    val array = wordToSum.collect()
    array.foreach(println)

    sc.stop()
  }

}
