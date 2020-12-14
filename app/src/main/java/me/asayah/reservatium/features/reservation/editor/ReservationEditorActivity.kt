package me.asayah.reservatium.features.reservation.editor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.components.extensions.toLocalDateTime
import me.asayah.reservatium.databinding.EditorReservationBinding
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.customer.chooser.CustomerChooserActivity
import me.asayah.reservatium.features.room.Room
import me.asayah.reservatium.features.room.chooser.RoomChooserActivity
import me.asayah.reservatium.features.shared.base.BaseActivity

@AndroidEntryPoint
class ReservationEditorActivity: BaseActivity() {
    private lateinit var binding: EditorReservationBinding

    private var requestCode: Int = REQUEST_CODE_INSERT
    private val viewModel: ReservationEditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditorReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.appbarLayout.toolbar)
        setToolbarTitle(R.string.editor_new_reservation)

        if (intent.hasExtra(EXTRA_RESERVATION)) {
            requestCode = REQUEST_CODE_UPDATE
            setToolbarTitle(R.string.editor_edit_reservation)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.status.observe(this) {
            when(it) {
                ReservationEditorViewModel.ReservationStatus.CONFLICT_DATE_ROOM -> {
                    MaterialDialog(this).show {
                        cancelable(false)
                        title(R.string.error_reservation_conflict_room_title)
                        message(R.string.error_reservation_conflict_room_message)
                        positiveButton(R.string.button_reschedule) {
                            binding.startDateTextView.performClick()
                        }
                        negativeButton(R.string.button_change_room) {
                            binding.roomTextView.performClick()
                        }
                    }
                }
                ReservationEditorViewModel.ReservationStatus.CONFLICT_DATE_CUSTOMER -> {
                    MaterialDialog(this).show {
                        cancelable(false)
                        title(R.string.error_reservation_conflict_customer_title)
                        message(R.string.error_reservation_conflict_customer_message)
                        positiveButton(R.string.button_reschedule) {
                            binding.startDateTextView.performClick()
                        }
                        negativeButton(R.string.button_change_room) {
                            binding.customerTextView.performClick()
                        }
                    }
                }
                ReservationEditorViewModel.ReservationStatus.CONFLICT_NONE -> { }
            }
        }

        binding.roomTextView.setOnClickListener {
            startActivityForResult(Intent(this, RoomChooserActivity::class.java),
                RoomChooserActivity.REQUEST_CODE_CHOOSE)
        }

        binding.customerTextView.setOnClickListener {
            startActivityForResult(Intent(this, CustomerChooserActivity::class.java),
                CustomerChooserActivity.REQUEST_CODE_CHOOSE)
        }

        binding.startDateTextView.setOnClickListener {
            MaterialDialog(it.context).show {
                title(R.string.input_start_date)
                dateTimePicker { _, datetime ->
                    viewModel.startDate = datetime.toLocalDateTime()
                }
            }
        }

        binding.endDateTextView.setOnClickListener {
            MaterialDialog(it.context).show {
                title(R.string.input_end_date)
                dateTimePicker { _, datetime ->

                }
            }
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