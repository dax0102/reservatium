package me.asayah.reservatium.database.repository

import androidx.lifecycle.LiveData
import me.asayah.reservatium.database.dao.ReservationDao
import me.asayah.reservatium.features.reservation.Reservation
import me.asayah.reservatium.features.reservation.ReservationBundle
import me.asayah.reservatium.features.room.Room
import javax.inject.Inject

class ReservationRepository @Inject constructor(private val reservationDao: ReservationDao) {

    suspend fun insert(reservation: Reservation) = reservationDao.insert(reservation)

    suspend fun remove(reservation: Reservation) = reservationDao.remove(reservation)

    suspend fun update(reservation: Reservation) = reservationDao.update(reservation)

    suspend fun fetchSuspended(): List<Reservation> = reservationDao.fetchSuspended()

    fun fetch(): LiveData<List<ReservationBundle>> = reservationDao.fetch()

}