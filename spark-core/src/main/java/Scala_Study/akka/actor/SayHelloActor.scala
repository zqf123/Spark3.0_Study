package Scala_Study.akka.actor
import akka.actor.{Actor, ActorSystem, Props}
class SayHelloActor extends Actor{


  //1.receive方法，会被该Actor的MailBox(实现了Runnable接口)调用
  //2.当该Actor的MailBox接收到消息，就会调用receive
  //3.type Receive = PartialFunction[Any,Unit]
  override def receive: Receive = {
    case "hello" => println("收到hello，回应hello too")
    case "ok" => println("收到ok，回应ok too")
    case "exit" =>{
      println("收到exit，退出系统")
      context.stop(self) //停止actoref
      context.system.terminate() //退出actorsystem
    }
    case _ => println("匹配不到")
  }
}

object SayHelloActorDemo {
  //1.先创建一个ActorSystem,专门用来创建Actor
  private val actorFactory = ActorSystem("actorfactory")
  //2.创建一个Actor的同时，返回Actor的ActorRef
  //说明
  //(1)Props[SayHelloActor]创建了一个SayHelloActor实例，使用反射
  //(2)"sayHelloActor"给actor取名
  //(3)sayHelloActorRef:ActorRef就是Props[SayHelloActor]的ActorRef
  //(4)创建的SayHelloActor实例被ActorSystem接管
  private val sayHelloActorRef = actorFactory.actorOf(Props[SayHelloActor],"sayHelloActor")

  def main(args: Array[String]): Unit = {
    //给SayHelloActor发消息(邮箱)
    sayHelloActorRef ! "hello"
    sayHelloActorRef ! "ok"
    sayHelloActorRef ! "ok~"
    //研究异步如何退出ActorSystem
    sayHelloActorRef ! "exit"
  }
}
