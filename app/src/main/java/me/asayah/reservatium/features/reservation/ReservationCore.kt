package me.asayah.reservatium.features.reservation

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.room.Room
import java.time.LocalDateTime
import java.util.*

@Parcelize
@Entity(tableName = "reservations", foreignKeys = [
    ForeignKey(entity = Customer::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("customer"),onDelete = ForeignKey.SET_NULL),
    ForeignKey(entity = Room::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("room"), onDelete = ForeignKey.SET_NULL)
])
data class ReservationCore @JvmOverloads constructor(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var room: String? = null,
    var customer: String? = null,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
    var numberOfGuests: Int = 0,
): Parcelable