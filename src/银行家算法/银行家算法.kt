@file:Suppress("UNCHECKED_CAST")

package 银行家算法

import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

//工作向量
val workArray by lazy { ArrayList<Int>() }
//系统拥有的各类资源总和
val allArray by lazy { ArrayList<Int>() }
//目前系统可利用资源向量
val availableArray by lazy { ArrayList<Int>() }
//最大需求矩阵
val maxArray by lazy { ArrayList<ArrayList<Int>>() }
//分配矩阵
val allocationArray by lazy { ArrayList<ArrayList<Int>>() }
//需求矩阵
val needArray by lazy { ArrayList<ArrayList<Int>>() }
//用来表示系统是否有足够的资源分配给进程
val finishArray by lazy { ArrayList<Boolean>() }
//用来保存安全序列
val arr by lazy { ArrayList<Int>() }
//进程数量
var m: Int = 0
//系统资源种类
var n: Int = 0
val input = Scanner(System.`in`)

fun main() {
    //开始初始化系统数据
    input()
    //先判断当前系统是否处于安全状态,若不处于安全状态直接结束程序
    if(!banker()){
        input.close()
        exitProcess(0)
    }
    while (true) {
        //每次进行银行家算法前需要将该List置为false，并清空安全序列List
        for(i in 0 until m){
            finishArray[i] = false
        }
        arr.clear()
        print("请输入要请求的进程序号(退出输入q):")
        val temp = input.next()
        if (temp[0] == 'q' || !temp[0].isDigit()) {
            input.close()
            exitProcess(0)
        }
        val num = temp.toInt()
        //保存当前请求进程请求的资源数
        val requestArray by lazy { ArrayList<Int>() }
        print("请输入该进程所申请的资源数:")
        requestArray.input(n)
        //判断该进程请求的资源是否超过其最大需求或当前系统剩余资源数
        if (requestArray < needArray[num] && requestArray < availableArray) {
            for (i in 0 until n) {
                availableArray[i] -= requestArray[i]
                allocationArray[num][i] += requestArray[i]
                needArray[num][i] -= requestArray[i]
            }
            workArray.input(availableArray)
            printSystemDetail()
            //若系统处于不安全状态，还原所有资源数
            if (!banker()) {
                for (i in 0 until n) {
                    availableArray[i] += requestArray[i]
                    allocationArray[num][i] -= requestArray[i]
                    needArray[num][i] += requestArray[i]
                }
                workArray.input(availableArray)
                printSystemDetail()
            }
        } else {
            println("进程申请的资源数超过其最大需求或当前系统剩余资源数")
        }
    }
}

fun printSystemDetail(){
    print("当前可用系统资源的数量为:")
    availableArray.print()
    println("当前各进程拥有的资源数量:")
    allocationArray.print()
    println("当前各进程还需要的资源数量:")
    needArray.print()
}

//自定义ArrayList<Int>比较方法
operator fun ArrayList<Int>.compareTo(arrayList: ArrayList<Int>): Int {
    for ((index, value) in this.withIndex()) {
        if (value > arrayList[index]) {
            return 1
        }
    }
    return -1
}

//银行家算法
fun banker(): Boolean {
    var index = find()
    while (index > -1) {
        arr.add(index)
        for ((i, v) in allocationArray[index].withIndex()) {
            workArray[i] += v
        }
        index = find()
    }
    return if (index == -2) {
        println("系统处于安全状态，存在一个安全序列:$arr")
        true
    } else {
        println("系统处于不安全状态，无法为进程分配所需资源!")
        false
    }
}

/*查找当前符合条件的进程
* 银行家算法.getFinishArray[index] = false
* 银行家算法.getNeedArray[index,j] <= 银行家算法.getWorkArray[j]
* */
fun find(): Int {
    f@ for ((index, value) in finishArray.withIndex()) {
        if (!value) {
            for ((i, v) in needArray[index].withIndex()) {
                if (v > workArray[i]) {
                    continue@f
                }
            }
            finishArray[index] = true
            return index
        }
    }
    for (i in finishArray) {
        if (!i) {
            return -1
        }
    }
    return -2
}

//获取数据
fun input() {
    print("请输入系统资源的种类数量:")
    n = input.nextInt()
    print("请输入每种系统资源的数量:")
    allArray.input(n)
    print("请输入进程数量:")
    m = input.nextInt()
    for (i in 0 until m) {
        finishArray.add(false)
    }
    println("请输入各进程的最大需求:")
    maxArray.input(n, m)
    println("请输入分配资源数:")
    allocationArray.input(n, m)
    println("当前进程还需要的资源数:")
    for (i in 0 until m) {
        val tempArray = ArrayList<Int>()
        for (j in 0 until n) {
            tempArray.add(maxArray[i][j] - allocationArray[i][j])
            print("${tempArray[j]} ")
        }
        needArray.add(tempArray)
        println()
    }
    //根据系统拥有的资源数与已分配给进程的资源数求得当前系统可以资源数
    for (j in 0 until n) {
        availableArray.add(allArray[j] - allocationArray.sumBy { it[j] })
    }
    println("当前可用系统资源的数量为:$availableArray")
    workArray.input(availableArray)
}

//定义ArrayList输入方法
inline fun <reified T> ArrayList<T>.input(n: Int, m: Int = 0) {
    when (T::class.java) {
        Integer::class.java -> {
            for (i in 0 until n) {
                this as ArrayList<Int>
                this.add(input.nextInt())
            }
        }
        ArrayList<Int>()::class.java -> {
            this as ArrayList<ArrayList<Int>>
            for (j in 0 until m) {
                val temp = ArrayList<Int>()
                for (i in 0 until n) {
                    temp.add(input.nextInt())
                }
                this.add(temp)
            }
        }
        else -> {
            println("该方法不支持当前类型")
        }
    }
}

//自定义ArrayList<Int>赋值
fun ArrayList<Int>.input(arrayList: ArrayList<Int>){
    this.clear()
    arrayList.forEach {
        this.add(it)
    }
}

//自定义ArrayList控制台输出
inline fun <reified T> ArrayList<T>.print() {
    when (T::class.java) {
        Integer::class.java -> {
            this.forEach {
                print("$it ")
            }
            println()
        }
        ArrayList<Int>()::class.java -> {
            this as ArrayList<ArrayList<Int>>
            this.forEach {item ->
                item.forEach{
                    print("$it ")
                }
                println()
            }
        }
        else -> {
            println("该方法不支持当前类型")
        }
    }
}