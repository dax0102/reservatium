package me.asayah.reservatium.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.asayah.reservatium.features.reservation.Reservation

@Dao
interface ReservationDao {

    @Insert
    suspend fun insert(reservation: Reservation)

    @Delete
    suspend fun remove(reservation: Reservation)

    @Update
    suspend fun update(reservation: Reservation)

    @Query("SELECT * FROM reservations")
    fun fetch(): LiveData<List<Reservation>>

}