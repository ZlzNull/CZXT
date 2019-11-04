import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

infix fun String.拼接(a:String):String = this + a

fun main(){
//    println("ab" 拼接 "c")
//    val path = Paths.get("src/Test.java")
//    println(path)
////    val file = File("./")
//    var stream = Files.newInputStream(path)
//    stream.buffered(1024).reader(Charsets.UTF_8).use {
//            reader-> println("current:${reader.readText()}");//一次读完
//    }

    println(Date().time)
    Thread.sleep(1000)
    println(Date().time)
}