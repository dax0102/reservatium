package me.asayah.reservatium.features.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.LayoutItemRoomBinding
import me.asayah.reservatium.features.shared.base.BaseAdapter

class RoomAdapter(private val actionListener: ActionListener)
    : BaseAdapter<Room, RoomAdapter.RoomViewHolder>(Room.diffutil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = LayoutItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding.root, actionListener)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class RoomViewHolder(itemView: View, private val actionListener: ActionListener): BaseViewHolder(itemView) {
        private var binding = LayoutItemRoomBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is Room) {
                with(binding) {
                    titleView.text = t.name
                    root.setOnClickListener {
                        actionListener.onActionPerformed(t, ActionListener.Action.SELECT)
                    }
                }
            }
        }
    }
}