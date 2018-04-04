package com.handheldrainbow.tcpServer

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import getHeader
import java.io.File
import java.io.OutputStream
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*

/**
 * Class that responds to a single uri
 */
abstract class ContextHandler {
    abstract fun getContext(): String
    abstract fun getHandler(): HttpHandler
    private val CONTENT_LENGTH: String = "Content-Length"
    protected val HTTP_POST: String = "POST"
    protected val HTTP_GET: String = "GET"

    private var logging: Boolean = true

    fun getLoggingHandler(): HttpHandler {
        logging = true
        val handler = getHandler()
        val wrapperHandler = HttpHandler { httpExchange ->
            val startLogTime = getTime()
            log("\nConnectionTime: $startLogTime")
            log("Context: " + getContext())
            logHeaders("RequestHeaders: ", httpExchange.requestHeaders)
            try {
                handler.handle(httpExchange)
            } catch (e : Exception) {
                log("Error: " + e)
                log(e.stackTrace.toString())
            }
            val EndLogTime = getTime()
            log("EndConnection: $EndLogTime\n")
        }
        return wrapperHandler
    }

    private fun log(output: String) {
        if (logging) {
            println(output)
        }
    }

    fun getTime(): String {
        val time = Calendar.getInstance().getTime()
        return SimpleDateFormat("yyyy-MM-dd - HH:mm:ss.SSSXXX")
                .format(time)
    }
    protected fun sendNotAuthorized(httpExchange: HttpExchange) {
        send(httpExchange, 404, "400 (Not Authorized)\n" )
    }

    protected fun sendOk(httpExchange: HttpExchange) {
        send(httpExchange, 200, "Ok\n")
    }

    protected fun sendMissingParameter(httpExchange: HttpExchange) {
        send(httpExchange, 400, "Incomplete")
    }

    protected fun sendUnauthorized(httpExchange: HttpExchange) {
        send(httpExchange, 403, "Forbidden")
    }
    protected fun sendInvalid(httpExchange: HttpExchange, error: String) {
        send(httpExchange, 400, error)
    }

    protected fun sendInvalid(httpExchange: HttpExchange) {
        send(httpExchange, 400, "Invalid")
    }

    protected fun sendFile(httpExchange: HttpExchange, path: String) {
        val outputStream: OutputStream = httpExchange.getResponseBody()
        val file = File(path)
        httpExchange.responseHeaders.add("Content-Type", "image/png")
        httpExchange.sendResponseHeaders(200, file.length())
        logHeaders("ResponseHeaders", httpExchange.responseHeaders)
        log("ResponseCode: 200")
        log("SendingFile: ${file.toPath()}")
        Files.copy(file.toPath(), outputStream)
        outputStream.close()
    }

    private fun logHeaders(id: String, headers: Headers?) {
        if (headers == null || !logging || headers.keys.isEmpty()) {
            return
        }
        println("$id --------")
        headers.keys.forEach {
            val value = headers.get(it)
            println("\t$it: $value")
        }


    }

    protected fun hasHeader(httpExchange: HttpExchange, name: String): Boolean {
        return httpExchange.requestHeaders.containsKey(name)
    }

    protected fun sendInvalidLength(httpExchange: HttpExchange) {
        send(httpExchange, 400, "Content Length Invalid")
    }

    protected fun getContentLength(httpExchange: HttpExchange): Int {
        if (!hasHeader(httpExchange, CONTENT_LENGTH)) {
            return -1
        }
        val header = httpExchange.getHeader(CONTENT_LENGTH)
        if (header.size > 1) {
            return -1
        }
        return header[0].toInt()
    }

    private fun send(httpExchange: HttpExchange, httpCode: Int, response: String) {
        val toSend = if (response.endsWith("\n")) response else response + "\n"
        log("ResponseCode: $httpCode")
        val logDisplay = toSend.substring(0, toSend.length-1)
        log("ResponseSending: $logDisplay")
        httpExchange.sendResponseHeaders(httpCode, toSend.toByteArray().size.toLong())
        val output: OutputStream = httpExchange.responseBody
        output.write(toSend.toByteArray())
        output.close()
    }

    protected fun sendContinue(httpExchange: HttpExchange) {
        val response = "continue\n"
        httpExchange.sendResponseHeaders(100, response.toByteArray().size.toLong())
        val output: OutputStream = httpExchange.responseBody
        output.write(response.toByteArray())
    }

    protected fun sendOk(httpExchange: HttpExchange, response: String) {
        send(httpExchange, 200, response)
    }
}