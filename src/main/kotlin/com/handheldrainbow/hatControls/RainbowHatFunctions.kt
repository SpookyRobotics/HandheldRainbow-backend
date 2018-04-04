package com.handheldrainbow.hatControls

enum class RainbowHatFunctions(val execute: () -> Unit) {
    SET_BRIGHTNESS({ RainbowHat.setBrightness(40) }),
    CLEAR_SCREEN({ RainbowHat.clear() }),
    SHOW_IDLE_LEFT({ RainbowHat.showLeftIdle() }),
    SHOW_IDLE_RIGHT({ RainbowHat.showRightIdle() })
}