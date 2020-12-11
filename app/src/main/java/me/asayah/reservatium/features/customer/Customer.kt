package me.asayah.reservatium.features.customer

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "customers")
data class Customer @JvmOverloads constructor(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var lastName: String? = null,
    var firstName: String? = null
): Parcelable {}