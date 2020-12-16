package me.asayah.reservatium.features.customer.editor

import android.content.Intent
import android.os.Bundle
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.EditorCustomerBinding
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.shared.base.BaseActivity

class CustomerEditorActivity: BaseActivity() {
    private lateinit var binding: EditorCustomerBinding

    private var customer = Customer()
    private var requestCode = REQUEST_CODE_INSERT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditorCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.toolbar)
        setToolbarTitle(R.string.editor_new_customer)

        if (intent.hasExtra(EXTRA_CUSTOMER)) {
            requestCode = REQUEST_CODE_UPDATE
            setToolbarTitle(R.string.editor_edit_customer)

            intent.getParcelableExtra<Customer>(EXTRA_CUSTOMER)?.also { customer = it }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.actionButton.setOnClickListener {

            if (binding.firstNameTextInput.text.isNullOrEmpty()) {
                return@setOnClickListener
            }

            if (binding.lastNameTextInput.text.isNullOrEmpty()) {
                return@setOnClickListener
            }

            with(customer) {
                firstName = binding.firstNameTextInput.text.toString()
                lastName = binding.lastNameTextInput.text.toString()
            }

            setResult(RESULT_OK, Intent().apply {
                putExtra(EXTRA_CUSTOMER, customer)
            })
            finish()
        }
    }

    companion object {
        const val REQUEST_CODE_INSERT = 1
        const val REQUEST_CODE_UPDATE = 3
        const val EXTRA_CUSTOMER = "extra:customer"
    }
}