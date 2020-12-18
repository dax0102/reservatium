package me.asayah.reservatium.features.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.components.interfaces.Swipeable
import me.asayah.reservatium.databinding.LayoutItemReservationBinding
import me.asayah.reservatium.features.shared.base.BaseAdapter

class ReservationAdapter(private val actionListener: ActionListener)
    : BaseAdapter<ReservationBundle, ReservationAdapter.ReservationViewHolder>(ReservationBundle.CALLBACK), Swipeable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = LayoutItemReservationBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ReservationViewHolder(binding.root, actionListener)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onItemSwipe(position: Int, direction: Int) {
        actionListener.onActionPerformed(getItem(position), ActionListener.Action.DELETE)
    }

    class ReservationViewHolder(itemView: View, private val actionListener: ActionListener)
        : BaseViewHolder(itemView) {
        private val binding = LayoutItemReservationBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is ReservationBundle) {
                with(binding) {
                    customerTextView.text = t.customer.getName()
                    dateTextView.text = t.reservation.format()
                    roomTextView.text = t.room.name
                    root.setOnClickListener {
                        actionListener.onActionPerformed(t, ActionListener.Action.SELECT)
                    }
                }
            }
        }
    }
}