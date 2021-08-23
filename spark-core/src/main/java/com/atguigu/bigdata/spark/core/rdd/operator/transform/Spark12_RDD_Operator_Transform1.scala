package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark12_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(List(("1",1),("11",2),("2",3)),2)

    //sortBy方法可以根据指定的规制对数据源中的数据进行排序，默认为升序
    //sortBy默认情况下，不会改变分区，但是中间存在shuffle操作
    val newRDD = rdd.sortBy(num => num._1.toInt,false)

    newRDD.collect().foreach(println)
    sc.stop()

  }

}
