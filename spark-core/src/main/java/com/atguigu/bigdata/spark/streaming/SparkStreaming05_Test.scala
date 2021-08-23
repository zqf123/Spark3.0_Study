package com.atguigu.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming05_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
    val ssc = new StreamingContext(sparkConf,Seconds(3))

    //创建DStream
    val lineDStream = ssc.socketTextStream("hadoop102", 9999)

    //转换为RDD操作
    val wordAndCountDStream = lineDStream.transform(
      rdd => {
        val words = rdd.flatMap(_.split(" "))
        val wordAndOne = words.map((_, 1))
        val value = wordAndOne.reduceByKey(_ + _)
        value
      }
    )

    //打印
    wordAndCountDStream.print()

    //启动
    ssc.start()
    ssc.awaitTermination()
  }

}
