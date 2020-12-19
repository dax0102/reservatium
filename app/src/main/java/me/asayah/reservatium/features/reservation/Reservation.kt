package me.asayah.reservatium.features.reservation

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import me.asayah.reservatium.components.extensions.isCurrentYear
import me.asayah.reservatium.components.formatting.DateFormatting
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.room.Room
import java.time.LocalDate
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
        var startDate: LocalDate? = null,
        var endDate: LocalDate? = null,
        var numberOfGuests: Int = 0,
): Parcelable {

    fun format(): String {
        return StringBuilder().apply {
            append(startDate?.format(DateFormatting.getFormatter(startDate?.isCurrentYear() != true)))
            if (endDate != null) {
                append(" - ")
                append(endDate
                        ?.format(DateFormatting.getFormatter(endDate?.isCurrentYear() != true)))
            }
        }.toString()
    }
}