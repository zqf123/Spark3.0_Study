package Scala_Study

object MultiplyArray {

  def main(args: Array[String]): Unit = {
    val arr = Array.ofDim[Int](3,4)

    //遍历
    for (item<-arr){
      for (item2<-item)
        print(item2+"\t")
      println()
    }

    //指定取出
    println(arr(1)(1))

    //修改值
    arr(1)(1) = 100

    //遍历
    println("==================")
    for (item<-arr){
      for(item2<-item)
        println(item2+"\t")
      println()
    }

    //使用传统的下标的方式来进行遍历
    println("=====================")
    for(i<-0 to arr.length-1){
      for(j<-0 to arr(i).length-1)
        printf("arr[%d][%d]=%d\t",i,j,arr(i)(j))
    }
    println()
  }



}
