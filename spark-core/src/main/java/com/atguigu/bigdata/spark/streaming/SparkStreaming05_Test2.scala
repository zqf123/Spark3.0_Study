package com.atguigu.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming05_Test2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
    val ssc = new StreamingContext(sparkConf,Seconds(5))

    //创建DStream
    val lineDStream1 = ssc.socketTextStream("hadoop102", 9999)
    val lineDStream2 = ssc.socketTextStream("hadoop102", 9990)

    //将两个流转换为KV类型
    val wordToOneDStream = lineDStream1.flatMap(_.split("")).map((_,1))
    val wordToADStream = lineDStream2.flatMap(_.split(" ")).map((_,"a"))

    //流的join
    val joinDStream = wordToOneDStream.join(wordToADStream)

    joinDStream.print()

    ssc.start()
    ssc.awaitTermination()





    //启动
    ssc.start()
    ssc.awaitTermination()
  }

}
