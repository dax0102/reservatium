package me.asayah.reservatium.features.room

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.RoomRepository

class RoomViewModel @ViewModelInject constructor(
        private val roomRepository: RoomRepository
): ViewModel() {

    val rooms: LiveData<List<RoomCore>> = roomRepository.fetch()
    val isEmpty: LiveData<Boolean> = Transformations.map(rooms) { it.isEmpty() }

    fun insert(roomCore: RoomCore) = viewModelScope.launch { roomRepository.insert(roomCore) }

    fun remove(roomCore: RoomCore) = viewModelScope.launch { roomRepository.remove(roomCore) }

    fun update(roomCore: RoomCore) = viewModelScope.launch { roomRepository.update(roomCore) }

}