package com.handheldrainbow.hatControls.tcpInterface

import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.RainbowHat
import com.handheldrainbow.tcpServer.ContextHandler
import com.sun.net.httpserver.HttpHandler

class RainbowHatHandler(val id: ServerId) : ContextHandler() {
    override fun getContext(): String {
        return "/rainbowControl"
    }

    private val HEADER_FUNCTION: String = "FUNCTION"

    override fun getHandler(): HttpHandler {
        return HttpHandler {  httpExchange ->
            if (!httpExchange.requestMethod.equals(HTTP_GET)) {
                sendNotAuthorized(httpExchange)
                return@HttpHandler
            }
            val functionHeader = httpExchange.requestHeaders.get(HEADER_FUNCTION)
            functionHeader?.let {
                val function = it.first()
                if(function.toLowerCase().equals("show_idle_left")) {
                    RainbowHat.showLeftIdle()
                    sendOk(httpExchange)
                    return@HttpHandler
                } else if (function.toLowerCase().equals("show_idle_right")) {
                    RainbowHat.showRightIdle()
                    sendOk(httpExchange)
                    return@HttpHandler
                } else if (function.toLowerCase().equals("clear_screen")) {
                    RainbowHat.clear()
                    sendOk(httpExchange)
                    return@HttpHandler
                }
            }
            sendInvalid(httpExchange)
        }
    }

    private fun buildJsonResponse(function: String): String {
        return "Executing $function on $id"
    }

}
