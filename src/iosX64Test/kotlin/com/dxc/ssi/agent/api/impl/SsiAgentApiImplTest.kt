package com.dxc.ssi.agent.api.impl

import com.dxc.ssi.agent.api.callbacks.CallbackResult
import com.dxc.ssi.agent.api.callbacks.didexchange.ConnectionInitiatorController
import com.dxc.ssi.agent.didcomm.model.didexchange.ConnectionRequest
import com.dxc.ssi.agent.didcomm.model.didexchange.ConnectionResponse
import com.dxc.ssi.agent.didcomm.model.didexchange.Invitation
import com.dxc.ssi.agent.model.Connection
import com.dxc.ssi.agent.transport.Sleeper
import com.dxc.ssi.agent.utils.ToBeReworked
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.native.internal.test.testLauncherEntryPoint

import kotlin.test.Test
import kotlin.test.Ignore

//import Starscream

//import JFRWebSocket
//import jetfier.JFRWebSocket
//import cocoapods.jetfire.JFRWebSocket
//import cocoapods.Starscream.FoundationTransport
//import cocoapods.jetfire.JFRWebSocketDelegateProtocol
//import kotlinx.cinterop.COpaquePointer
//import kotlinx.cinterop.ObjCClass
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch/
//import objcnames.classes.Protocol
//import platform.Foundation.NSURL
//import platform.darwin.NSObject
//import platform.darwin.NSUInteger

class SsiAgentApiImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    //@Ignore
    fun basicTest() {


        // ToBeReworked.enableIndyLog()


            val ssiAgentApi = SsiAgentBuilderImpl()
                .withConnectionInitiatorController(ConnectionInitiatorControllerImpl())
                .build()

            ssiAgentApi.init()

            val invitationUrl =
                "ws://192.168.0.117:7000/ws?c_i=eyJsYWJlbCI6Iklzc3VlciIsImltYWdlVXJsIjpudWxsLCJzZXJ2aWNlRW5kcG9pbnQiOiJ3czovLzE5Mi4xNjguMC4xMTc6NzAwMC93cyIsInJvdXRpbmdLZXlzIjpbIkZ0YVB4ZTlCc1gyWld5YTdFOGd0MVRaSDN0WjloblptU3ozNEdrbVM3ZGI2Il0sInJlY2lwaWVudEtleXMiOlsiQ3JzbTJaNWRaNkE2d3NHSmFtYzZ1VnJNWkxlVm4xdGlhNEIxYlFqU2Y2V0siXSwiQGlkIjoiYjE2MmNmMzgtMTI2Zi00MzQ3LWE3NWYtMTBlZDFkNmY0YTkxIiwiQHR5cGUiOiJkaWQ6c292OkJ6Q2JzTlloTXJqSGlxWkRUVUFTSGc7c3BlYy9jb25uZWN0aW9ucy8xLjAvaW52aXRhdGlvbiJ9ª"
            // "ws://192.168.0.104:9000/ws?c_i=eyJsYWJlbCI6IlZlcmlmaWVyIiwiaW1hZ2VVcmwiOm51bGwsInNlcnZpY2VFbmRwb2ludCI6IndzOi8vMTkyLjE2OC4wLjEwNDo5MDAwL3dzIiwicm91dGluZ0tleXMiOlsiR0ZXNkJGalFNc1FXTFFHMzFGNjh2Uzk5TVg5UUtSV1pFZGZQNmJRaHEzNlgiXSwicmVjaXBpZW50S2V5cyI6WyJIY0ZFVllkc3JwaVJpaXVMcXlrTTNWSFRaUlBXSmJwTWt3RlRpWVhMTndROSJdLCJAaWQiOiI0NWY5ZDAyOC1mMjg0LTRkN2MtYTYwYy0yODkwNWVjOTk4MmMiLCJAdHlwZSI6ImRpZDpzb3Y6QnpDYnNOWWhNcmpIaXFaRFRVQVNIZztzcGVjL2Nvbm5lY3Rpb25zLzEuMC9pbnZpdGF0aW9uIn0="



            //launch(myThread, CoroutineStart.DEFAULT) {
            //    println("Started myThread")
            // }
//TODO: current problem is that by default kotlin uses one background thread for GlobalScope: https://github.com/Kotlin/kotlinx.coroutines/blob/native-mt/kotlin-native-sharing.md
            //TODO: either I need to use another thread or have non blocking sleep
            //  println("Started test thread")
            //   withContext(myThread) {

        GlobalScope.launch {
            println("launching connect from myThread")
            ssiAgentApi.connect(invitationUrl)

        }

        println("before connect from GlobalScope")
/*
            GlobalScope.launch {
                println("launching connect from GlobalScope")
            ssiAgentApi.connect(invitationUrl)
                return@launch
             }
*/


        println("after connect from GlobalScope")

        Sleeper().sleep(100000)

    }

    class ConnectionInitiatorControllerImpl : ConnectionInitiatorController {
        override fun onInvitationReceived(
            connection: Connection,
            endpoint: String,
            invitation: Invitation
        ): CallbackResult {
            return CallbackResult(canProceedFurther = true)
        }

        override fun onRequestSent(connection: Connection, request: ConnectionRequest): CallbackResult {
            println("Request sent hook called : $connection, $request")
            return CallbackResult(true)
        }

        override fun onResponseReceived(connection: Connection, response: ConnectionResponse): CallbackResult {
            println("Response received hook called : $connection, $response")
            return CallbackResult(true)
        }

        override fun onCompleted(connection: Connection): CallbackResult {
            println("Connection completed : $connection")
            return CallbackResult(true)
        }

    }
}