package 内存管理

import java.util.*
import kotlin.collections.ArrayList

const val DEFAULT_MEM_START = 0
const val DEFAULT_MEM_SIZE = 1024
var memSize = DEFAULT_MEM_SIZE //内存大小
var maAlgorithm = Algorithm.MA_FF //当前分配算法
var pid = 0 //初始化pid
var flag = true
val freeBlockArray by lazy { ArrayList<FreeBlockType>() }
val allocatedBlockArray by lazy { ArrayList<AllocatedBlock>() }
const val MIN_SLICE = 10
val input = Scanner(System.`in`)

fun main() {
    pid = 0
    freeBlockArray.add(FreeBlockType(memSize, DEFAULT_MEM_START))
    var f = true
    while (f) {
        displayMenu()
        when (input.nextInt()) {
            1 -> {
                setMemSize()
            }
            2 -> {
                setAlgorithm()
                flag = false
            }
            3 -> {
                newProcess()
                flag = false
            }
            4 -> {
                killProcess()
                flag = false
            }
            5 -> {
                displayMemUsage()
                flag = false
            }
            6 -> {
                re()
            }
            0 -> {
                f = false
            }
            else -> {

            }
        }
    }
}

fun re(){
    freeBlockArray.clear()
    memSize = DEFAULT_MEM_SIZE
    freeBlockArray.add(FreeBlockType(memSize, DEFAULT_MEM_START))
    allocatedBlockArray.clear()
}

fun displayMenu() {
    println()
    if (flag) {
        println("1 - 设置总内存大小 (默认大小为[$memSize])")
    } else {
        println("1 - 内存大小为[${freeBlockArray.sumBy { it.size } + allocatedBlockArray.sumBy { it.size }}]")
    }
    println("2 - 选择空间分配算法(${maAlgorithm})")
    println("3 - 创建新进程")
    println("4 - 删除进程")
    println("5 - 展示内存使用情况")
    println("6 - 重置内存")
    println("0 - 退出")
}

fun setMemSize() {
    if (!flag) {
        println("无法设置内存大小!!")
        return
    }
    print("总内存大小为:")
    val size: Int = input.nextInt()
    if (size > 0) {
        memSize = size
        freeBlockArray[0].size = memSize
    }
    flag = false
}

fun freeMem(ab: AllocatedBlock): Int {
    val algorithm = maAlgorithm
    freeBlockArray.add(FreeBlockType(ab.size, ab.startAddr))
    freeBlockArray.rearrang(Algorithm.MA_FF)
    mergeMem()
    freeBlockArray.rearrang(algorithm)
    return 1
}

fun mergeMem() {
    var flag = true
    w@ while (flag) {
        for (i in 0 until freeBlockArray.size - 1) {
            if (freeBlockArray[i].startAddr + freeBlockArray[i].size == freeBlockArray[i + 1].startAddr) {
                freeBlockArray[i].size += freeBlockArray[i + 1].size
                freeBlockArray.removeAt(i + 1)
                continue@w
            }
        }
        flag = false
    }
}

fun allocateMem(ab: AllocatedBlock): Boolean {
    val requestSize = ab.size
    for (v in freeBlockArray) {
        if (v.size >= requestSize) {
            if (v.size - requestSize >= MIN_SLICE) {
                memSize -= requestSize
                v.size -= requestSize
                ab.startAddr = v.startAddr
                v.startAddr = ab.startAddr + requestSize
            } else if (v.size - requestSize in 1 until MIN_SLICE) {
                memSize -= v.size
                ab.startAddr = v.startAddr
                v.startAddr += v.size
            } else {
                mergeMem()
                break
            }
            freeBlockArray.rearrang(maAlgorithm)
            return true
        }
    }
    return false
}

fun newProcess() {
    print("请输入进程内存大小:")
    val ab = AllocatedBlock(++pid, input.nextInt(), -1, "PROCESS-[$pid]")
    if (ab.size > 0 && allocateMem(ab)) {
        allocatedBlockArray.add(ab)
    } else {
        println("分配内存失败!!")
    }
}

fun dispose(freeAb: AllocatedBlock) {
    val temp = allocatedBlockArray.filterNot { it.startAddr == freeAb.startAddr }
    allocatedBlockArray.clear()
    allocatedBlockArray.addAll(temp)
}

fun displayMemUsage() {
    println("-----------------空闲内存---------------------")
    println("\t开始地址\t\t大小")
    val temp = ArrayList<FreeBlockType>(freeBlockArray)
    temp.rearrang(Algorithm.MA_FF)
    temp.forEach {
        println("\t${it.startAddr}\t\t\t${it.size}")
    }
    println("----------------使用中的内存-------------------")
    println("\t进程号\t进程名称\t\t\t开始地址\t\t大小")
    allocatedBlockArray.forEach {
        println("\t${it.pid}\t\t${it.processName}\t\t${it.startAddr}\t\t\t${it.size}")
    }
    print("----------------------------------------------")
}

fun findProcess(pid: Int): AllocatedBlock? {
    allocatedBlockArray.forEach {
        if (it.pid == pid) {
            return it
        }
    }
    println("当前输入的进程号有误")
    return null
}

fun killProcess() {
    print("请输入要关闭进程的进程号:")
    val ab = findProcess(input.nextInt())
    if (ab != null) {
        freeMem(ab)
        dispose(ab)
    }
}

fun setAlgorithm() {
    println("\t1 - First Fit")
    println("\t2 - Best Fit")
    println("\t3 - Worst Fit")
    val type = input.nextInt()
    if (type in 1..3) {
        maAlgorithm = Algorithm.values()[type - 1]
        freeBlockArray.rearrang(maAlgorithm)
    }
}

fun ArrayList<FreeBlockType>.rearrang(type: Algorithm) {
    when (type) {
        Algorithm.MA_FF -> {
            this.sortBy { it.startAddr }
        }
        Algorithm.MA_BF -> {
            this.sortBy { it.size }
        }
        Algorithm.MA_WF -> {
            this.sortByDescending { it.size }
        }
    }
}

data class AllocatedBlock(
    var pid: Int,
    var size: Int,
    var startAddr: Int,
    var processName: String
)

data class FreeBlockType(
    var size: Int,
    var startAddr: Int
)

enum class Algorithm {
    MA_FF,
    MA_BF,
    MA_WF
}