import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads text from the given input txt file.
 */
fun readInputAsText(name: String) = File("src", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
        .padStart(32, '0')

/**
 * Return a Pair of string split in half.
 */
fun String.splitInHalf(): Pair<String, String> {
    val firstHalf = this.slice(IntRange(0, this.length / 2 - 1))
    val secondHalf = this.slice(IntRange(this.length / 2, this.length - 1))
    return firstHalf to secondHalf
}

/**
 * Return Set of reappearing Chars in all Strings in a list.
 */
fun Collection<String>.reappearingChars(): Set<Char> {
    return this.first().filter { char -> this.all { it.contains(char) } }.toSet()
}
