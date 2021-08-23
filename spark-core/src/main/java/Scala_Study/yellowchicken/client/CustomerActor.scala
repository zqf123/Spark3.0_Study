package Scala_Study.yellowchicken.client

import Scala_Study.yellowchicken.common.{ClientMessage, ServerMessage}
import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

class CustomerActor(serverHost:String,serverPort:Int)extends Actor{

  //定义一个YelloChickenServerRef
  var serverActorRef:ActorSelection = _

  //在Actor中有一个方法PreStart方法，它会在actor运行前执行
  //在akka的开发中，通常将初始化工作，放在preStart方法
  override def preStart(): Unit = {

    println("preStart()执行")
    serverActorRef = context.actorSelection(s"akka.tcp://Server@${serverHost}:${serverPort}/user/YellowChickenServer")

    println("serverActorRef="+serverActorRef)
  }
  override def receive: Receive = {

    case "start" => println("start,客户端运行，可以咨询问题")
    case mes:String =>{
      //发送给小黄鸡客服
      serverActorRef ! ClientMessage(mes)
    }
    case ServerMessage(mes) => {
      println(s"收到小黄鸡客服(Server):$mes")
    }
  }
}

object CustomerActor extends App{
  val (clientHost,clientPort,serverHost,serverPort) = ("127.0.0.1",9990,"127.0.0.1",9999)
  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=$clientHost
       |akka.remote.netty.tcp.port=$clientPort
       |""".stripMargin
  )

  //创建ActorSystem
  val clientActorSystem = ActorSystem("client",config)

  //创建CustomerActor的实例和引用
  val customerActorRef = clientActorSystem.actorOf(Props(new CustomerActor(serverHost,serverPort)),"CustomerActor")

  //启动customerRef
  customerActorRef ! "start"

  //客户端可以发送消息给服务器
  while(true){
    println("请输出要咨询的问题")
    val mes = StdIn.readLine()
    customerActorRef ! mes
  }
}
