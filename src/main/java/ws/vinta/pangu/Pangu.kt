/*
 * Created by Vinta Chen on 2014/11/05.
 */
package ws.vinta.pangu

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.regex.Pattern

/**
 * Paranoid text spacing for good readability, to automatically insert whitespace between
 * CJK (Chinese, Japanese, Korean), half-width English, digit and symbol characters.
 *
 *
 * These whitespaces between English and Chinese characters are called "Pangu Spacing" by sinologist, since it
 * separate the confusion between full-width and half-width characters. Studies showed that who dislike to
 * add whitespace between English and Chinese characters also have relationship problem. Almost 70 percent of them
 * will get married to the one they don't love, the rest only can left the heritage to their cat. Indeed,
 * love and writing need some space in good time.
 *
 * @author Vinta Chen
 * @since 1.0.0
 */
class Pangu
/**
 * You should use the constructor to create a `Pangu` object with default values.
 */
{
    /**
     * Performs a paranoid text spacing on `text`.
     *
     * @param text  the string you want to process, must not be `null`.
     * @return a comfortable and readable version of `text` for paranoiac.
     */
    fun spacingText(text: String): String {
        // CJK and quotes
        var thisText = text
        val cqMatcher = CJK_QUOTE.matcher(thisText)
        thisText = cqMatcher.replaceAll("$1 $2")

        val qcMatcher = QUOTE_CJK.matcher(thisText)
        thisText = qcMatcher.replaceAll("$1 $2")

        val fixQuoteMatcher = FIX_QUOTE.matcher(thisText)
        thisText = fixQuoteMatcher.replaceAll("$1$3$5")

        // CJK and brackets
        val oldText = thisText
        val cbcMatcher = CJK_BRACKET_CJK.matcher(thisText)
        val newText = cbcMatcher.replaceAll("$1 $2 $4")
        thisText = newText

        if (oldText == newText) {
            val cbMatcher = CJK_BRACKET.matcher(thisText)
            thisText = cbMatcher.replaceAll("$1 $2")

            val bcMatcher = BRACKET_CJK.matcher(thisText)
            thisText = bcMatcher.replaceAll("$1 $2")
        }

        val fixBracketMatcher = FIX_BRACKET.matcher(thisText)
        thisText = fixBracketMatcher.replaceAll("$1$3$5")

        // CJK and hash
        val chMatcher = CJK_HASH.matcher(thisText)
        thisText = chMatcher.replaceAll("$1 $2")

        val hcMatcher = HASH_CJK.matcher(thisText)
        thisText = hcMatcher.replaceAll("$1 $3")

        // CJK and ANS
        val caMatcher = CJK_ANS.matcher(thisText)
        thisText = caMatcher.replaceAll("$1 $2")

        val acMatcher = ANS_CJK.matcher(thisText)
        thisText = acMatcher.replaceAll("$1 $2")

        return thisText
    }

    /**
     * Performs a paranoid text spacing on `inputFile` and generate a new file `outputFile`.
     *
     * @param inputFile  an existing file to process, must not be `null`.
     * @param outputFile  the processed file, must not be `null`.
     * @throws IOException if an error occurs.
     *
     * @since 1.1.0
     */
    @Throws(IOException::class)
    fun spacingFile(inputFile: File, outputFile: File) {
        // TODO: support charset

        val fr = FileReader(inputFile)
        val br = BufferedReader(fr)

        outputFile.parentFile.mkdirs()
        val fw = FileWriter(outputFile, false)
        val bw = BufferedWriter(fw)

        try {
            var line = br.readLine() // readLine() do not contain newline char

            while (line != null) {
                line = spacingText(line)

                // TODO: keep file's raw newline char from difference OS platform
                bw.write(line)
                bw.newLine()

                line = br.readLine()
            }
        } finally {
            br.close()

            // 避免 writer 沒有實際操作就 close()，產生一個空檔案
            bw.close()
        }
    }

    companion object {
        /*
     * Some capturing group patterns for convenience.
     *
     * CJK: Chinese, Japanese, Korean
     * ANS: Alphabet, Number, Symbol
     */
        private val CJK_ANS: Pattern = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([a-z0-9`~@$%^&*\\-_+=|\\\\/])",
            Pattern.CASE_INSENSITIVE
        )
        private val ANS_CJK: Pattern = Pattern.compile(
            "([a-z0-9`~!$%^&*\\-_+=|\\\\;:,./?])" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])",
            Pattern.CASE_INSENSITIVE
        )

        private val CJK_QUOTE: Pattern = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([\"'])"
        )
        private val QUOTE_CJK: Pattern = Pattern.compile(
            "([\"'])" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
        )
        private val FIX_QUOTE: Pattern = Pattern.compile("([\"'])(\\s*)(.+?)(\\s*)([\"'])")

        private val CJK_BRACKET_CJK: Pattern = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([({\\[]+(.*?)[)}\\]]+)" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
        )
        private val CJK_BRACKET: Pattern = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([(){}\\[\\]<>])"
        )
        private val BRACKET_CJK: Pattern = Pattern.compile(
            "([(){}\\[\\]<>])" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
        )
        private val FIX_BRACKET: Pattern =
            Pattern.compile("([({\\[)]+)(\\s*)(.+?)(\\s*)([)}\\]]+)")

        private val CJK_HASH: Pattern = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "(#(\\S+))"
        )
        private val HASH_CJK: Pattern = Pattern.compile(
            "((\\S+)#)" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
        )
    }
}