import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.RainbowHatFunctions
import com.handheldrainbow.hatControls.RainbowHatInterface

class HatDisplayController(val serverId: ServerId) : Runnable, RainbowHatInterface {

    val UPDATE_DISPLAY_REFRESH_SLEEP : Long = 1000
    @Volatile
    var currentDisplay : RainbowHatFunctions = RainbowHatFunctions.CLEAR_SCREEN
    @Volatile
    var nextDisplay : RainbowHatFunctions = RainbowHatFunctions.CLEAR_SCREEN

    override fun run() {
        when (serverId) {
            ServerId.LEFT -> { currentDisplay = RainbowHatFunctions.SHOW_IDLE_LEFT }
            ServerId.RIGHT ->  { currentDisplay = RainbowHatFunctions.SHOW_IDLE_RIGHT }
            else -> print("No Default display")
        }
        nextDisplay = currentDisplay
        while (true) {
            synchronized(this) {
                if (nextDisplay != currentDisplay) {
                    nextDisplay.execute()
                    currentDisplay = nextDisplay

                }
                Thread.sleep(UPDATE_DISPLAY_REFRESH_SLEEP)
            }
        }
    }

    @Synchronized
    override fun setBrightness(value: Int) {
        nextDisplay = RainbowHatFunctions.CLEAR_SCREEN
    }

    @Synchronized
    override fun clear() {
        nextDisplay = RainbowHatFunctions.CLEAR_SCREEN
    }

    @Synchronized
    override fun showLeftIdle() {
        nextDisplay = RainbowHatFunctions.SHOW_IDLE_LEFT
    }

    @Synchronized
    override fun showRightIdle() {
        nextDisplay = RainbowHatFunctions.SHOW_IDLE_RIGHT
    }

    @Synchronized
    override fun showBallBouncingBothWalls() {
        nextDisplay = RainbowHatFunctions.CLEAR_SCREEN
    }
}