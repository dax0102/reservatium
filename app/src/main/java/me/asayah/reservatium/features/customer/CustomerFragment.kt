package me.asayah.reservatium.features.customer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.databinding.FragmentCustomerBinding
import me.asayah.reservatium.features.customer.editor.CustomerEditorActivity
import me.asayah.reservatium.features.shared.base.BaseFragment

@AndroidEntryPoint
class CustomerFragment: BaseFragment() {
    private var _binding: FragmentCustomerBinding? = null

    private val binding get() = _binding!!
    private val customerAdapter = CustomerAdapter()
    private val viewModel: CustomerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = customerAdapter

        binding.actionButton.setOnClickListener {
            startActivityForResult(Intent(it.context, CustomerEditorActivity::class.java),
                CustomerEditorActivity.REQUEST_CODE_INSERT)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.customers.observe(viewLifecycleOwner) { customerAdapter.submitList(it) }
        viewModel.isEmpty.observe(viewLifecycleOwner) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        when(requestCode) {
            CustomerEditorActivity.REQUEST_CODE_INSERT -> {
                data?.getParcelableExtra<Customer>(CustomerEditorActivity.EXTRA_CUSTOMER)?.also {
                    viewModel.insert(it)
                }
            }
            CustomerEditorActivity.REQUEST_CODE_UPDATE -> {
                data?.getParcelableExtra<Customer>(CustomerEditorActivity.EXTRA_CUSTOMER)?.also {
                    viewModel.update(it)
                }
            }
        }
    }
}