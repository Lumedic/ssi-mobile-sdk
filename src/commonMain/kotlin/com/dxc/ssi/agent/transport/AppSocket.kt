package com.dxc.ssi.agent.transport

import com.dxc.ssi.agent.model.messages.MessageEnvelop
import co.touchlab.stately.isFrozen
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import co.touchlab.stately.collections.IsoMutableList

//Common
//TODO: replace with CompletableDeferred?
//TODO: move to separate file
class SocketListenerLoosingAdapter(
    val socketOpenedChannel: Channel<Unit> = Channel(),
    val socketReceivedMessageChannel: Channel<String> = Channel(),
    val socketFailureChannel: Channel<Throwable> = Channel()
)

class AppSocket(url: String, incomingMessagesQueue: IsoMutableList<MessageEnvelop>) {
    private val ws = PlatformSocket(url)

    private val socketListenerLoosingAdapter = SocketListenerLoosingAdapter()


    // private val isolatedWs = IsolateState {}

    var socketError: Throwable? = null
        private set
    var currentState: State = State.CLOSED
        private set(value) {
            field = value
            stateListener?.invoke(value)
        }
    var stateListener: ((State) -> Unit)? = null
        set(value) {
            field = value
            value?.invoke(currentState)
        }


    //TODO: rework this function to be more robust and more suited for different platforms
    fun connect() {
        if (currentState != State.CLOSED) {
            throw IllegalStateException("The socket is available.")
        }
        socketError = null
        currentState = State.CONNECTING


        //TODO: refactor this to have cleaner code, introduce single listen fun combining the funs above
        //TODO: introduce liseners for other types of events
        listenForMessages()
        listenForFailures()

        ws.openSocket(socketListenerLoosingAdapter)
        println("Before running blocking")
        runBlocking {
            socketListenerLoosingAdapter.socketOpenedChannel.receive()
            socketListener.onOpen()
            println("After socketListener.onOpen")
        }

        println("After running blocking")
        //  while (currentState == State.CONNECTING) {
        //    Sleeper().sleep(100)
        //}

        if (currentState != State.CONNECTED)
            throw throw IllegalStateException("Could not be opened")

        println("Exiting AppSocket.connect")
    }

    fun disconnect() {
        if (currentState != State.CLOSED) {
            currentState = State.CLOSING
            ws.closeSocket(1000, "The user has closed the connection.")
        }
    }

    fun send(msg: String) {
        if (currentState != State.CONNECTED) throw IllegalStateException("The connection is lost.")
        println("Sending message to websocket")
        ws.sendMessage(msg)
        println("Sent message to websocket")
    }

    private fun listenForMessages() {
        println("IN listen function")
        GlobalScope.launch {
            //TODO: change to smth like while CONNECTED
        //    withContext(newSingleThreadContext("Thread 2")) {

                val receivedMessage = socketListenerLoosingAdapter.socketReceivedMessageChannel.receive()
                socketListener.onMessage(receivedMessage)

                listenForMessages()
       //     }


        }
    }


    private fun listenForFailures() {

        GlobalScope.launch {
            //TODO: change to smth like while CONNECTED
       //     withContext(newSingleThreadContext("Thread 3")) {

                val receivedThrowable = socketListenerLoosingAdapter.socketFailureChannel.receive()
                socketListener.onFailure(receivedThrowable)

                listenForFailures()
      //      }

        }

    }

    private val socketListener: PlatformSocketListener = object : PlatformSocketListener {
        override fun onOpen() {
            println("Opened socket")
            println("is PlatformSocketListener frozen = ${this.isFrozen}")

            currentState = State.CONNECTED
        }

        override fun onFailure(t: Throwable) {
            socketError = t
            currentState = State.CLOSED
            println("Socket failure: $t \n ${t.stackTraceToString()}")
        }

        override fun onMessage(msg: String) {
            println("Received message: $msg")
            incomingMessagesQueue.add(MessageEnvelop(msg))

        }

        override fun onClosing(code: Int, reason: String) {
            currentState = State.CLOSING
            println("Closing socket: code = $code, reason = $reason")
        }

        override fun onClosed(code: Int, reason: String) {
            currentState = State.CLOSED
            println("Closed socket: code = $code, reason = $reason")
        }
    }

    enum class State {
        CONNECTING,
        CONNECTED,
        CLOSING,
        CLOSED
    }
}