package me.asayah.reservatium.features.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.databinding.LayoutItemReservationBinding
import me.asayah.reservatium.features.shared.base.BaseAdapter

class ReservationAdapter
    : BaseAdapter<ReservationBundle, ReservationAdapter.ReservationViewHolder>(ReservationBundle.CALLBACK)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = LayoutItemReservationBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ReservationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ReservationViewHolder(itemView: View): BaseViewHolder(itemView) {
        private val binding = LayoutItemReservationBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is ReservationBundle) {
                with(binding) {
                    customerTextView.text = t.customer.getName()
                    dateTextView.text = t.reservation.format(root.context)
                    roomTextView.text = t.room.name
                }
            }
        }
    }
}