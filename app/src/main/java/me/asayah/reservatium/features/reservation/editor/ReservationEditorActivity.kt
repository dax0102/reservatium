package me.asayah.reservatium.features.reservation.editor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.components.formatting.DateFormatting
import me.asayah.reservatium.databinding.EditorReservationBinding
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.customer.chooser.CustomerChooserActivity
import me.asayah.reservatium.features.reservation.Reservation
import me.asayah.reservatium.features.room.Room
import me.asayah.reservatium.features.room.chooser.RoomChooserActivity
import me.asayah.reservatium.features.shared.DateRangeChooserActivity
import me.asayah.reservatium.features.shared.base.BaseActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ReservationEditorActivity: BaseActivity() {
    private lateinit var binding: EditorReservationBinding
    private lateinit var formatter: DateTimeFormatter

    private var requestCode: Int = REQUEST_CODE_INSERT
    private val viewModel: ReservationEditorViewModel by viewModels()
    private val currentYear = LocalDate.now().year

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditorReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.toolbar)
        setToolbarTitle(R.string.editor_new_reservation)
        formatter = DateFormatting.getFormatter(false)

        if (intent.hasExtra(EXTRA_RESERVATION)) {
            requestCode = REQUEST_CODE_UPDATE
            setToolbarTitle(R.string.editor_edit_reservation)

            intent.getParcelableExtra<Reservation>(EXTRA_RESERVATION)?.also {
                viewModel.reservation
                viewModel.startDate = it.startDate!!
                viewModel.endDate = it.endDate
            }
            intent.getParcelableExtra<Customer>(EXTRA_CUSTOMER)?.also {
                viewModel.customer
            }
            intent.getParcelableExtra<Room>(EXTRA_ROOM)?.also {
                viewModel.room
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.roomLive.observe(this) {
            binding.roomTextView.text = if (it != null) it.name else getString(R.string.input_not_set)
        }
        viewModel.customerLive.observe(this) {
            binding.customerTextView.text = it?.getName() ?: getString(R.string.input_not_set)
        }
        viewModel.startDateLive.observe(this) { startDate ->
            if (startDate != null) {
                binding.dateTextView.text = StringBuilder().apply {
                    append(startDate.format(DateFormatting.getFormatter(startDate.year != currentYear)))
                    viewModel.endDate?.also {
                        append(" - ")
                        append(it.format(DateFormatting.getFormatter(it.year != currentYear)))
                    }
                }
            } else binding.dateTextView.setText(R.string.input_not_set)
        }
        viewModel.endDateLive.observe(this) { endDate ->
            if (viewModel.startDate != null) {
                binding.dateTextView.text = StringBuilder().apply {
                    viewModel.startDate?.also {
                        append(it.format(DateFormatting.getFormatter(it.year != currentYear)))
                    }
                    endDate?.also {
                        append(" - ")
                        append(it.format(DateFormatting.getFormatter(it.year != currentYear)))
                    }
                }
            } else binding.dateTextView.setText(R.string.input_not_set)
        }

        viewModel.status.observe(this) {
            when(it) {
                ReservationEditorViewModel.ReservationStatus.CONFLICT_DATE_ROOM -> {
                    MaterialDialog(this).show {
                        cancelable(false)
                        title(R.string.error_reservation_conflict_room_title)
                        message(R.string.error_reservation_conflict_room_message)
                        positiveButton(R.string.button_reschedule) {
                            binding.dateTextView.performClick()
                            viewModel.reset()
                        }
                        negativeButton(R.string.button_change_room) {
                            binding.roomTextView.performClick()
                            viewModel.reset()
                        }
                    }
                }
                ReservationEditorViewModel.ReservationStatus.CONFLICT_DATE_CUSTOMER -> {
                    MaterialDialog(this).show {
                        cancelable(false)
                        title(R.string.error_reservation_conflict_customer_title)
                        message(R.string.error_reservation_conflict_customer_message)
                        positiveButton(R.string.button_reschedule) {
                            binding.dateTextView.performClick()
                            viewModel.reset()
                        }
                        negativeButton(R.string.button_change_room) {
                            binding.customerTextView.performClick()
                            viewModel.reset()
                        }
                    }
                }
                ReservationEditorViewModel.ReservationStatus.CONFLICT_NONE -> { }
                null -> {}
            }
        }

        binding.roomTextView.setOnClickListener {
            startActivityForResult(Intent(this, RoomChooserActivity::class.java),
                RoomChooserActivity.REQUEST_CODE_CHOOSE)
        }

        binding.customerTextViewContainer.setOnClickListener {
            startActivityForResult(Intent(this, CustomerChooserActivity::class.java),
                CustomerChooserActivity.REQUEST_CODE_CHOOSE)
        }

        binding.dateTextView.setOnClickListener {
            startActivityForResult(Intent(this, DateRangeChooserActivity::class.java),
                DateRangeChooserActivity.REQUEST_CODE_CHOOSE)
        }

        binding.numberStepper.setOnValueChangedListener { _, value ->
            viewModel.numberOfGuests = value
        }

        binding.actionButton.setOnClickListener {
            if (viewModel.room == null)
                return@setOnClickListener

            if (viewModel.customer == null)
                return@setOnClickListener

            setResult(RESULT_OK, Intent().apply {
                putExtra(EXTRA_RESERVATION, viewModel.reservation)
            })
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        when(requestCode) {
            CustomerChooserActivity.REQUEST_CODE_CHOOSE -> {
                data?.getParcelableExtra<Customer>(CustomerChooserActivity.EXTRA_CUSTOMER)?.also {
                    viewModel.customer = it
                }
            }
            RoomChooserActivity.REQUEST_CODE_CHOOSE -> {
                data?.getParcelableExtra<Room>(RoomChooserActivity.EXTRA_ROOM)?.also {
                    viewModel.room = it
                }
            }
            DateRangeChooserActivity.REQUEST_CODE_CHOOSE -> {
                viewModel.startDate = data?.getParcelableExtra(DateRangeChooserActivity.EXTRA_START_DATE)
                viewModel.endDate = data?.getParcelableExtra(DateRangeChooserActivity.EXTRA_END_DATE)
            }
        }
    }

    companion object {
        const val REQUEST_CODE_INSERT = 2
        const val REQUEST_CODE_UPDATE = 4
        const val EXTRA_RESERVATION = "extra:reservation"
        const val EXTRA_ROOM = "extra:room"
        const val EXTRA_CUSTOMER = "extra:customer"
    }
}