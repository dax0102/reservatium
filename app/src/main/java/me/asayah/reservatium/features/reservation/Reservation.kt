package me.asayah.reservatium.features.reservation

import android.content.Context
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import me.asayah.reservatium.components.formatting.DateTimeFormatting
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.room.Room
import java.time.LocalDateTime
import java.util.*

@Parcelize
@Entity(tableName = "reservations", foreignKeys = [
    ForeignKey(entity = Customer::class, parentColumns = arrayOf("customerId"),
        childColumns = arrayOf("customer"),onDelete = ForeignKey.SET_NULL),
    ForeignKey(entity = Room::class, parentColumns = arrayOf("roomId"),
        childColumns = arrayOf("room"), onDelete = ForeignKey.SET_NULL)
])
data class Reservation @JvmOverloads constructor(
        @PrimaryKey
    var reservationId: String = UUID.randomUUID().toString(),
        var room: String? = null,
        var customer: String? = null,
        var startDate: LocalDateTime? = null,
        var endDate: LocalDateTime? = null,
        var numberOfGuests: Int = 0,
): Parcelable {

    fun format(context: Context): String {
        return StringBuilder().apply {
            append(startDate?.format(DateTimeFormatting.getDateTimeFormatter(context)))
            if (endDate != null) {
                append(" => ")
                append(endDate?.format(DateTimeFormatting.getDateTimeFormatter(context)))
            }
        }.toString()

    }
}