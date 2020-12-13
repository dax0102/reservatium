package me.asayah.reservatium.features.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.databinding.LayoutItemCustomerBinding
import me.asayah.reservatium.features.shared.base.BaseAdapter

class CustomerAdapter: BaseAdapter<Customer, CustomerAdapter.CustomerViewHolder>(Customer.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = LayoutItemCustomerBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return CustomerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class CustomerViewHolder(itemView: View): BaseViewHolder(itemView) {
        private var binding = LayoutItemCustomerBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is Customer)
                binding.titleView.text = StringBuilder()
                        .append(t.firstName)
                        .append(" ")
                        .append(t.lastName)
        }
    }
}