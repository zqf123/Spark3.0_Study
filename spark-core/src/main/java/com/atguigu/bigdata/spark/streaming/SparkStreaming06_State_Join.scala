package com.atguigu.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming06_State_Join {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkstream")
    val ssc = new StreamingContext(sparkConf,Seconds(5))

    val data999 = ssc.socketTextStream("hadoop102",9999)
    val data990 = ssc.socketTextStream("hadoop102",9990)

    val map999 = data999.map((_,9))
    val map998 = data990.map((_,8))

    val joinDS = map999.join(map998)

    joinDS.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
