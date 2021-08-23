package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Spark01_SparkSQL_Basic {
  def main(args: Array[String]): Unit = {

    //创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import  spark.implicits._

//    val df = spark.read.json("datas/user.json")
//    df.show()
//
//    df.createOrReplaceTempView("user")
//    spark.sql("select * from user").show()
//
//    df.select("age").show()

//    //TODO DataSet
//    val seq = Seq(1,2,3,4)
//    val ds = seq.toDS()
//    ds.show()

    //RDD => DataFrame
    val rdd = spark.sparkContext.makeRDD(List((1,"zhangsan",30),(2,"lisi",40)))
    val df = rdd.toDF("id","name","age")
    df.show()
    df.rdd

    //RDD <=> DataSet
    val ds1 = rdd.map{
      case (id,name,age) =>{
        User(id,name,age)
      }
    }.toDS()
    ds1.show()
    val userRDD = ds1.rdd



    //关闭环境
    spark.close()
  }

  case class User(id:Int,name:String,age:Int)

}
