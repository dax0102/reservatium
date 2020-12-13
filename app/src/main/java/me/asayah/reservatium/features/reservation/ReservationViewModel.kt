package me.asayah.reservatium.features.reservation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import me.asayah.reservatium.database.repository.ReservationRepository

class ReservationViewModel @ViewModelInject constructor(
        private val reservationRepository: ReservationRepository
): ViewModel() {
}