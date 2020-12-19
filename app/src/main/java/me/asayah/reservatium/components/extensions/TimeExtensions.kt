package me.asayah.reservatium.components.extensions

import java.time.LocalDate

fun LocalDate.isCurrentYear(): Boolean {
    return LocalDate.now().year == this.year
}