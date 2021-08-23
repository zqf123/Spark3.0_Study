package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Spark06_SparkSQL_Test {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME","root")

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("hive")
    val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()

//    spark.sql("create database atguigu")
    spark.sql("use atguigu")
    //准备数据
    spark.sql(
      """
        |create table if not exists `user_visit_action`(
        |   `date` string,
        |   `user_id` bigint,
        |   `session_id` string,
        |   `page_id` bigint,
        |   `action_time` string,
        |   `search_keyword` string,
        |   `click_category_id` bigint,
        |   `click_product_id` bigint,
        |   `order_category_ids` string,
        |   `order_product_ids` string,
        |   `pay_category_ids` string,
        |   `pay_product_ids` string,
        |   `city_id` bigint)
        |row format delimited fields terminated by '\t'
        |""".stripMargin
    )

    spark.sql(
      """
        |load data local inpath 'datas/user_visit_action.txt' into table atguigu.user_visit_action
        |""".stripMargin)


    spark.sql(
      """
        |create table if not exists `product_info`(
        |   `product_id` bigint,
        |    `product_name` string,
        |    `extend_info` string)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)

    spark.sql(
      """
        |load data local inpath 'datas/product_info.txt' into table atguigu.product_info
        |""".stripMargin)

    spark.sql(
      """
        |create  table if not exists `city_info`(
        |   `city_id` bigint,
        |   `city_name` string,
        |   `area` string)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)

    spark.sql(
      """
        |load data local inpath  'datas/city_info.txt' into table atguigu.city_info
        |""".stripMargin)

    spark.sql(
      """
        |select * from city_info
        |""".stripMargin).show()

    spark.close()
  }

}
