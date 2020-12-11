package me.asayah.reservatium.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.asayah.reservatium.features.reservation.ReservationCore

@Dao
interface ReservationDao {

    @Insert
    suspend fun insert(reservation: ReservationCore)

    @Delete
    suspend fun remove(reservation: ReservationCore)

    @Update
    suspend fun update(reservation: ReservationCore)

    @Transaction
    @Query("SELECT * FROM reservations")
    fun fetch(): LiveData<List<ReservationCore>>

}