package me.asayah.reservatium.features.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.databinding.LayoutItemRoomReservationBinding
import me.asayah.reservatium.features.reservation.ReservationBundle
import me.asayah.reservatium.features.shared.base.BaseAdapter

class RoomReservationAdapter: BaseAdapter<ReservationBundle,
        RoomReservationAdapter.RoomReservationViewHolder>(ReservationBundle.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomReservationViewHolder {
        val binding = LayoutItemRoomReservationBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return RoomReservationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RoomReservationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class RoomReservationViewHolder(itemView: View): BaseAdapter.BaseViewHolder(itemView) {
        private val binding = LayoutItemRoomReservationBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is ReservationBundle) {
                with(binding) {
                    customerTextView.text = t.customer.getName()
                    dateTextView.text = t.reservation.format()
                }
            }
        }
    }
}