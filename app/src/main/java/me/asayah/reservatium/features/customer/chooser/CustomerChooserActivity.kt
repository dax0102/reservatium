package me.asayah.reservatium.features.customer.chooser

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.components.custom.ItemDecoration
import me.asayah.reservatium.databinding.ChooserCustomerBinding
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.customer.CustomerAdapter
import me.asayah.reservatium.features.shared.base.BaseActivity
import me.asayah.reservatium.features.shared.base.BaseAdapter

@AndroidEntryPoint
class CustomerChooserActivity: BaseActivity(), BaseAdapter.ActionListener {
    private lateinit var binding: ChooserCustomerBinding

    private val viewModel: CustomerChooserViewModel by viewModels()
    private val customerAdapter = CustomerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChooserCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.appbarLayout.toolbar)
        setToolbarTitle(R.string.chooser_customer)

        with(binding.recyclerView) {
            addItemDecoration(ItemDecoration(context))
            adapter = customerAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.customers.observe(this) { customerAdapter.submitList(it) }
        viewModel.isEmpty.observe(this) { binding.emptyView.isVisible = it }
    }

    override fun <T> onActionPerformed(t: T, action: BaseAdapter.ActionListener.Action) {
        if (t is Customer) {
            when(action) {
                BaseAdapter.ActionListener.Action.SELECT -> {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(EXTRA_CUSTOMER, t)
                    })
                    finish()
                }
                BaseAdapter.ActionListener.Action.DELETE -> {
                    viewModel.remove(t)
                    createSnackbar(binding.root, R.string.feedback_customer_removed)
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_CHOOSE = 5
        const val EXTRA_CUSTOMER = "extra:customer"
    }
}