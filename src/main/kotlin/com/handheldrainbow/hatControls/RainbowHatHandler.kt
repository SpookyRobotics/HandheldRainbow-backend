package com.handheldrainbow.hatControls

import com.handheldrainbow.ServerId
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
                RainbowHatFunctions.values()
                        .firstOrNull { it.name.equals(function) }
                        ?.let {
                            it.execute()
                            sendOk(httpExchange, buildJsonResponse(function))
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
