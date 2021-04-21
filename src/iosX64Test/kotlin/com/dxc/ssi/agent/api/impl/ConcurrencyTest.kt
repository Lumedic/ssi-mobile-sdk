package com.dxc.ssi.agent.api.impl

import com.dxc.ssi.agent.transport.Sleeper
import kotlinx.coroutines.*
import kotlin.test.Test

class ConcurrencyTest {

    @Test
    fun parralelExecution() {

        GlobalScope.launch {
            runBlocking {
                withContext(newSingleThreadContext("Thread 1")) {
                    runBlocking {
                        println("Starting Global scope 1")
                        Sleeper().sleep(1000)
                        println("Finishing Global scope 1")
                    }
                }
            }
        }

        GlobalScope.launch {
            withContext(newSingleThreadContext("Thread 2")) {
                runBlocking {
                    println("Starting Global scope 2")
                    Sleeper().sleep(1000)
                    println("Finishing Global scope 2")
                }
            }
        }

        Sleeper().sleep(10000)

    }

    @Test
    fun sequentialExecution() {

        GlobalScope.launch {
            println("Starting Global scope 1")
            Sleeper().sleep(1000)
            println("Finishing Global scope 1")
        }

        GlobalScope.launch {
            println("Starting Global scope 2")
            Sleeper().sleep(1000)
            println("Finishing Global scope 2")
        }

        Sleeper().sleep(10000)

    }
}