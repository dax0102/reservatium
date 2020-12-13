package me.asayah.reservatium.features.customer

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerBundle @JvmOverloads constructor(
        @Embedded
        var customer: Customer
): Parcelable {

    companion object {
        val DIFF_UTIL = object: DiffUtil.ItemCallback<CustomerBundle>() {
            override fun areContentsTheSame(oldItem: CustomerBundle, newItem: CustomerBundle): Boolean {
                return oldItem.customer.id == newItem.customer.id
            }

            override fun areItemsTheSame(oldItem: CustomerBundle, newItem: CustomerBundle): Boolean {
                return oldItem == newItem
            }
        }
    }
}