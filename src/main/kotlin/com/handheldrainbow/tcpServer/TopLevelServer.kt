package com.handheldrainbow.tcpServer

import com.handheldrainbow.hatControls.RainbowHatInterface
import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.tcpInterface.DisplayFrameHandler
import com.handheldrainbow.hatControls.tcpInterface.RainbowHatHandler
import com.handheldrainbow.motorControls.MotorInterface
import com.handheldrainbow.motorControls.tcpInterface.MotorHandler
import com.handheldrainbow.towerControls.TowerHandler
import com.handheldrainbow.towerControls.TowerInterface
import java.util.ArrayList

class TopLevelServer(port: Int,
                     id: ServerId, rainbowHatInterface: RainbowHatInterface,
                     motorInterface: MotorInterface,
                     towerInterface: TowerInterface) : TcpServer(port,
                                                                 getHandlers(id, rainbowHatInterface, motorInterface, towerInterface)) {

    companion object {

        private fun getHandlers(id: ServerId,
                                rainbowHatInterface: RainbowHatInterface,
                                motorInterface: MotorInterface, towerInterface: TowerInterface): List<ContextHandler> {
            val handlers: ArrayList<ContextHandler> = arrayListOf(
                    ConnectedHandler(id),
                    RainbowHatHandler(id),
                    DisplayFrameHandler(id, rainbowHatInterface),
                    MotorHandler(id, motorInterface),
                    TowerHandler(id, towerInterface)
            )
            return handlers
        }
    }
}