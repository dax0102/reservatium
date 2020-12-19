package me.asayah.reservatium.features.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.LayoutItemCustomerReservationBinding
import me.asayah.reservatium.features.reservation.ReservationBundle
import me.asayah.reservatium.features.shared.base.BaseAdapter
import java.time.LocalDate

class CustomerReservationAdapter
    : BaseAdapter<ReservationBundle,
        CustomerReservationAdapter.CustomerReservationViewHolder>(ReservationBundle.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerReservationViewHolder {
        val binding = LayoutItemCustomerReservationBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return CustomerReservationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CustomerReservationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class CustomerReservationViewHolder(itemView: View): BaseViewHolder(itemView) {
        private val binding = LayoutItemCustomerReservationBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is ReservationBundle) {
                with(binding) {
                    dateTextView.text = t.reservation.format()
                    roomTextView.text = String.format(root.context.getString(R.string.room_concat),
                        t.room.name)
                }
            }
        }
    }
}