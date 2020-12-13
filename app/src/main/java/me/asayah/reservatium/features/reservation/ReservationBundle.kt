package me.asayah.reservatium.features.reservation

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.room.RoomCore

@Parcelize
data class ReservationBundle(
        @Embedded
    var reservation: Reservation,
        @Embedded
    var customer: Customer,
        @Embedded
    var roomCore: RoomCore
): Parcelable {}