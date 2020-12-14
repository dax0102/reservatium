package me.asayah.reservatium.features.room.chooser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.RoomRepository
import me.asayah.reservatium.features.room.Room

class RoomChooserViewModel @ViewModelInject constructor(
    private val roomRepository: RoomRepository
): ViewModel() {

    val rooms: LiveData<List<Room>> = roomRepository.fetch()

    fun insert(room: Room) = viewModelScope.launch { roomRepository.insert(room) }

}