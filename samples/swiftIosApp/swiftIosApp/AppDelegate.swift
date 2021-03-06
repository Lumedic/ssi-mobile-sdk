//
//  AppDelegate.swift
//  swiftApp
//
//  Created by Krzysztof on 25/04/2021.
//

import UIKit
import ssi_agent


@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window : UIWindow?
    let myWalletName = "newWalletName5"
    let myWalletPassword = "newWalletPassword"
    let myDid = "4PCVFCeZbKXyvgjCedbXDx"

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
    
        let cic = ConnectionInitiatorControllerImpl()
        let crc = CredentialReceiverControllerImpl()
        let cpc = CredPresenterControllerImpl()
        

        EnvironmentUtils().doInitEnvironment(environment: EnvironmentImpl())

        let walletManager = IndyWalletManager.Companion()


        if (!walletManager.isWalletExistsAndOpenable(walletName: myWalletName, walletPassword: myWalletPassword)) {
            print("Recreating wallet")
            walletManager.createWallet(walletName: myWalletName, walletPassword: myWalletPassword, walletCreationStrategy: WalletCreationStrategy.truncateandcreate)}


        if (!walletManager.isDidExistsInWallet(did: myDid, walletName: myWalletName, walletPassword: myWalletPassword)) {
            print("Recreating did")
            let didResult: CreateAndStoreMyDidResult = walletManager.createDid(
                didConfig: DidConfig.init(did: myDid, seed: nil, cryptoType: nil, cid: nil),
                walletName : myWalletName, walletPassword:myWalletPassword)

            print("Got generated didResult: did = \(didResult.getDid()) , verkey = \(didResult.getVerkey())")
            //Store did somewhere in your application to use it afterwards
        }

        let walletHolder = IndyWalletHolder(
            walletName : myWalletName,
            walletPassword :myWalletPassword,
            didConfig : DidConfig.init(did: myDid, seed: nil, cryptoType: nil, cid: nil)
        )

        let indyWalletConnector = IndyWalletConnector().build(walletHolder: walletHolder)


        let indyLedgerConnectorConfiguration = IndyLedgerConnectorConfiguration(
            genesisFilePath: "./docker_pool_transactions_genesis.txt",
            ipAddress: "20.36.7.68",
            genesisMode: IndyLedgerConnectorConfiguration.GenesisMode.ip,
            generatedGenesysFileName: "genesis.txn",
            retryTimes: 5,
            retryDelayMs: 5000)
        
        let ssiAgentApi = SsiAgentBuilderImpl(walletConnector: indyWalletConnector)
                .withConnectionInitiatorController(connectionInitiatorController: cic)
                .withCredReceiverController(credReceiverController: crc)
                .withCredPresenterController(credPresenterController: cpc)
                .withLedgerConnector(ledgerConnector: IndyLedgerConnector(indyLedgerConnectorConfiguration: indyLedgerConnectorConfiguration))
                .build()
        
               ssiAgentApi.doInit()


       let connection = ssiAgentApi.connect(url: "wss://lce-agent-dev.lumedic.io/ws?c_i=eyJsYWJlbCI6IkNsb3VkIEFnZW50IiwiaW1hZ2VVcmwiOm51bGwsInNlcnZpY2VFbmRwb2ludCI6IndzczovL2xjZS1hZ2VudC1kZXYubHVtZWRpYy5pby93cyIsInJvdXRpbmdLZXlzIjpbIkhhcVVEM1Y0OEx5eGJmWWJxWE5XZ0hVdTdVS0paRmNvNmJyMjJDSEZ3dTZIIl0sInJlY2lwaWVudEtleXMiOlsiSDd4WnNoZjZhNGFEcGtNV3pZNUdDNkR2NjN1NHhLeXZWbWVpeGFVUEhkZHMiXSwiQGlkIjoiYzIwOTY3Y2QtZmIzYi00OTI2LWFlZDctZGQ5Mjc5ZTU0ZGZiIiwiQHR5cGUiOiJkaWQ6c292OkJ6Q2JzTlloTXJqSGlxWkRUVUFTSGc7c3BlYy9jb25uZWN0aW9ucy8xLjAvaW52aXRhdGlvbiJ9", keepConnectionAlive: true)

      
        sleep(300)
        ssiAgentApi.abandonConnection(connection: connection, force: true, notifyPeerBeforeAbandoning : false)
        ssiAgentApi.shutdown(force: true)
        // Override point for customization after application launch.
    
                    if #available(iOS 13, *) {
                        // do only pure app launch stuff, not interface stuff
                    } else {
                        self.window = UIWindow()
                        let vc = ViewController()
                        self.window!.rootViewController = vc
                        self.window!.makeKeyAndVisible()
                        self.window!.backgroundColor = .red
                    }
                    return true
                        
    }
     
}

class ConnectionInitiatorControllerImpl: ConnectionInitiatorController
{
    func onCompleted(connection: PeerConnection) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onInvitationReceived(connection: PeerConnection, invitation: Invitation) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
   
    
    func onRequestSent(connection: PeerConnection, request: ConnectionRequest) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onResponseReceived(connection: PeerConnection, response: ConnectionResponse) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onAbandoned(connection: PeerConnection, problemReport: ProblemReport?) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
        
    }
    
}


class CredentialReceiverControllerImpl: CredReceiverController {
    func onProblemReport(connection: PeerConnection, problemReport: ProblemReport) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onCredentialReceived(connection: PeerConnection, credentialContainer: CredentialContainer) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onDone(connection: PeerConnection, credentialContainer: CredentialContainer) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onOfferReceived(connection: PeerConnection, credentialOfferContainer: CredentialOfferContainer) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onRequestSent(connection: PeerConnection, credentialRequestContainer: CredentialRequestContainer) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
}


class CredPresenterControllerImpl: CredPresenterController {
    func onProblemReportGenerated(connection: PeerConnection, problemReport: ProblemReport) {

    }

    func onDone(connection: PeerConnection) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    func onRequestReceived(connection: PeerConnection, presentationRequest: PresentationRequestContainer) -> CallbackResult {
        return CallbackResult(canProceedFurther: true)
    }
    
    
}
