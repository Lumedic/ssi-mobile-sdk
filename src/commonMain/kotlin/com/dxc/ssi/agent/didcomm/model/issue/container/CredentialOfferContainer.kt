package com.dxc.ssi.agent.didcomm.model.issue.container

import com.dxc.ssi.agent.didcomm.model.common.Attach
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
* {
  "comment": null,
  "credential_preview": {
    "attributes": [
      {
        "name": "name",
        "mime-type": "application/json",
        "value": "Alice Smith"
      },
      {
        "name": "age",
        "mime-type": "application/json",
        "value": "18"
      },
      {
        "name": "vaccination",
        "mime-type": "application/json",
        "value": "yes"
      }
    ],
    "@id": null,
    "@type": "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/issue-credential/1.0/credential-preview"
  },
  "offers~attach": [
    {
      "@id": "libindy-cred-offer-0",
      "mime-type": "application/json",
      "data": {
        "base64": "eyJzY2hlbWFfaWQiOiJBZDZmYWh3SEZjNlJ5NDFZYnFYUjJXOjI6dmFjY2luYXRpb24tc2NoZW1hOjEuMCIsImNyZWRfZGVmX2lkIjoiQWQ2ZmFod0hGYzZSeTQxWWJxWFIyVzozOkNMOjEyOmRlZmF1bHQiLCJrZXlfY29ycmVjdG5lc3NfcHJvb2YiOnsiYyI6IjIwNjA4MTkzNzcxOTY3NDA2OTU0Mzk4MzM4MjU0Njc5NjMzNjk0MjcwNDkyNzA1OTQyNTE5MDI5NDE1NjQ5ODgxMjIyMzMzNTI3NzkiLCJ4el9jYXAiOiIzNzI0ODUyMDQ5OTUxNjc4NDQ2MTkwNDAzNzQ2MzQyNTYyMzg1NTYxMTU3ODQ0MDI5MTg1Nzg3MTcxNjA0NzE1MTI5NzA0MzQyMDAxNTMzNDk1OTkxNjkwMjc1MTY0MTU2NzEzMTg0MTM5MDgxNzgyNjQwNDE4Nzk2ODY4MjgzNjQzNDY1MTI4NjE1NjY3NDA2NTA4NzI1NDQwMzY0NjgyMzE2ODk3MTUwMzQ0NzY4MDM4MDcwMDk0Njg1NjkxMzE2ODIzODMwNTE3MzEwNDkzMjY0ODA1NjkyNjI0NjQyMTI3NjE3Nzk3MTQ3NDgyNjk0NDcxMjIyMzg1NzM3Nzc2MTM4Njg5ODcyMzQ4NDYyMzM2MDU1ODE4NTg2MjI1MDY5NzA5NzIxMTA3OTcyMTg4MzQzNjAxNTQ1Mzk1OTcyMzgwMDU2NzI3ODU1MzcyNTMzODE4Njg2NTg4NzczNzg4ODgyMTM3MDkyODAzNDEzNzQwOTUyNTMxMjgwMTgzMjkzMTM5NDk4MDcwODQ4MTIzNzMwOTUxMzA1NjY4MjA1OTI1NDg0NzgyNDU2NTA2NzQzNjc4MDcxMDc2NzUyNzExMzI4MzI1MDM5OTA2Nzk1MTQ2NTczODk1MjY2ODQ5NTU0MzMxOTY5Njg5Mjc1Mjk4NjEzODYzMTAzNDY4MjM5NjUyODA5NjY5MjcxOTU3NjA4NjAxNjEwNjA0ODAzNDA4ODc4NjU0MTIzMjAzNjk1NTMxNzYzMTczNDAyMzcwNjE2MzQ0MDM5NDA2OTY0Njg4Mjg0ODYzNTE4MzM4NDQzMjU5MTkxNjk2MzA3NDc2NTk3MDkxNTM2MTEyOTY0MjcwMzkyNzUyODE2MDEwMTE1NzM4NjE0NDMyMDQ5MDM2MzQwODY0NTQ4NTczMzE0ODI1MzA0OSIsInhyX2NhcCI6W1sibWFzdGVyX3NlY3JldCIsIjM4NTk5NTc0NzMzNjIzMzU5MTUxMDA0MzMyOTgxMDI2NzQ1MzE1MTQ0NzIxOTE1NjQ2NDMxNDM4OTY3MDU1MTc4NDE2MTIyOTMxNjE2NDA3MjkwOTk5NzEzMTk4NzA5ODQyNzEzMzUzMDczODk1NDk4NDE4MDczMDQ3OTk1MDI3NDU5Njk1NjI2MDExNjMzMDI2OTA2MDY2ODgyNjY3OTg2NzgxNzk3MzMwNDcwNDk5Mzc3NDQ3MjAwMDA3ODM5Njg1NTgwOTM3MTQ5NTMxOTMzOTUxMjgxNzIxODM4OTgwMTUyNjUyNTE5MTAwMDE4Nzg1OTYwMDA3ODgyMjYzMzgzMTk2NjAwMTg0NjYzNDQ0MDgzNTc5MTY0NDMzMzI1NTIyMTE1MTgzNTgwOTg2NzMyMjkwNTkxMTAwNzc5MzQyNzQwMjg2MDY2MzMwOTExMDk0NDE1NjAyNjQ4NjExOTYzMjE3MDc4Mzk2ODYwODM0OTUxMTk2MjI3MDk0ODc3Mjk3MDQ1MzAyOTU1NTI5Nzg1OTE0NzU0NTU2MTQxODUxODY1ODgwNDAxMDA4ODkxNDk3NTM4OTY4NTA2ODYxMzg2NDI1NjczMDYzOTQ0MzkxNjc1NTU3MDc3Nzc4Mjc1NTE3MDc1MzEzMzM1ODQ5ODYzMTk0NzQxNjc5OTcwNTA5ODkwMjE3MTY4Nzg0MTAxMDU3NDY5MjcxMzQ3OTYxMDU4NDk3MzUwODc2MzY5NTQ4ODYzNjU5MjAwMTMwMjUxMDU3NzEyNDA1NzM1OTg4OTAxNzA0ODYxODczMzkxMTYxMDE0Njk0ODYzNzU4Mjg4MzIzMzI0MDIxMzkxMzk3NTc2MzI3MzQxMTk3OTIyMzY0NDM4MDc5MTkxODY5MDY0ODY5NTk2MzYzMTgwNjcyMDcwNDY1Il0sWyJhZ2UiLCIyMTU0MzkwNTQ5NTc5MjAxMzA4NzIxNTMxMzYwMDI5ODI2OTk4MzY3MjY3NTA2OTMwNTU2NDY4NjkxODQ4NjEzMjMyMTc0NDE5MTc2MjQxOTY4ODQzNTk1MjE0ODYwMzM2NTA1OTA1NDA1NTc4NzYxNDM1ODU2MDgzOTQ2MjQxNDA4MDEyNjczODY5MzEyNzcyNzM5Mjk2ODkzMTM4MjM1MDI4MTQ3NTEyOTMzNDU0NjkzMjcwMzQzMDY1ODA1MDAyODIyODA0MDY3ODg3NjgzMTQzMjExNDcwNjIwOTQ5Mzg4MTE3ODcyNzY1NDg4MDM3OTI5NjUwNzcyMjY5Nzc4NTY3NzE3ODc3NDIxMzkwNjk4MDM4MjAyMjEwMzE3OTIzMjI4OTE3NDE5NzAzNDQ1NDgzMjU3Njc0NDM2MjQ0Mjg1NDc1MTY3MjkwNDI2MjE1NDkxNTAxMjk1ODIxNTU0NjU5NzI0NzkzMjI5NzczNDUwODYyODQ1Mzg1NTk1NDg1ODA0NDc3NTAyMzc0MjQ5NTI2NTUzNjEwMTgxNzcxNzg3MjAzNTUxNDM1ODgyMDkxMDE0NzI0NzU1MTI5MzkyMTM4MTIwNTI5OTk1NTIzNDA3NzAyOTYwMjc1OTM5MjU2MzUxNTQ1Njg2OTQzMzU2Njg1Nzc3MDU0NjEzODc1NzE3NjMwMjYxNjI5OTEwNzY1MzcxNDk5OTA4OTgzMjU3NjYxNzMzNDczNDU0NjI3MTI1NDE2NzkyMTcxNDY2MDA2ODIwNjMxMzMyMTk2NzI1MTMxNjcxNzUwNTExMTk0OTU2OTcxMzI3NDQ1NjY5MjM1MjcyMTkzMDM5MTIyMDc5ODY0OTQ0ODU0MzA3ODAwMzI0NDM1MDE2NDY0Mzg0NTU0MjU2MjI3MTAwODU0MzcxNjQyNiJdLFsibmFtZSIsIjI0NjcxODAxMDM4MTQ1NzcwMzY4MDcwNTc5NzE5MTMzNjE5MDQzODgzNTIwMTEyNjAxMDc4MjQzODA2MTYyMTUxNjAyOTQwMzM2NTU4NTI2OTY5ODY0MTE1MDk5MjA1OTI0ODIzOTQ1NzQ1NjUwMDAwNTE4NjU5MDIzMjQ5NDA3MjM0MzEwMzkzMDE4MTM0Njg2NTEwODUzMzczMTM0OTU1Mjc5MTEwMTA5NDUzNDc4MTE3ODUwNDg3NTYyNDkzODM3ODI2ODg1MzA3MTMyODkzMjQzNTA5NTE5NTU4MTUxMTQ2MTk2ODc1MDg4NTI0MzU4Mzk1MzMwNzkyNjY3Mjc0MTgxMDMwMzc4MzM0MDAwNzYyMDY5MTg0NzIyNDU5NTExODk5MzE3MzMxMzc5MDg5ODMzODM1ODg3NDI3NjM4MTA0MTk5NjY2NDIzNzQ0NjgzODQzNDQyOTY3MjEzMTA2NTUzMDQ4ODk2NDA1NjkyMzI3ODA1ODI0ODc1NTk5Mjk1MzkxMDM0OTUzNTMxNDM0NTE3NTcwNDAyNjcxMTUxMTIwOTc2NDYwNDcwMjk2NjQwNDE3MTM5NDY1MDA0OTA0MjY0NzQ4NjY5MTgwMDcyNjI0MjMwNjMzMzUxMjU3Mjc1Nzk2MTY1MDczOTMwNTc3NjgzMTcwMDUwODUwNDI2MTU0ODI2Njg3MjE2ODA5MTE5NjE0MzM5NzQ4Njk0ODYwNzQ1NzE2NzUzMjcxMDcyMzU3ODg3NDUxNjYxMDkyOTI1NTU5MjE3NDcyNDEyODk5NjMzODM0ODc3MTcwNTQ4MjQ1NzA0NjgzODM1Mjc5MjM1MDE4ODQ4NDc5OTQxNTgxODU5MDkxMDY1NTAzOTQ1MTMxODk1NjI2MDkzMDA5MTA4NDQyMzI2ODY0NTE2NzA3MDI1Il0sWyJ2YWNjaW5hdGlvbiIsIjIyMDcyMjQ3OTM5MDM1MTM3MDY2NjQ0MDYzOTk3NDY5MjQyMzA1MTMzNjk4MzUwNjc3NjAzMzc5OTQ2MTE0MDQ5MDA1NTAxMDgyMTMyNzg0Mjk0Mjc1MDM2NDkxMTc4ODM2NTIxNDgyNjE1NDc1MzM4ODIzMzIzMjU5ODkzMDQ4MjAwNDIwOTkyODY0NzUxODI5NTg3NzQ2MzYzMjQ0NDc5MTg5NjQxNDQ1Mjc4NTI2MTU3MDA5OTUwNDA2NTkxNjA5OTE5NzM4NDE1MDExNzA1MzkyNjA4NjEzNTYzODg2OTg2OTYyNDEzMzcwMDA1NDUxMDUwNDc1MjgyMTUwNzA2NTczNTU1MTE5NjMwMTcxMjI5NjU2MDU5NDc2MzA4MzA4MjY1MjI5MDQxNDY4MTc0NDg4NzM3ODkxNjQwOTU0ODYxNjE1OTc3Mzk5MzYyMjc2NDIyMjc0Mzg3MDc4MzQ5MzY4MTk0MTA5NTg3MDY4NjY4OTM0OTUyNzY2NjkwODA3NTIzMDY1MjAyMzEzNTUxOTM4NzAwNDM0OTgxOTU2NTc3MTI1OTkwMTMzODgxNTkyMDExMDc1MzI0NzM3MTM5ODU3MjEyMjcxNTI4NjQ4NDgzODM1NTAzODEzMjExNzU1NjA3ODAyNjQwODQzODg1NzgzODM4NTYxNjIyNzcwMzU2NDkxMjg4ODQ5NDAyNDI3NjE2OTcxOTg1MjEzMjIwMjM0Nzk0ODM0OTk0MDgzMDgwNDI3NzkzMTY1NjM4Mzk5MTQzODgxNTM0OTMxMDM3MDc1OTY5Nzc5NjQ4ODM5Mzk1NzczMDU5MjE1ODY3NDc4MTEyNDAyNDUzNjMwNjQyNzA3Mjk4OTI5MjA5NjM3NDMwMDM0NjY2MDE4MDc0Mjk0ODc5NzMzNTUxNDkwMTUwMDE5Il1dfSwibm9uY2UiOiIxMTc4NjYyNjM5MTExNDQyOTM1NTI5MjEyIn0="
      }
    }
  ],
  "@id": "2c66020a-9298-49dc-83e6-120b7c9865de",
  "@type": "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/issue-credential/1.0/offer-credential",
  "~service": {
    "recipientKeys": [
      "6FBZvvzK3VQoJBNnk1C9gVtKVMLUbCMDUoypmrpQeBFB"
    ],
    "routingKeys": [
      "DJXkE6crubt2Np95ExaAxsTGyDQqL1YyaqfKVB4r61mG"
    ],
    "serviceEndpoint": "ws://11.0.1.11:7000/ws"
  }
}=$yږǂyrب    bjZ,wi){^A: rci1h lLlBc&|A⾵

* */

//TODO: create tests for this class
//TODO: think about some model types instead of just strings
@Serializable
data class CredentialOfferContainer(
    //TODO: set default value and validate it
    @SerialName("@type") val type: String,
    @SerialName("@id") val id: String,
    @SerialName("credential_preview") val credentialPreview: CredentialPreview,
    @SerialName("offers~attach") val offersAttach: List<Attach>,
    //@SerialName("~service") val service: List<Service>? = null,
    val comment: String?
)