package com.dxc.ssi.agent.ledger.indy.libindy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class PoolJSONParameters {
    @Serializable
    data class CreatePoolLedgerConfigJSONParameter(
        @SerialName("genesis_txn") val genesisTxn: String)
}