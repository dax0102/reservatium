package me.asayah.reservatium.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.asayah.reservatium.features.reservation.Reservation
import me.asayah.reservatium.features.reservation.ReservationBundle

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservation: Reservation)

    @Delete
    suspend fun remove(reservation: Reservation)

    @Update
    suspend fun update(reservation: Reservation)

    @Query("SELECT * FROM reservations")
    suspend fun fetchSuspended(): List<Reservation>

    @Query("SELECT * FROM reservations INNER JOIN customers ON customers.customerId == reservations.customer INNER JOIN rooms ON rooms.roomId == reservations.room")
    fun fetch(): LiveData<List<ReservationBundle>>

    @Query("SELECT * FROM reservations INNER JOIN customers ON customers.customerId == reservations.customer INNER JOIN rooms ON rooms.roomId == reservations.room WHERE customer = :id")
    suspend fun fetchWithCustomer(id: String?): List<ReservationBundle>

}