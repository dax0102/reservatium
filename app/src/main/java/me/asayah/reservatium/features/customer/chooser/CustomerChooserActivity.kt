package me.asayah.reservatium.features.customer.chooser

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.ChooserCustomerBinding
import me.asayah.reservatium.features.shared.base.BaseActivity

@AndroidEntryPoint
class CustomerChooserActivity: BaseActivity() {
    private lateinit var binding: ChooserCustomerBinding

    private val viewModel: CustomerChooserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChooserCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.appbarLayout.toolbar)
        setToolbarTitle(R.string.chooser_customer)
    }

    override fun onStart() {
        super.onStart()

        viewModel.customers.observe(this) { }
    }

    companion object {
        const val REQUEST_CODE_CHOOSE = 5
        const val EXTRA_CUSTOMER = "extra:customer"
    }
}