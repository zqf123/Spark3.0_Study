package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}
import org.apache.spark.sql.expressions.Aggregator

object Spark03_SparkSQL_UDAF2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
    val df = spark.read.json("datas/user.json")

    //早期版本中，spark不能在sql中使用强类型udaf操作
    //SQL & DSL
    //早期的UDAF强类型聚合函数使用DSL语法操作
    val ds = df.as[User]

    //将UDAF函数转换为查询的列对象
    val udafCol = new MyAvgUDAF().toColumn

    ds.select(udafCol).show()


    spark.close()
  }

  case class User(username:String,age:Long)
  case class Buff(var total:Long,var count:Long)
  class MyAvgUDAF extends Aggregator[User,Buff,Long] {

    //z & zero:初始值或零值
    override def zero: Buff = {
      Buff(0L,0L)
    }

    //根据输入的数据更新缓冲区的数据
    override def reduce(b: Buff, a: User): Buff = {
      b.total = b.total + a.age
      b.count = b.count + 1
      b
    }

    //合并缓冲区
    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.total = b1.total + b2.total
      b1.count = b1.count + b2.count
      b1
    }

    override def finish(reduction: Buff): Long = {
      reduction.total / reduction.count
    }

    //缓冲区的编码操作
    override def bufferEncoder: Encoder[Buff] = Encoders.product

    //输出的编码操作
    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }


}
