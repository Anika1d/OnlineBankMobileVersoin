package libs

import java.text.SimpleDateFormat
import java.util.*

val defaultDateTimeFormatter = SimpleDateFormat("dd MMM HH:mm:ss", Locale.forLanguageTag("RU"))
val defaultDateYearFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("RU"))
val defaultTimeShortFormatter = SimpleDateFormat("HH:mm", Locale.forLanguageTag("RU"))

fun <T> createNullList(n: Int): List<T?> {
    val newList = mutableListOf<T?>()
    repeat(n) {
        newList.add(null)
    }
    return newList
}

fun <T> List<T?>.addNullList(n: Int): List<T?> {
    val newList = this.toMutableList()
    repeat(n) {
        newList.add(null)
    }
    return newList
}

// always merges from end
// OOBE-safe for the list being merged
fun <T> List<T>.mergeFromList(another: List<T>, n: Int): List<T> {
    var newList = this.toMutableList()
    var outCounter = 0
    repeat(n) {
        val i = n - it
        if (another.size > (n - i)) newList[size - i] = another[n - i]
        else outCounter++
    }
    if (outCounter > 0)
        newList = newList.dropLast(outCounter)
            .toMutableList() // effectively merged nothing from the merged list this way
    return newList
}