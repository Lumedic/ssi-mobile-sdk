package com.dxc.ssi.agent.ledger.indy.libindy

class PoolJSONParameters {
    class CreatePoolLedgerConfigJSONParameter() {
        constructor(genesisTxn: String) : this() {
            if (genesisTxn != null) this.map.put("genesis_txn", genesisTxn)
        }
        fun toJson(): String {
            TODO("Not yet implemented")
        }

    }

    class OpenPoolLedgerJSONParameter(timeout: Int?, extendedTimeout: Int?) {
        fun toJson(): String {
            TODO("Not yet implemented")
        }

    }
}