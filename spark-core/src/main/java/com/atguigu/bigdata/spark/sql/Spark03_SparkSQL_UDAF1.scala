package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders, SparkSession, functions}

object Spark03_SparkSQL_UDAF1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    val df = spark.read.json("datas/user.json")
    df.createOrReplaceTempView("user")

    spark.udf.register("ageAvg",functions.udaf(new MyAvgUDAF()))

    spark.sql("select ageAvg(age) from user").show()



    spark.close()
  }

  /*
  * 自定义聚合类函数类，计算年龄的平均值
  * 1.继承org.apache.spark.sql.expressions.Aggregator, 定义泛型
  *     IN：输入的数据类型Long
  *     BUF：缓冲区的数据类型Buff
  *     OUT：输出的数据类型Long
  * 2.重写方法（6）*/
  case class Buff(var total:Long,var count:Long)
  class MyAvgUDAF() extends Aggregator[Long,Buff,Long] {

    //z&zero：初始值或零值
    //缓冲区的初始化
    override def zero: Buff = {
      Buff(0L,0L)
    }

    //根据输入的数据更新缓冲区的数据
    override def reduce(b: Buff, a: Long): Buff = {
      b.total = b.total + a
      b.count = b.count + 1
      b
    }

    //合并缓冲区
    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.total = b1.total + b2.total
      b1.count = b1.count + b2.count
      b1
    }

    //计算结果
    override def finish(reduction: Buff): Long = {
      reduction.total / reduction.count
    }

    //缓冲区的编码操作
    override def bufferEncoder: Encoder[Buff] = Encoders.product

    //输出的编码操作
    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }

}
