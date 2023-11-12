package com.userstipa.currency.ui.search_currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.userstipa.currency.databinding.SearchItemListBinding
import com.userstipa.currency.domain.model.Currency

class SearchAdapter(
    private val onClickAddCurrency: (currency: Currency) -> Unit,
    private val onClickRemoveCurrency: (currency: Currency) -> Unit
) : RecyclerView.Adapter<SearchAdapter.Holder>() {

    private val diffUtil = AsyncListDiffer(this, DiffUtilCallback())

    var list: List<Currency>
        get() = diffUtil.currentList
        set(value) = diffUtil.submitList(value)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            SearchItemListBinding.inflate(
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
                    list[holder.adapterPosition].isEnableCheckbox = true
                    onClickAddCurrency(currency)
                } else {
                    list[holder.adapterPosition].isEnableCheckbox = false
                    onClickRemoveCurrency(currency)
                }
            }
        }
    }

    inner class Holder(val binding: SearchItemListBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DiffUtilCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }

    }
}