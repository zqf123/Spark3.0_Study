package Scala_Study.yellowchicken.common

//使用样例类来构建协议
//客户端发送服务器协议(序列化的对象)
case class ClientMessage(mes:String)

//服务端发送客户端的协议(样例类对象)
case class ServerMessage(mes:String)