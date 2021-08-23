package Scala_Study.akka.actor

import akka.actor.{ActorSystem, Props}

object ActorGame extends App {

  //创建ActorSystem
  val actorfactory = ActorSystem("actorfactory")
  //先创建BActor引用/代理
  val bActorRef = actorfactory.actorOf(Props[BActor],"bActor")
  //创建AActor的引用
  val aActorRef = actorfactory.actorOf(Props(new AActor(bActorRef)),"aActor")

  aActorRef ! "start"

}
