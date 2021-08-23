package com.atguigu.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming09_Resume {

  def main(args: Array[String]): Unit = {

    val ssc = StreamingContext.getActiveOrCreate("cp",()=>{
      val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkstreaming")
      val ssc = new StreamingContext(sparkConf,Seconds(3))

      val lines = ssc.socketTextStream("hadoop102",9999)
      val wordToOne = lines.map((_,1))

      wordToOne.print()
      ssc
    })

    ssc.checkpoint("cp")

    ssc.start()
    ssc.awaitTermination() //block阻塞main线程
  }

}
