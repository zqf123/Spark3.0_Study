package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Spark05_SparkSQL_Hive {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME","root")
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sql")
    val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()

    //使用SparkSQL连接外置的Hive
    //1.启动Hive-site.xml文件到classpath下
    //2.启动Hive的支持
    //3.增加对应的依赖关系（包含mysql驱动）
//    spark.sql("use gmall")
    spark.sql("show tables").show()

    spark.close()
  }

}
