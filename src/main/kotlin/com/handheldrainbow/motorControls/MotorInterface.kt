package com.handheldrainbow.motorControls

interface MotorInterface {
    fun backwards(milliseconds : Long)
    fun forwards(milliseconds: Long)
    fun stop()
}