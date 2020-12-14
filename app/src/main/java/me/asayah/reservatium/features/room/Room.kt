package me.asayah.reservatium.features.room

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "rooms")
data class Room @JvmOverloads constructor(
    @PrimaryKey
    var roomId: String = UUID.randomUUID().toString(),
    var name: String? = null
): Parcelable {

    companion object {
        val diffutil = object: DiffUtil.ItemCallback<Room>() {
            override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem.roomId == newItem.roomId
            }
        }
    }
}