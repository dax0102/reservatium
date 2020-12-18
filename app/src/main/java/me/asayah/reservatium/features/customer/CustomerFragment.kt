package me.asayah.reservatium.features.customer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.components.custom.ItemDecoration
import me.asayah.reservatium.components.custom.ItemSwipeCallback
import me.asayah.reservatium.databinding.FragmentCustomerBinding
import me.asayah.reservatium.features.customer.editor.CustomerEditorActivity
import me.asayah.reservatium.features.shared.base.BaseAdapter
import me.asayah.reservatium.features.shared.base.BaseFragment

@AndroidEntryPoint
class CustomerFragment: BaseFragment(), BaseAdapter.ActionListener {
    private var _binding: FragmentCustomerBinding? = null

    private val binding get() = _binding!!
    private val customerAdapter = CustomerAdapter(this)
    private val viewModel: CustomerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            ItemTouchHelper(ItemSwipeCallback(context, customerAdapter))
                    .attachToRecyclerView(this)
            addItemDecoration(ItemDecoration(context))
            adapter = customerAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.customers.observe(viewLifecycleOwner) { customerAdapter.submitList(it) }
        viewModel.isEmpty.observe(viewLifecycleOwner) { binding.emptyView.isVisible = it }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun <T> onActionPerformed(t: T, action: BaseAdapter.ActionListener.Action) {
        if (t is Customer) {
            when(action) {
                BaseAdapter.ActionListener.Action.SELECT -> {
                    startActivityForResult(Intent(context, CustomerEditorActivity::class.java).apply {
                        putExtra(CustomerEditorActivity.EXTRA_CUSTOMER, t)
                    }, CustomerEditorActivity.REQUEST_CODE_INSERT)
                }
                BaseAdapter.ActionListener.Action.DELETE -> {
                    viewModel.remove(t)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        when(requestCode) {
            CustomerEditorActivity.REQUEST_CODE_UPDATE -> {
                data?.getParcelableExtra<Customer>(CustomerEditorActivity.EXTRA_CUSTOMER)?.also {
                    viewModel.update(it)
                }
            }
        }
    }
}