package me.asayah.reservatium.features.room

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "rooms")
data class RoomCore @JvmOverloads constructor(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String? = null,
    var isOccupied: Boolean = false,
): Parcelable {

    companion object {
        val diffutil = object: DiffUtil.ItemCallback<RoomCore>() {
            override fun areContentsTheSame(oldItem: RoomCore, newItem: RoomCore): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: RoomCore, newItem: RoomCore): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}