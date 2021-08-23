package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Spark06_Acc {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))
    val map = mutable.Map(("a", 4), ("b", 5), ("c", 6))

    //分装广播变量
    val bc = sc.broadcast(map)

    rdd1.map{
      case (w,c) => {
        //方法广播变量
        val l = bc.value.getOrElse(w, 0)
        (w,(c,l))
      }
    }.collect().foreach(println)


    sc.stop()
  }

}
