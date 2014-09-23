package org.kaloz.gatling.http.action.cometd

import akka.actor.ActorDSL._
import akka.actor.ActorRef
import io.gatling.core.action.Interruptable
import io.gatling.core.session._
import io.gatling.core.util.TimeHelper._
import io.gatling.http.Predef._
import io.gatling.http.ahc.{HttpEngine, WsTx}
import io.gatling.http.check.ws.WsCheck
import io.gatling.http.config.HttpProtocol

class CometDOpenAction(
                        requestName: Expression[String],
                        wsName: String,
                        request: Expression[Request],
                        check: Option[WsCheck],
                        val next: ActorRef,
                        protocol: HttpProtocol) extends Interruptable {

  def execute(session: Session): Unit = {

    def open(tx: WsTx): Unit = {
      logger.info(s"Opening websocket '$wsName': Scenario '${session.scenarioName}', UserId #${session.userId}")

      val wsActor = actor(context)(new CometDActor(wsName))

      HttpEngine.instance.startWebSocketTransaction(tx, wsActor)
    }

    for {
      requestName <- requestName(session)
      request <- request(session)
    } yield open(WsTx(session, request, requestName, protocol, next, nowMillis, check = check))
  }
}