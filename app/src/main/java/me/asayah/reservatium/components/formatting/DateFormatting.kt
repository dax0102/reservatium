package me.asayah.reservatium.components.formatting

import java.time.format.DateTimeFormatter

object DateFormatting {

    private const val FORMAT_DATE_WITH_YEAR = "MMM d yyyy"
    private const val FORMAT_DATE_WITHOUT_YEAR = "MMM d"

    fun getFormatter(withYear: Boolean = false): DateTimeFormatter {
        val pattern = if (withYear) FORMAT_DATE_WITH_YEAR else FORMAT_DATE_WITHOUT_YEAR
        return DateTimeFormatter.ofPattern(pattern)
    }
}