package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders, SparkSession, functions}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Spark06_SparkSQL_Test2 {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME","root")

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("hive_test")
    val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()

    spark.sql("use atguigu")

    //查询基本数据
    spark.sql(
      """
        |select
        |   a.*,
        |   p.product_name,
        |   c.area,
        |   c.city_name
        |from user_visit_action a
        |join product_info p on a.click_product_id = p.product_id
        |join city_info c on a.city_id = c.city_id
        |where a.click_product_id > -1
        |""".stripMargin
    ).createOrReplaceTempView("t1")


    //根据区域，商品进行数据聚合
    spark.udf.register("cityRemark",functions.udaf(new CityRemarkUDAF()))
    spark.sql(
      """
        |select
        |   area,
        |   product_name,
        |   count(*) as clickCnt,
        |   cityRemark(city_name) as city_remark
        | from t1 group by area,product_name
        |""".stripMargin
    ).createOrReplaceTempView("t2")

    //区域内对点击数量进行排行
    spark.sql(
      """
        |select
        |   *,
        |   rank() over(partition by area order by clickCnt desc) as rank
        |from t2
        |""".stripMargin
    ).createOrReplaceTempView("t3")


    //取前三
    spark.sql(
      """
        |select
        |   *
        |from t3 where rank <= 3
        |""".stripMargin
    ).show(false)

    spark.close()
  }


  case class Buffer(var total:Long,var cityMap:mutable.Map[String,Long])

  //自定义聚合函数：实现城市备注功能
  //1.继承Aggregator，定义泛型
  //IN:城市名称
  //BUF ： Buffer => 【点击数量，Map[(city,cnt),(city,cnt)]】
  //重写方法（6）
  class CityRemarkUDAF() extends Aggregator[String,Buffer,String]{

    //缓冲区初始化
    override def zero: Buffer = {
      Buffer(0,mutable.Map[String,Long]())
    }

    //更新缓冲区数据
    override def reduce(b: Buffer, a: String): Buffer = {
      b.total += 1
      val newCount = b.cityMap.getOrElse(a,0l) + 1
      b.cityMap.update(a,newCount)
      b
    }

    //合并缓冲区数据
    override def merge(b1: Buffer, b2: Buffer): Buffer = {
      b1.total += b2.total
      val map1 = b1.cityMap
      val map2 = b2.cityMap

      map2.foreach{
        case(city,cnt) => {
          val newCount = map1.getOrElse(city,0L) + cnt
          map1.update(city,newCount)
        }
      }
      b1.cityMap = map1
      b1
    }

    //将统计的结果生成字符串信息
    override def finish(buff: Buffer): String = {
      val remarkList = ListBuffer[String]()

      val totalcnt = buff.total
      val cityMap = buff.cityMap

      //降序排列
      val cityCntList = cityMap.toList.sortWith(
        (left,right) => {
          left._2 > right._2
        }
      ).take(2)

      val hasMore = cityMap.size > 2
      var rsum = 0l
      cityCntList.foreach{
        case(city,cnt) => {
          val r = cnt*100 / totalcnt
          remarkList.append(s"${city} ${r}")
          rsum += r
        }
      }

      if(hasMore){
        remarkList.append(s"其他${100-rsum}%")
      }
      remarkList.mkString(", ")
    }

    override def bufferEncoder: Encoder[Buffer] = Encoders.product

    override def outputEncoder: Encoder[String] = Encoders.STRING
  }
}
