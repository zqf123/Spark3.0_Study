package com.atguigu.bigdata.spark.core.rdd.operator.transform

import java.text.SimpleDateFormat

import org.apache.spark.{SparkConf, SparkContext}

object Spark06_RDD_Operator_Transform_test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile("datas/apache.log")

    val timeRDD = rdd.map(
      line => {
        val datas = line.split(" ")
        val time = datas(3)

        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date = sdf.parse(time)
//        println("time1:"+date)
        val sdf1 = new SimpleDateFormat("HH")
        val hour = sdf1.format(date)
//        println("hour:"+hour)
        (hour, 1)
      }
    ).groupBy(_._1)

    timeRDD.map {
      case (hour, iter) => {
        (hour, iter.size)
      }
    }.collect().foreach(print)

    sc.stop()

  }

}
