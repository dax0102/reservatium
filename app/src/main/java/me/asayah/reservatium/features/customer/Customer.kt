package me.asayah.reservatium.features.customer

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
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
): Parcelable {

    companion object {
        val DIFF_UTIL = object: DiffUtil.ItemCallback<Customer>() {
            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}