package Scala_Study.akka.actor

import akka.actor.{Actor, ActorRef}

class AActor(actorRef: ActorRef)extends Actor{

  val bActorRef = actorRef
  override def receive: Receive = {
    case "start" => {
      println("AActor出招了，start ok")
      self ! "我打" //发给自己
    }
    case "我打" => {
      //给BActor 发出消息
      //这里需要持有BActor的引用(BActorRef)
      println("AActor厉害")
      Thread.sleep(1000)
      bActorRef ! "我打" //给BActor发出消息
    }
  }
}
