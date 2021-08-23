package com.atguigu.bigdata.spark.util

import java.sql.{Connection, PreparedStatement}
import java.util.Properties

import com.alibaba.druid.pool.DruidDataSourceFactory

object JDBCUtil {

  //初始化连接池
  var dataSource = init()

  //初始化连接池方法
  def init() = {
    val properties = new Properties()
    properties.setProperty("driverClassName","com.mysql.jdbc.Driver")
    properties.setProperty("url","jdbc:mysql://hadoop102:3306/spark-streaming?useUnicode=true&characterEncoding=UTF-8")
    properties.setProperty("username","root")
    properties.setProperty("password","123456")
    properties.setProperty("maxActive","50")
    DruidDataSourceFactory.createDataSource(properties)
  }

  //获取Mysql连接
  def getConnection = {
    dataSource.getConnection
  }

  //执行SQL语句，单条数据插入
  def executeUpdate(connection:Connection,sql:String,params:Array[Any]) = {
    var rtn = 0
    var pstmt:PreparedStatement = null
    try{
      connection.setAutoCommit(false)
      pstmt = connection.prepareStatement(sql)

      if(params!=null&&params.length>0){
        for(i <- params.indices){
          pstmt.setObject(i+1,params(i))
        }
      }
      rtn = pstmt.executeUpdate()
      connection.commit()
      pstmt.close()
    }catch {
      case e:Exception => e.printStackTrace()
    }
    rtn
  }

  def isExit(connection:Connection,sql:String,params:Array[Any]): Unit ={
    var flag = false
    var pstmt: PreparedStatement = null
    try{
      pstmt = connection.prepareStatement(sql)
      for (i <- params.indices){
        pstmt.setObject(i+1,params(i))
      }
      flag = pstmt.executeQuery().next()
      pstmt.close()
    }catch {
      case e: Exception => e.printStackTrace()
    }
    flag
  }

}
