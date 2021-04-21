package com.dxc.ssi.agent.api.impl

import co.touchlab.stately.freeze
import co.touchlab.stately.isFrozen
import com.dxc.ssi.agent.model.Connection
import com.dxc.ssi.agent.model.messages.MessageEnvelop
import com.dxc.ssi.agent.transport.AppSocket
import com.dxc.ssi.agent.transport.PlatformSocketListener
import com.dxc.ssi.agent.transport.Sleeper
import com.dxc.ssi.agent.transport.WebSocketTransportImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import platform.Foundation.*
import platform.darwin.NSObject
import kotlin.test.Test

class WebsocketTest {


    class AppSocket() {
        val ws: PlatformSocket = PlatformSocket()
        val openSocketChannel = Channel<String> ()

        var currentState = com.dxc.ssi.agent.transport.AppSocket.State.CLOSED

        fun connect() {
            ws.connect(socketListener, openSocketChannel)

            val returnedVal = runBlocking {
            openSocketChannel.receive()}

            socketListener.onOpen()
        }

        private val socketListener: PlatformSocketListener = object : PlatformSocketListener {
            override val openSocketChannel =  Channel<String>()

            override fun onOpen() {
                println("Opened socket")
                println("is PlatformSocketListener frozen = ${this.isFrozen}")

                currentState = com.dxc.ssi.agent.transport.AppSocket.State.CONNECTED
            }

            override fun onFailure(t: Throwable) {

                currentState = com.dxc.ssi.agent.transport.AppSocket.State.CLOSED
                println("Socket failure: $t \n ${t.stackTraceToString()}")
            }

            override fun onMessage(msg: String) {
                println("Received message: $msg")
              //  incomingMessagesQueue.add(MessageEnvelop(msg))

            }

            override fun onClosing(code: Int, reason: String) {
                currentState = com.dxc.ssi.agent.transport.AppSocket.State.CLOSING
                println("Closing socket: code = $code, reason = $reason")
            }

            override fun onClosed(code: Int, reason: String) {
                currentState = com.dxc.ssi.agent.transport.AppSocket.State.CLOSED
                println("Closed socket: code = $code, reason = $reason")
            }
        }


    }

    class PlatformSocket() {

        fun connect(listener: PlatformSocketListener, channel: Channel<String>) {
            val url = "ws://192.168.0.117:7000/ws"
             val channelListener = listener.openSocketChannel
            val socketEndpoint = NSURL.URLWithString(url)!!

            GlobalScope.launch {

                var webSocket: NSURLSessionWebSocketTask? = null



                val urlSession = NSURLSession.sessionWithConfiguration(
                    configuration = NSURLSessionConfiguration.defaultSessionConfiguration(),
                    delegate = object : NSObject(), NSURLSessionWebSocketDelegateProtocol {
                        override fun URLSession(
                            session: NSURLSession,
                            webSocketTask: NSURLSessionWebSocketTask,
                            didOpenWithProtocol: String?
                        ) {
                            println("session opened")
                            runBlocking {
                                channel.send("session opened message to the channel")
                                //channelListener.send("session opened message to the channel")
                            }
                                //  listener.onOpen()
                        }

                        override fun URLSession(
                            session: NSURLSession,
                            webSocketTask: NSURLSessionWebSocketTask,
                            didCloseWithCode: NSURLSessionWebSocketCloseCode,
                            reason: NSData?
                        ) {
                            println(" session closed, code = ${didCloseWithCode.toInt()}, reasons =  ${reason.toString()}")
                        }
                    }.freeze(),
                    delegateQueue = NSOperationQueue.currentQueue()
                )
                webSocket = urlSession.webSocketTaskWithURL(socketEndpoint)

                Sleeper().sleep(1000)
                listenMessages(webSocket!!)
                webSocket!!.resume()
                Sleeper().sleep(1000)
                sendMessage("test",webSocket!!)
                Sleeper().sleep(1000)
                webSocket?.cancelWithCloseCode(0L, null)
                Sleeper().sleep(5000)
            }
            Sleeper().sleep(5000)
        }

        private fun listenMessages(webSocket: NSURLSessionWebSocketTask) {
            webSocket?.receiveMessageWithCompletionHandler { message, nsError ->
                when {
                    nsError != null -> {
                        println("Got error  = ${nsError.description}")
                    }
                    message != null -> {
                        println("Received message $message")
                    }
                }
                listenMessages(webSocket)
            }.freeze()
        }

        fun sendMessage(msg: String,webSocket: NSURLSessionWebSocketTask) {
            val message = NSURLSessionWebSocketMessage(msg)

            val  completionHandler: (platform.Foundation.NSError?) -> kotlin.Unit = { err: NSError? ->
                err?.let { println("send $msg error: $it") }
            }

            webSocket!!.sendMessage(message, completionHandler.freeze())
        }

    }

    interface PlatformSocketListener {
        val openSocketChannel: Channel<String>
        fun onOpen()
        fun onFailure(t: Throwable)
        fun onMessage(msg: String)
        fun onClosing(code: Int, reason: String)
        fun onClosed(code: Int, reason: String)
    }

    @Test
    fun webSocketTestRaw() {

        val appSocket = AppSocket()
        appSocket.connect()

    }



    @Test
    fun websocketTest() {

        val transport = WebSocketTransportImpl()

        transport.sendMessage(
            Connection(
                "testConnectionId",
                state = "test",
                "inv",
                true,
                listOf(),
                endpoint = "ws://192.168.0.117:7000/ws"
            ),
            MessageEnvelop("payload")
        )
    }
}