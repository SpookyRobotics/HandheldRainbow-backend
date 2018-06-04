package com.handheldrainbow.tcpServer

import com.handheldrainbow.hatControls.RainbowHatInterface
import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.tcpInterface.DisplayFrameHandler
import com.handheldrainbow.hatControls.tcpInterface.RainbowHatHandler
import com.handheldrainbow.motorControls.MotorInterface
import com.handheldrainbow.motorControls.tcpInterface.MotorHandler
import java.util.ArrayList

class TopLevelServer(port: Int,
                     id: ServerId, rainbowHatInterface: RainbowHatInterface,
                     motorInterface: MotorInterface) : TcpServer(port, getHandlers(id, rainbowHatInterface, motorInterface)) {

    companion object {

        private fun getHandlers(id: ServerId,
                                rainbowHatInterface: RainbowHatInterface,
                                motorInterface: MotorInterface): List<ContextHandler> {
            val handlers : ArrayList<ContextHandler> = arrayListOf(
                    ConnectedHandler(id),
                    RainbowHatHandler(id),
                    DisplayFrameHandler(id, rainbowHatInterface),
                    MotorHandler(id, motorInterface)
            )
            return handlers
        }
    }
}