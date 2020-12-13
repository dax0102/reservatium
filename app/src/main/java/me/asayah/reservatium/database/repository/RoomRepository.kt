package me.asayah.reservatium.database.repository

import androidx.lifecycle.LiveData
import me.asayah.reservatium.database.dao.RoomDao
import me.asayah.reservatium.features.room.RoomCore
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomDao: RoomDao) {

    suspend fun insert(roomCore: RoomCore) = roomDao.insert(roomCore)

    suspend fun remove(roomCore: RoomCore) = roomDao.remove(roomCore)

    suspend fun update(roomCore: RoomCore) = roomDao.update(roomCore)

    fun fetch(): LiveData<List<RoomCore>> = roomDao.fetch()

}