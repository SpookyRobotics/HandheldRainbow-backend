import com.sun.net.httpserver.HttpExchange
import java.io.File
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.sql.Types
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private val SELECT_LAST_INSERTED_ID_SQL: String = "select LAST_INSERT_ID();"

fun Timestamp.toTimeStampString() : String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this)

fun Timestamp.toDateString() : String = SimpleDateFormat("yyyy-MM-dd").format(this)
fun String.append(suffix: String): String = this + suffix

fun Date.sqlDate(): String = Timestamp(this.time).toDateString()

fun Date.sqlTimeStamp(): String = Timestamp(this.time).toTimeStampString()

fun ResultSet.readIntColumn(columnName: String): Int = getInt(columnName)

fun ResultSet.readStringColumn(columnName: String): String = getString(columnName)

fun ResultSet.readNullableStringColumn(columnName: String): String? = getString(columnName)

fun ResultSet.readTimestampColumn(columnName: String): Timestamp = getTimestamp(columnName)

fun ResultSet.readNullableTimestampColumn(columnName: String): Timestamp? = getTimestamp(columnName)


fun HttpExchange.getSingleHeader(headerName: String): String? {
    val headers = getHeader(headerName)
    if (headers.size != 1) {
        return null
    }
    return headers[0]
}

fun HttpExchange.getHeader(key: String): MutableList<String> {
    return requestHeaders.get(key)!!
}

fun PreparedStatement.setStringNullIfEmpty(columnIndex: Int, stringValue: String?) {
    if (stringValue != null && stringValue.isNotEmpty()) {
        setString(columnIndex, stringValue)
    } else {
        setNull(columnIndex, Types.NCHAR)
    }
}

fun executeInThread(toRun: () -> Unit) {
    Thread{toRun.invoke()}.start()
}

// https://stackoverflow.com/questions/35421699/how-to-invoke-external-command-from-within-kotlin-code
fun String.runWithArguments(workingDir: File? = null): String? {
    try {
        val parts = this.split("\\s".toRegex())
        val commandList = parts.toTypedArray().filter { !it.isBlank() }
        val processBuilder = ProcessBuilder(commandList)
        workingDir?.let { processBuilder.directory(it) }
        val proc = processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()


        println("--------------------")
        println("Executing ${this}")
        proc.waitFor(60, TimeUnit.SECONDS)
        val result = proc.inputStream.bufferedReader().readText()
        println(result)
        println("--------------------\n")
        return result
    } catch(e: Throwable) {
        e.printStackTrace()
        return null
    }
}