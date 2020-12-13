package me.asayah.reservatium.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.asayah.reservatium.features.room.RoomCore

@Dao
interface RoomDao {

    @Insert
    suspend fun insert(roomCore: RoomCore)

    @Delete
    suspend fun remove(roomCore: RoomCore)

    @Update
    suspend fun update(roomCore: RoomCore)

    @Query("SELECT * FROM rooms")
    fun fetch(): LiveData<List<RoomCore>>

}