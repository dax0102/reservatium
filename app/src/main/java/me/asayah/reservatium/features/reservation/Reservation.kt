package me.asayah.reservatium.features.reservation

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.room.Room

@Parcelize
data class Reservation(
    @Embedded
    var reservation: ReservationCore,
    @Embedded
    var customer: Customer,
    @Embedded
    var room: Room
): Parcelable {}