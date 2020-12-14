package me.asayah.reservatium.features.customer

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "customers")
data class Customer @JvmOverloads constructor(
        @PrimaryKey
        var customerId: String = UUID.randomUUID().toString(),
        var lastName: String? = null,
        var firstName: String? = null
): Parcelable {

    fun getName(): String {
        return StringBuilder()
                .append(firstName)
                .append(" ")
                .append(lastName)
                .toString()
    }

    companion object {
        val CALLBACK = object: DiffUtil.ItemCallback<Customer>() {
            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.customerId == newItem.customerId
            }
        }
    }
}