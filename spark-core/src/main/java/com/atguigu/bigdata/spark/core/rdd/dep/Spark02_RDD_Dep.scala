package com.atguigu.bigdata.spark.core.rdd.dep

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_Dep {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("datas/1.txt")
    println(lines.dependencies)
    println("********************")

    val words = lines.flatMap(_.split(" "))
    println(words.dependencies)
    println("*********************")
    val wordToOne = words.map(word => (word, 1))
    println(wordToOne.dependencies)
    println("**********************")
    val wordToSum = wordToOne.reduceByKey(_ + _)
    println(wordToSum.dependencies)
    println("***********************")
    val array = wordToSum.collect()
    array.foreach(println)

    sc.stop()
  }

}
