package me.asayah.reservatium.features.reservation.editor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.CustomerRepository
import me.asayah.reservatium.database.repository.ReservationRepository
import me.asayah.reservatium.database.repository.RoomRepository
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.reservation.Reservation
import me.asayah.reservatium.features.room.Room
import java.time.LocalDateTime

class ReservationEditorViewModel @ViewModelInject constructor(
    private val reservationRepository: ReservationRepository
): ViewModel() {

    private var _status = MutableLiveData(ReservationStatus.CONFLICT_NONE)
    internal val status: LiveData<ReservationStatus> = _status

    var room: Room? = null
        set(value) {
            field = value
            checkReservation()
        }
    var customer: Customer? = null
        set(value) {
            field = value
            checkReservation()
        }
    var reservation = Reservation()
        get() {
            field.room = room?.roomId
            field.customer = customer?.customerId
            field.startDate = startDate
            field.endDate = endDate
            return field
        }
    var startDate: LocalDateTime = LocalDateTime.now()
        set(value) {
            field = value
            checkReservation()
        }
    var endDate: LocalDateTime? = null
        set(value) {
            field = value
            checkReservation()
        }

    private fun checkReservation() = viewModelScope.launch {
        val reservations = reservationRepository.fetchSuspended()
        reservations.forEach {

            val reservedStartDate = it.startDate?.toLocalDate()
            val reservedEndDate = it.startDate?.toLocalDate()
            val chosenDate = startDate.toLocalDate()

            if (chosenDate.isAfter(reservedStartDate) || chosenDate.isBefore(reservedEndDate)) {
                if (it.room == room?.roomId)
                    _status.value = ReservationStatus.CONFLICT_DATE_ROOM
                else if (it.customer == customer?.customerId)
                    _status.value = ReservationStatus.CONFLICT_DATE_CUSTOMER
                else _status.value = ReservationStatus.CONFLICT_NONE
            } else _status.value = ReservationStatus.CONFLICT_NONE
        }
    }

    enum class ReservationStatus {
        CONFLICT_DATE_ROOM,
        CONFLICT_DATE_CUSTOMER,
        CONFLICT_NONE
    }
}