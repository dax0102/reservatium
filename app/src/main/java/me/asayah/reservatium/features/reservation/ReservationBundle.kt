package me.asayah.reservatium.features.reservation

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.room.Room

@Parcelize
data class ReservationBundle(
        @Embedded
        var reservation: Reservation,
        @Embedded
        var customer: Customer,
        @Embedded
        var room: Room
): Parcelable {

    companion object {
        val CALLBACK = object: DiffUtil.ItemCallback<ReservationBundle>() {
            override fun areContentsTheSame(oldItem: ReservationBundle, newItem: ReservationBundle): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ReservationBundle, newItem: ReservationBundle): Boolean {
                return oldItem.reservation.reservationId == newItem.reservation.reservationId
            }
        }
    }
}