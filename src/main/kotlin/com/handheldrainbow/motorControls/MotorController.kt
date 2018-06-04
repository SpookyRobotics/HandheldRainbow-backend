package com.handheldrainbow.motorControls
import executeInThread
import runWithArguments

class MotorController : MotorInterface {
    override fun backwards(milliseconds: Long) = Motors.backwards(milliseconds)

    override fun forwards(milliseconds: Long) = Motors.forwards(milliseconds)

    override fun stop() = Motors.stop()
}

private object Motors : MotorInterface {
    override fun forwards(milliseconds: Long) {
        executeInThread { "sh /home/pi/MotorInterface/forward.sh $milliseconds".runWithArguments() }
    }

    override fun backwards(milliseconds: Long) {
        executeInThread { "sh /home/pi/MotorInterface/backward.sh $milliseconds".runWithArguments() }
    }

    override fun stop() {
        executeInThread { "sh /home/pi/MotorInterface/stop.sh".runWithArguments() }
    }

}