package com.handheldrainbow.hatControls.tcpInterface

import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.RainbowHatInterface
import com.handheldrainbow.tcpServer.ContextHandler
import com.sun.net.httpserver.HttpHandler

class DisplayFrameHandler(val id: ServerId,
                          val rainbowHatInterface: RainbowHatInterface) : ContextHandler() {
    override fun getContext(): String {
        return "/displayFrame"
    }

    private val FRAME_DATA: String = "FRAME_DATA"

    override fun getHandler(): HttpHandler {
        return HttpHandler {  httpExchange ->
            if (!httpExchange.requestMethod.equals(HTTP_GET)) {
                sendNotAuthorized(httpExchange)
                return@HttpHandler
            }
            val functionHeader = httpExchange.requestHeaders.get(FRAME_DATA)
            if (functionHeader == null || functionHeader.isEmpty()) {
                sendInvalid(httpExchange)
                return@HttpHandler
            }
            sendOk(httpExchange)
            val diodeValues = functionHeader[0].split(",")
                    .map { it.toLong() }
            rainbowHatInterface.setDisplay(diodeValues)
        }
    }

    private fun buildJsonResponse(function: String): String {
        return "Executing $function on $id"
    }

}
