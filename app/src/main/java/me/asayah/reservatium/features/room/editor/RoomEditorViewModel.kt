package me.asayah.reservatium.features.room.editor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.ReservationRepository
import me.asayah.reservatium.features.reservation.ReservationBundle
import me.asayah.reservatium.features.room.Room

class RoomEditorViewModel @ViewModelInject constructor(
        private val reservationRepository: ReservationRepository
): ViewModel() {

    var room: Room = Room()
        set(value) {
            field = value
            fetchWithRoom(value.roomId)
        }

    private val _reservations: MutableLiveData<List<ReservationBundle>> = MutableLiveData(emptyList())
    internal val reservations: LiveData<List<ReservationBundle>> = _reservations
    internal val isEmpty: LiveData<Boolean> = Transformations.map(reservations) { it.isEmpty() }

    private fun fetchWithRoom(id: String?) = viewModelScope.launch {
        _reservations.value = reservationRepository.fetchWithRoom(id)
    }

}