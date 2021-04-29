package com.dxc.ssi.agent.ledger.indy.libindy

import com.dxc.ssi.agent.callback.CallbackData
import com.dxc.ssi.agent.callback.callbackHandler
import com.indylib.indy_set_protocol_version
import kotlinx.cinterop.staticCFunction

//TODO: unify this, define those methods as expected ones
actual class Pool {
    data class SetProtocolVersionCallbackResult(
        override val commandHandle: Int,
        override val errorCode: UInt
    ) : CallbackData

    companion object {
        suspend fun setProtocolVersion(protocolVersion: Long) {

            val commandHandle = callbackHandler.prepareCallback()

            val callback =
                staticCFunction() { commandHandle: Int, errorCode: UInt
                    ->
                    initRuntimeIfNeeded()
                    callbackHandler.setCallbackResult(
                        SetProtocolVersionCallbackResult(commandHandle, errorCode)
                    )
                }

            indy_set_protocol_version(commandHandle, protocolVersion.toULong(), callback)

            callbackHandler.waitForCallbackResult(commandHandle)

        }

        fun createPoolLedgerConfig(poolName: String, toJson: Any): Any {
            TODO("Not yet implemented")
        }

        fun openPoolLedger(poolName: String, toJson: String): Pool {
            TODO("Not yet implemented")
        }

    }
}