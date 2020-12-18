package me.asayah.reservatium.features.customer.editor

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.components.custom.ItemDecoration
import me.asayah.reservatium.databinding.EditorCustomerBinding
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.reservation.ReservationAdapter
import me.asayah.reservatium.features.shared.base.BaseActivity
import me.asayah.reservatium.features.shared.base.BaseAdapter

@AndroidEntryPoint
class CustomerEditorActivity: BaseActivity(), BaseAdapter.ActionListener {
    private lateinit var binding: EditorCustomerBinding

    private var requestCode = REQUEST_CODE_INSERT

    private val reservationAdapter = ReservationAdapter(this)
    private val viewModel: CustomerEditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditorCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.toolbar)
        setToolbarTitle(R.string.editor_new_customer)

        if (intent.hasExtra(EXTRA_CUSTOMER)) {
            requestCode = REQUEST_CODE_UPDATE
            setToolbarTitle(R.string.editor_edit_customer)

            intent.getParcelableExtra<Customer>(EXTRA_CUSTOMER)?.also {
                viewModel.customer = it
            }
        }

        with(binding.recyclerView) {
            addItemDecoration(ItemDecoration(context))
            adapter = reservationAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.customerLive.observe(this) {
            binding.firstNameEditText.setText(it.firstName)
            binding.lastNameEditText.setText(it.lastName)
        }

        viewModel.reservations.observe(this) {
            reservationAdapter.submitList(it)
        }

        binding.actionButton.setOnClickListener {
            val customer = viewModel.customer

            if (customer.firstName.isNullOrEmpty()) {
                createSnackbar(binding.root, R.string.error_customer_no_first_name)
                return@setOnClickListener
            }

            if (customer.lastName.isNullOrEmpty()) {
                createSnackbar(binding.root, R.string.error_customer_no_last_name)
                return@setOnClickListener
            }

            setResult(RESULT_OK, Intent().apply {
                putExtra(EXTRA_CUSTOMER, customer)
            })
            finish()
        }
    }

    override fun <T> onActionPerformed(t: T, action: BaseAdapter.ActionListener.Action) {}

    companion object {
        const val REQUEST_CODE_INSERT = 1
        const val REQUEST_CODE_UPDATE = 3
        const val EXTRA_CUSTOMER = "extra:customer"
    }
}