package com.atguigu.bigdata.spark.streaming

import java.text.SimpleDateFormat
import java.util.Date

import com.atguigu.bigdata.spark.util.JDBCUtil
import org.apache.kafka.clients.consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object SparkStreaming11_Req1_BlackList {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("spark")
    val ssc = new StreamingContext(sparkConf,Seconds(3))

    val kafkaPara = Map[String,Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop102:9092,hadoop103:9092,hadoop104:9092",
      ConsumerConfig.GROUP_INSTANCE_ID_CONFIG -> "atguigu",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer"
    )

    val kafkaDataDS = KafkaUtils.createDirectStream[String,String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String,String](Set("atguiguNew"),kafkaPara)
    )

    val adClickData = kafkaDataDS.map(
      kafkaData => {
        val data = kafkaData.value()
        val datas = data.split(" ")
        AdclickData(datas(0),datas(1),datas(2),datas(3),datas(4))
      }
    )

    val ds = adClickData.transform(
      rdd => {
        //TODO 通过JDBC周期性获取黑名单数据
        val blackList = ListBuffer[String]()

        val conn = JDBCUtil.getConnection
        val pstat = conn.prepareStatement("select userid from black_list")

        val rs = pstat.executeQuery()
        while(rs.next()){
          blackList.append(rs.getString(1))
        }
        rs.close()
        pstat.close()
        conn.close()

        //TODO 判断低级用户是否在黑名单中
        val filterRDD = rdd.filter(
          data => {
            !blackList.contains(data.user)
          }
        )

        //TODO 如果用户不在黑名单中，那么进行统计熟练（每个采集周期）
        filterRDD.map(
          data => {
            val sdf = new SimpleDateFormat("yyyy-MM-dd")
            val day =sdf.format(new Date(data.ts.toLong))
            val user = data.user
            val ad = data.ad

            ((day,user,ad),1)  //(word,count)
          }
        ).reduceByKey(_+_)
      }
    )

    ds.foreachRDD(
      rdd => {
        rdd.foreach{
          case ((day,user,ad),count) => {
            println(s"${day} ${user} ${count}")
            if(count >= 30){
              //TODO 如果统计数量超过阈值（30），那么将用户拉入黑名单
              val conn = JDBCUtil.getConnection
              val pstat = conn.prepareStatement(
                """
                  |insert into black_list (userid) values (?)
                  |on DUPLICATE KEY
                  |UPDATE userid = ?
                  |""".stripMargin
              )
              pstat.setString(1,user)
              pstat.setString(2,user)
              pstat.executeUpdate()
              pstat.close()
              conn.close()
            }else {
              //TODO 如果没有超过阈值，那么需要将当天的广告点击数量进行更新
              val conn = JDBCUtil.getConnection
              val pstat = conn.prepareStatement(
                """
                  |select
                  |    *
                  |from user_ad_count
                  |where dt = ? and userid = ? and adid = ?
                  |""".stripMargin
              )
              pstat.setString(1,day)
              pstat.setString(2,user)
              pstat.setNString(3,ad)
              val rs = pstat.executeQuery()
              //查询统计表数据
              if(rs.next()){
                //如果存在数据，那么更新
                val pstat1 = conn.prepareStatement(
                  """
                    |update user_ad_count
                    |set count = count + ?
                    |where dt = ? and userid = ? and adid = ?
                    |""".stripMargin
                )
                pstat1.setInt(1,count)
                pstat1.setString(2,day)
                pstat1.setString(3,user)
                pstat1.setString(4,ad)
                pstat1.close()

                //TODO 判断更新后的点击数量是否超过阈值，如果超过，那么将用户拉入到黑名单
                val pstat2 = conn.prepareStatement(
                  """
                    |select
                    |   *
                    |from user_ad_count
                    |where dt = ? and userid = ? and adid = ? and count >= 30
                    |""".stripMargin
                )
                pstat2.setString(1,day)
                pstat2.setString(2,user)
                pstat2.setString(3,ad)
                val rs2 = pstat2.executeQuery()
                if (rs2.next()){
                  val pstat3 = conn.prepareStatement(
                    """
                      |insert into black_list (userid) values (?)
                      |on DUPLICATE KEY
                      |UPDATE userid = ?
                      |""".stripMargin
                  )
                  pstat3.setString(1,user)
                  pstat3.setString(2,user)
                  pstat3.executeUpdate()
                  pstat3.close()
                }

                rs2.close()
                pstat2.close()
              }else {
                //如果不存在数据，那么新增
                val pstat1 = conn.prepareStatement(
                  """
                    |insert into user_ad_count (dt,userid,adid,count) values (?,?,?,?)
                    |""".stripMargin
                )
                pstat1.setString(1,day)
                pstat1.setString(2,user)
                pstat1.setString(3,ad)
                pstat1.setInt(4,count)
              }

              rs.close()
              pstat.close()
              conn.close()
            }
          }
        }
      }
    )
    ssc.start()
    ssc.awaitTermination()
  }

}

case class AdclickData(ts: String, area: String, city: String, user: String, ad: String)