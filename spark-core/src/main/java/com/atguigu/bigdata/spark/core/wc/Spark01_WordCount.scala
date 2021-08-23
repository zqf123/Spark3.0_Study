package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_WordCount {
  def main(args: Array[String]): Unit = {

    //建立与Spark框架的连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    //执行业务操作
    //1.读取文件，获取一行一行的数据
    val lines = sc.textFile("datas")

    //2.将每行数据进行分词
    val words = lines.flatMap(_.split(" "))

    //3.转换数据结构 word => (word,1)
    val word = words.map((_, 1))

    //4.将转换后的数据按照相同的单词进行聚合
    val value = word.reduceByKey(_ + _)

    //5.将结果采集到内存中
    val tuples = value.collect()

    //6.打印结果
    tuples.foreach(println)

    //关闭连接
    sc.stop()
  }
}
