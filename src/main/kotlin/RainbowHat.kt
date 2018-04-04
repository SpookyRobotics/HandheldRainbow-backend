import com.handheldrainbow.hatControls.RainbowHatInterface

object RainbowHat : RainbowHatInterface {
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

}