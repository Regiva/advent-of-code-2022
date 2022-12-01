import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun file(name: String) = File("src", "$name.txt")

fun readInput(fileName: String) = file(fileName).readLines()
fun readText(fileName: String) = file(fileName).readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
