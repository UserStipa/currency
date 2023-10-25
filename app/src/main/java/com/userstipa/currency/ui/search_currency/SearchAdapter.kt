package com.userstipa.currency.ui.search_currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.userstipa.currency.databinding.HomeItemListBinding
import com.userstipa.currency.domain.model.Currency

interface SearchAdapterListener {
    fun onClickAddCurrency(currency: Currency)
    fun onClickRemoveCurrency(currency: Currency)
}

class SearchAdapter(
    private val listener: SearchAdapterListener
) : RecyclerView.Adapter<SearchAdapter.Holder>() {

    private val diffUtil = AsyncListDiffer(this, DiffUtilCallback())

    var list: List<Currency>
        get() = diffUtil.currentList
        set(value) = diffUtil.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            HomeItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = list[position]
        with(holder.binding) {
            name.text = currency.name
            symbol.text = currency.symbol
            checkbox.isChecked = currency.isEnableCheckbox
            checkbox.setOnClickListener {
                if (checkbox.isChecked) {
                    listener.onClickAddCurrency(currency)
                    currency.isEnableCheckbox = true
                } else {
                    listener.onClickRemoveCurrency(currency)
                    currency.isEnableCheckbox = false
                }
                notifyItemChanged(holder.adapterPosition)
            }
        }
    }

    inner class Holder(val binding: HomeItemListBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DiffUtilCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }

    }
}