package me.asayah.reservatium.features.reservation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.ReservationRepository

class ReservationViewModel @ViewModelInject constructor(
        private val reservationRepository: ReservationRepository
): ViewModel() {

    val reservations: LiveData<List<ReservationBundle>> = reservationRepository.fetch()
    val isEmpty: LiveData<Boolean> = Transformations.map(reservations) { it.isEmpty() }

    fun insert(reservation: Reservation) = viewModelScope.launch {
        reservationRepository.insert(reservation)
    }

    fun update(reservation: Reservation) = viewModelScope.launch {
        reservationRepository.update(reservation)
    }

    fun remove(reservation: Reservation) = viewModelScope.launch {
        reservationRepository.remove(reservation)
    }

}