package me.asayah.reservatium.components.extensions

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Calendar.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
}