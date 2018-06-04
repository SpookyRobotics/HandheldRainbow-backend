package com.handheldrainbow.hatControls

import com.handheldrainbow.ServerId
import executeInThread
import runWithArguments

class HatDisplayController(val serverId: ServerId) : Runnable, RainbowHatInterface {

    val UPDATE_DISPLAY_REFRESH_SLEEP : Long = 1000
    @Volatile
    var currentDisplay : () -> Unit = this::clear
    @Volatile
    var nextDisplay : () -> Unit = {}

    override fun run() {
        when (serverId) {
            ServerId.LEFT -> { currentDisplay = this::showLeftIdle }
            ServerId.RIGHT ->  { currentDisplay = this::showRightIdle }
            else -> print("No Default display")
        }
        currentDisplay.invoke()
        while (true) {
            synchronized(this) {
                nextDisplay.invoke()
                currentDisplay = nextDisplay
                Thread.sleep(UPDATE_DISPLAY_REFRESH_SLEEP)
            }
        }
    }

    @Synchronized
    override fun setBrightness(value: Int) {
        nextDisplay = { RainbowHat.setBrightness(value) }
    }

    @Synchronized
    override fun clear() {
        nextDisplay = { RainbowHat.clear() }
    }

    @Synchronized
    override fun showLeftIdle() {
        nextDisplay = { RainbowHat.showLeftIdle() }
    }

    @Synchronized
    override fun showRightIdle() {
        nextDisplay = { RainbowHat.showRightIdle() }
    }

    @Synchronized
    override fun showBallBouncingBothWalls() {
        nextDisplay = { RainbowHat.clear() }
    }

    @Synchronized
    override fun setDisplay(diodeValues: List<Long>) {
        nextDisplay = { RainbowHat.setDisplay(diodeValues) }
    }
}

private object RainbowHat : RainbowHatInterface {
    override fun setBrightness(value: Int) {
        executeInThread { "python /home/pi/RainbowHatInterface/setBrightness.py $value".runWithArguments() }
    }

    override fun clear() {
        executeInThread { "python /home/pi/RainbowHatInterface/clear.py".runWithArguments() }
    }

    override fun showLeftIdle() {
        executeInThread { "python /home/pi/RainbowHatInterface/leftIdle.py".runWithArguments() }
    }

    override fun showRightIdle() {
        executeInThread { "python /home/pi/RainbowHatInterface/rightIdle.py".runWithArguments() }
    }

    override fun showBallBouncingBothWalls() {
        executeInThread { "python /home/pi/RainbowHatInterface/bouncingBall.py".runWithArguments() }
    }

    override fun setDisplay(argbValues: List<Long>) {
        val shellArguments = argbValues.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", " ")
        executeInThread { "python /home/pi/RainbowHatInterface/displayValues.py $shellArguments".runWithArguments() }
    }

}