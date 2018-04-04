package com.handheldrainbow.tcpServer

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

abstract class TcpServer (
        protected val port: Int,
        protected val handlers: List<ContextHandler>
){
    private val server: HttpServer = HttpServer.create(InetSocketAddress(port), BACKLOG_QUEUE)


    init {
        server.executor = Executors.newSingleThreadExecutor()
        for (handler in handlers) {
            server.createContext(handler.getContext(), handler.getLoggingHandler())
        }
    }

    fun start() {
        println("Starting server " + name)
        println("http://localhost:$port/")
        println("Press Ctrl-c to exit")
        println("Messages:")
        println("-------------------------------------------------")
        server.start()
    }

    protected val name: String
        get() = javaClass.name

    fun stop() {
        server.stop(SHUDOWN_TASK_COMPLETION_DELAY_SECONDS)
    }

    companion object {
        private val BACKLOG_QUEUE = 2
        private val SHUDOWN_TASK_COMPLETION_DELAY_SECONDS = 0
    }
}