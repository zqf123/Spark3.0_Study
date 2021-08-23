package Scala_Study.yellowchicken.server

import Scala_Study.yellowchicken.common.{ClientMessage, ServerMessage}
import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class YellowChickenServer extends Actor{
  override def receive: Receive = {

    case "start" => println("start小黄鸡客服开始工作了...")
      //如果接收到ClientMessage
    case ClientMessage(mes) => {
      mes match {
        case "大数据学费" => sender() ! ServerMessage("3w5RMB")
        case "学校地址" => sender() ! ServerMessage("北京")
        case "学习什么技术" => sender() ! ServerMessage("大数据 前端 python")
        case _ => sender() ! ServerMessage("你说啥")
      }
    }
  }
}

object YellowChickenServer extends App{


  val host = "127.0.0.1" //服务端ip地址
  val port = 9999
  //创建config对象，指定协议类型，监听的ip和端口
  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=$host
       |akka..remote.netty.tcp.port=$port
       |""".stripMargin
  )

  //创建ActorSystem
  //url(统一资源定位)
  val serverActorSystem = ActorSystem("Server",config)
  //创建YellowChickenServer的actor和返回actorRef
  val yellowChickenServerRef = serverActorSystem.actorOf(Props[YellowChickenServer],"YellowChickenServer")

  yellowChickenServerRef ! "start"
}
