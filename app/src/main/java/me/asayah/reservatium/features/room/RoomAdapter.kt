package me.asayah.reservatium.features.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.LayoutItemRoomBinding
import me.asayah.reservatium.features.shared.base.BaseAdapter

class RoomAdapter: BaseAdapter<RoomCore, RoomAdapter.RoomViewHolder>(RoomCore.diffutil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = LayoutItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class RoomViewHolder(itemView: View): BaseViewHolder(itemView) {
        private var binding = LayoutItemRoomBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is RoomCore) {
                with(binding) {
                    titleView.text = t.name
                    summaryView.setText(if (t.isOccupied) R.string.status_occupied else R.string.status_available)
                }
            }
        }
    }
}