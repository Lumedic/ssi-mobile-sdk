package com.dxc.ssi.agent.didcomm.model.didexchange

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
  "connection~sig": {
    "@type": "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/signature/1.0/ed25519Sha512_single",
    "signature": "6enimqV97RRJLwq4mX7eAjr9PncuP6era7u0gIrnVqrZOSYBPeNDsUDa76gYp_6yIM0YMeNUFBrAWmtlDxGFDw==",
    "sig_data": "AAAAAGAlSud7ImRpZCI6ICJIZ3lVYWFZaDJIOUxmMnhCWnU1V1JkIiwgImRpZF9kb2MiOiB7IkBjb250ZXh0IjogImh0dHBzOi8vdzNpZC5vcmcvZGlkL3YxIiwgImlkIjogImRpZDpzb3Y6SGd5VWFhWWgySDlMZjJ4Qlp1NVdSZCIsICJwdWJsaWNLZXkiOiBbeyJpZCI6ICJkaWQ6c292OkhneVVhYVloMkg5TGYyeEJadTVXUmQjMSIsICJ0eXBlIjogIkVkMjU1MTlWZXJpZmljYXRpb25LZXkyMDE4IiwgImNvbnRyb2xsZXIiOiAiZGlkOnNvdjpIZ3lVYWFZaDJIOUxmMnhCWnU1V1JkIiwgInB1YmxpY0tleUJhc2U1OCI6ICJBNmFaTDV1U1BraERpWm40SjFtSHhydzE1eks0Y2Q4d0NZeTJ1ZFJpY2Q3aSJ9XSwgImF1dGhlbnRpY2F0aW9uIjogW3sidHlwZSI6ICJFZDI1NTE5U2lnbmF0dXJlQXV0aGVudGljYXRpb24yMDE4IiwgInB1YmxpY0tleSI6ICJkaWQ6c292OkhneVVhYVloMkg5TGYyeEJadTVXUmQjMSJ9XSwgInNlcnZpY2UiOiBbeyJpZCI6ICJkaWQ6c292OkhneVVhYVloMkg5TGYyeEJadTVXUmQ7aW5keSIsICJ0eXBlIjogIkluZHlBZ2VudCIsICJwcmlvcml0eSI6IDAsICJyZWNpcGllbnRLZXlzIjogWyJBNmFaTDV1U1BraERpWm40SjFtSHhydzE1eks0Y2Q4d0NZeTJ1ZFJpY2Q3aSJdLCAic2VydmljZUVuZHBvaW50IjogImh0dHA6Ly8xOTIuMTY4LjAuMTE3OjgwMzAifV19fQ==",
    "signer": "5vQhsiApG1LMD43L4cQjk5jyArDqoov8rtrMCeyRVj4J"
  }
* */
//TODO: think about proper data structures to use instead of String
@Serializable
data class ConnectionSignature(
    @SerialName("@type") val type: String,
    val signature: String,
    @SerialName("sig_data") val signedData: String,
    val signer: String
)