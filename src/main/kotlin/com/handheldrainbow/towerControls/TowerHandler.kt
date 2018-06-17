package com.handheldrainbow.towerControls

import com.handheldrainbow.tcpServer.ContextHandler
import com.sun.net.httpserver.HttpHandler
import com.handheldrainbow.ServerId

class TowerHandler(val id: ServerId,
                   val towerController: TowerInterface) : ContextHandler() {
    override fun getContext(): String {
        return "/towerControl"
    }

    private val HEADER_FUNCTION: String = "FUNCTION"
    private val HEADER_MILLISECONDS: String = "SECONDS"

    override fun getHandler(): HttpHandler {
        return HttpHandler {  httpExchange ->
            if (!httpExchange.requestMethod.equals(HTTP_GET)) {
                sendNotAuthorized(httpExchange)
                return@HttpHandler
            }
            val functionHeader = httpExchange.requestHeaders.get(HEADER_FUNCTION)
            if (functionHeader == null || functionHeader.isEmpty()) {
                sendInvalid(httpExchange)
                return@HttpHandler
            }
            val functionToExecute = functionHeader.first()!!.toLowerCase()
            val isSpinA = functionToExecute == TowerFunctions.SPIN_A.name.toLowerCase()
            val isSpinB = functionToExecute == TowerFunctions.SPIN_B.name.toLowerCase()
            val isStop = functionToExecute == TowerFunctions.STOP.name.toLowerCase()
            val millisecondsArgument : Long
            if (isSpinB || isSpinA) {
                val millisecondsHeader = httpExchange.requestHeaders.get(HEADER_MILLISECONDS)
                if (millisecondsHeader == null || millisecondsHeader.isEmpty()) {
                    sendInvalid(httpExchange)
                    return@HttpHandler
                }
                millisecondsArgument = millisecondsHeader.first()!!.toLong()
            } else {
                millisecondsArgument = 0
            }
            if (!(isSpinA || isSpinB || isStop)) {
                sendInvalid(httpExchange)
                return@HttpHandler
            }
            sendOk(httpExchange)
            when (functionToExecute) {
                TowerFunctions.SPIN_A.name.toLowerCase() -> towerController.spinA(millisecondsArgument)
                TowerFunctions.SPIN_B.name.toLowerCase() -> towerController.spinB(millisecondsArgument)
                TowerFunctions.STOP.name.toLowerCase() -> towerController.stop()
            }
        }
    }

    private fun buildJsonResponse(function: String): String {
        return "Executing $function on $id"
    }

}
