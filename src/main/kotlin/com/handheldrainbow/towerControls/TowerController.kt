package com.handheldrainbow.towerControls

import executeInThread
import runWithArguments

class TowerController : TowerInterface {
    override fun spinA(milliseconds: Long) = Tower.spinA(milliseconds)

    override fun spinB(milliseconds: Long) = Tower.spinB(milliseconds)
    override fun stop() = Tower.stop()
}

private object Tower : TowerInterface {
    override fun spinA(milliseconds: Long) {
        executeInThread { "sh /home/pi/TowerInterface/spinA.sh $milliseconds".runWithArguments() }
    }

    override fun spinB(milliseconds: Long) {
        executeInThread { "sh /home/pi/TowerInterface/spinB.sh $milliseconds".runWithArguments() }
    }

    override fun stop() {
        executeInThread { "sh /home/pi/TowerInterface/stop.sh".runWithArguments() }
    }

}