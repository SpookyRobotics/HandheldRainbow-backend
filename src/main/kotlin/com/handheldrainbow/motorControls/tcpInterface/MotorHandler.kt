package com.handheldrainbow.motorControls.tcpInterface

import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.HatDisplayController
import com.handheldrainbow.motorControls.MotorFunctions
import com.handheldrainbow.motorControls.MotorInterface
import com.handheldrainbow.tcpServer.ContextHandler
import com.sun.net.httpserver.HttpHandler

class MotorHandler(val id: ServerId,
                   val motorController: MotorInterface) : ContextHandler() {
    override fun getContext(): String {
        return "/motorControl"
    }

    private val HEADER_FUNCTION: String = "FUNCTION"
    private val HEADER_MILLISECONDS: String = "MILLISECONDS"

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
            val isForward = functionToExecute == MotorFunctions.FORWARD.name.toLowerCase()
            val isBackward = functionToExecute == MotorFunctions.BACKWARD.name.toLowerCase()
            val isStop = functionToExecute == MotorFunctions.STOP.name.toLowerCase()
            val millisecondsArgument : Long
            if (isBackward || isForward) {
                val millisecondsHeader = httpExchange.requestHeaders.get(HEADER_MILLISECONDS)
                if (millisecondsHeader == null || millisecondsHeader.isEmpty()) {
                    sendInvalid(httpExchange)
                    return@HttpHandler
                }
                millisecondsArgument = millisecondsHeader.first()!!.toLong()
            } else {
                millisecondsArgument = 0
            }
            if (!(isForward || isBackward || isStop)) {
                sendInvalid(httpExchange)
                return@HttpHandler
            }
            sendOk(httpExchange)
            when (functionToExecute) {
                MotorFunctions.FORWARD.name.toLowerCase() -> motorController.forwards(millisecondsArgument)
                MotorFunctions.BACKWARD.name.toLowerCase() -> motorController.backwards(millisecondsArgument)
                MotorFunctions.STOP.name.toLowerCase() -> motorController.stop()
            }
        }
    }

    private fun buildJsonResponse(function: String): String {
        return "Executing $function on $id"
    }

}
