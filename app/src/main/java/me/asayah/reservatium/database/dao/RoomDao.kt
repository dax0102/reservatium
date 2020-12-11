package me.asayah.reservatium.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.asayah.reservatium.features.room.Room

@Dao
interface RoomDao {

    @Insert
    suspend fun insert(room: Room)

    @Delete
    suspend fun remove(room: Room)

    @Update
    suspend fun update(room: Room)

    @Transaction
    @Query("SELECT * FROM rooms")
    fun fetch(): LiveData<List<Room>>
}