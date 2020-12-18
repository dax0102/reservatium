package me.asayah.reservatium.features.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class DateRange(
        var startDate: LocalDate?,
        var endDate: LocalDate?
): Parcelable