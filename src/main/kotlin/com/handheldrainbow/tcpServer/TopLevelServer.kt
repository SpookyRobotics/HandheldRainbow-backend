package com.handheldrainbow.tcpServer

import com.handheldrainbow.hatControls.RainbowHatInterface
import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.DisplayFrameHandler
import com.handheldrainbow.hatControls.RainbowHatHandler
import java.util.ArrayList

class TopLevelServer(port: Int, id: ServerId, rainbowHatInterface: RainbowHatInterface) : TcpServer(port, getHandlers(id, rainbowHatInterface)) {

    companion object {

        private fun getHandlers(id: ServerId, rainbowHatInterface: RainbowHatInterface): List<ContextHandler> {
            val handlers : ArrayList<ContextHandler> = arrayListOf(
                    ConnectedHandler(id),
                    RainbowHatHandler(id),
                    DisplayFrameHandler(id)
            )
            return handlers
        }
    }
}