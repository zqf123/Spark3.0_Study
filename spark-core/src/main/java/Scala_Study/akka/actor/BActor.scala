package Scala_Study.akka.actor

import akka.actor.Actor

class BActor extends Actor{
  override def receive: Receive = {
    case "我打" => {
      println("BActor挺猛")
      Thread.sleep(1000)
      //通过sender()可以获取到发送消息的actor的ref
      sender() ! "我打"
    }
  }
}
