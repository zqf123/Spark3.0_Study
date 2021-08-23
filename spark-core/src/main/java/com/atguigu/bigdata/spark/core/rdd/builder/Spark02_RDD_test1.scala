package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_test1 {
  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(conf)

    val value = sc.makeRDD(List(1, 2, 3, 4), 2)
    val value1 = value.mapPartitionsWithIndex(
      (index, datas) => {
//        datas.map((index, _))
        if (index==1){
          datas.map((index,_))
      }else{
          Nil.iterator
        }
      }
    )

    value1.collect().foreach(println)


    sc.stop()

  }

}
