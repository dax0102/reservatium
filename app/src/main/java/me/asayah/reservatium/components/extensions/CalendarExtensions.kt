package me.asayah.reservatium.components.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Calendar.toLocalDate(): LocalDate {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault()).toLocalDate()
}

fun Calendar.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
}