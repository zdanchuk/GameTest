package org.example
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

//import kotlin.concurrent.schedule


suspend fun testPrint(i: Int): Unit {
    withContext(Dispatchers.Default) {
        val a = (Math.random() * 1000).toLong()
        delay(a)
        println("a $i $a")
        delay(a)
        println("b $i $a")
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val actionList : MutableList<Deferred<Unit>> = ArrayList()
    for (i in 1..10) {
        actionList.addLast(GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT) { testPrint(i)})
    }
    runBlocking {

        launch { // launch a new coroutine and continue
            delay(10L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay
        }
        println("Hello") // main coroutine continues while a previous one is delayed
//        a.await() // без этого зависает на этапе выполнения
        actionList.awaitAll()
    }

    runBlocking {
        println("c")

//        Timer().schedule(delay = 2500) {
//            println("Planning to Cancel schedule with printing \"!!!\"")
//        }

        var count = 5
        val timer = Timer()
        val timerTask : Any

        timerTask = object : TimerTask() {
            override fun run() {
                println(" !!!")
                count--
                if (count == 0) {
                    println(this)
                    println(this.scheduledExecutionTime())
                    this.cancel()
                    println(" !!!!!!! canceling")
                }
            }
        }

        timer.schedule(timerTask, 100, 1000)
        println("d")
        println(timer)
        delay(3000)
        timer.also {
            println("Canceling")
            timerTask.cancel()
        }.cancel()
    }

}