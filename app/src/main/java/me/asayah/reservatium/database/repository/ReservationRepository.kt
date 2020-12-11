package me.asayah.reservatium.database.repository

import androidx.lifecycle.LiveData
import me.asayah.reservatium.database.dao.ReservationDao
import me.asayah.reservatium.features.reservation.ReservationCore
import javax.inject.Inject

class ReservationRepository @Inject constructor(private val reservationDao: ReservationDao) {

    suspend fun insert(reservation: ReservationCore) = reservationDao.insert(reservation)

    suspend fun remove(reservation: ReservationCore) = reservationDao.remove(reservation)

    suspend fun update(reservation: ReservationCore) = reservationDao.update(reservation)

    suspend fun fetch(): LiveData<List<ReservationCore>> = reservationDao.fetch()

}