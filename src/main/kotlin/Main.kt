import com.handheldrainbow.ServerId
import com.handheldrainbow.hatControls.HatDisplayController
import com.handheldrainbow.motorControls.MotorController
import com.handheldrainbow.tcpServer.TopLevelServer
import com.handheldrainbow.towerControls.TowerController
import java.io.File

fun main(args: Array<String>) {
    val hatServiceStartupWait: Long = 8000
    val port : Int = 4789
    val serverId = getServerId()
    val hatDisplayController : HatDisplayController = HatDisplayController(serverId)
    val motorController = MotorController()
    val towerController = TowerController()
    val server: TopLevelServer = TopLevelServer(port, serverId, hatDisplayController, motorController, towerController)
    server.start()
}


fun getServerId(): ServerId {
    val serverFile = File(SERVER_INIT_FILE)
    if (serverFile.exists()) {
        serverFile.useLines { lines ->
            lines.forEach { singleLine ->
                ServerId.values()
                        .first { it.name.toLowerCase() == singleLine.toLowerCase() }
                        .let { return it }
            }
        }
    }
    return ServerId.UNKNOWN
}
//val SERVER_INIT_FILE: String = "/tmp/.HANDHELD_RAINBOW_INIT"

val SERVER_INIT_FILE: String = "/home/pi/.HANDHELD_RAINBOW_INIT"


