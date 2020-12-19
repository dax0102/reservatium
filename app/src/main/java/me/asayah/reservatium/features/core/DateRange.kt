package me.asayah.reservatium.features.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import me.asayah.reservatium.components.extensions.isCurrentYear
import java.time.LocalDate

@Parcelize
data class DateRange(
        var startDate: LocalDate?,
        var endDate: LocalDate?
): Parcelable