package com.atguigu.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming06_State_Window1 {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkstreaming")
    val ssc = new StreamingContext(sparkConf,Seconds(3))
    ssc.checkpoint("cp")

    val lines = ssc.socketTextStream("hadoop102",9998)
    val wordToOne = lines.map((_,1))

    //reduceByKeyAndWindow:当窗口范围比较大，但是滑动幅度比较小，那么可以采用增加数据和删除数据的方式
    //无需重复计算，提升性能
    val windowDS = wordToOne.reduceByKeyAndWindow(
      (x:Int,y:Int) => {x+y},
      (x:Int,y:Int)=>{x-y},
      Seconds(9),Seconds(3)
    )
    windowDS.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
