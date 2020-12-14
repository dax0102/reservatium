package me.asayah.reservatium.features.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.asayah.reservatium.databinding.LayoutItemCustomerBinding
import me.asayah.reservatium.features.shared.base.BaseAdapter

class CustomerAdapter(private val actionListener: ActionListener)
    : BaseAdapter<Customer, CustomerAdapter.CustomerViewHolder>(Customer.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = LayoutItemCustomerBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return CustomerViewHolder(binding.root, actionListener)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class CustomerViewHolder(itemView: View, private val actionListener: ActionListener)
        : BaseViewHolder(itemView) {
        private var binding = LayoutItemCustomerBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is Customer) {
                with(binding) {
                    titleView.text = t.getName()
                    root.setOnClickListener {
                        actionListener.onActionPerformed(t, ActionListener.Action.SELECT)
                    }
                }
            }
        }
    }
}