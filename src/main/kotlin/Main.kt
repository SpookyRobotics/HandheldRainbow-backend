import com.handheldrainbow.hatControls.RainbowHatInterface
import com.handheldrainbow.ServerId
import com.handheldrainbow.tcpServer.TopLevelServer
import java.io.File

fun main(args: Array<String>) {
    val hatServiceStartupWait: Long = 8000
    val hatInterface : RainbowHatInterface = RainbowHat
    val port : Int = 4789
    val serverId = getServerId()
    val hatDisplayController : HatDisplayController = HatDisplayController(serverId)
    val server: TopLevelServer = TopLevelServer(port, serverId, hatDisplayController)
    server.start()
    Thread.sleep(hatServiceStartupWait)

    Thread(hatDisplayController).start()
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


