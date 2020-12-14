package me.asayah.reservatium.features.room

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.RoomRepository

class RoomViewModel @ViewModelInject constructor(
        private val roomRepository: RoomRepository
): ViewModel() {

    val rooms: LiveData<List<Room>> = roomRepository.fetch()

    fun insert(roomCore: Room) = viewModelScope.launch { roomRepository.insert(roomCore) }

    fun remove(roomCore: Room) = viewModelScope.launch { roomRepository.remove(roomCore) }

    fun update(roomCore: Room) = viewModelScope.launch { roomRepository.update(roomCore) }

}