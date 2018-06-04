package com.handheldrainbow.hatControls

interface RainbowHatInterface {

    fun setBrightness(value: Int)
    fun clear()
    fun showLeftIdle()
    fun showRightIdle()
    fun showBallBouncingBothWalls()
    fun setDisplay(diodeValues: List<Long>)
}