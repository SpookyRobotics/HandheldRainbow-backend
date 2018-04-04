package com.handheldrainbow.tcpServer

import com.handheldrainbow.ServerId
import com.sun.net.httpserver.HttpHandler

class ConnectedHandler(val id: ServerId) : ContextHandler() {
    override fun getContext(): String {
        return "/"
    }

    override fun getHandler(): HttpHandler {
        return HttpHandler {  httpExchange ->
            sendOk(httpExchange, response)
        }
    }

    private fun buildJsonResponse() : String {
        return "Connected to HandHeld Rainbow: $id"
    }

    private val response: String = buildJsonResponse()
}