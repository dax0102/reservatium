package me.asayah.reservatium.database.repository

import androidx.lifecycle.LiveData
import me.asayah.reservatium.database.dao.RoomDao
import me.asayah.reservatium.features.room.Room
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomDao: RoomDao) {

    suspend fun insert(room: Room) = roomDao.insert(room)

    suspend fun remove(room: Room) = roomDao.remove(room)

    suspend fun update(room: Room) = roomDao.update(room)

    suspend fun fetch(): LiveData<List<Room>> = roomDao.fetch()

}