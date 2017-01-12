import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

/**
 * This is a very primitive logging mechanism for Kotlin-based bots.
 * If you have significant logging needs, use a standard, full-featured logging system.
 */
interface Logger {

    fun log(message: String): Unit {
        synchronized(Logger, {
            Logger.logWriter.write("$message\n")
            Logger.logWriter.flush()})
    }

    companion object Logger {
        private val logWriter: Writer by lazy {
            val logFile = File("kotlin_bot_${ISO_LOCAL_DATE_TIME.format(LocalDateTime.now())}.log")
            FileWriter(logFile)
        }

        fun close(): Unit = Logger.logWriter.close()
    }
}
