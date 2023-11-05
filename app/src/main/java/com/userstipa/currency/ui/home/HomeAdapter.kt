package com.userstipa.currency.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.userstipa.currency.R
import com.userstipa.currency.databinding.HomeItemListBinding
import com.userstipa.currency.domain.model.CurrencyPrice

class HomeAdapter(
    private val onClickCurrency: (currency: CurrencyPrice, view: View) -> Unit
) : RecyclerView.Adapter<HomeAdapter.Holder>() {

    private val diffUtil = AsyncListDiffer(this, DiffUtilCallback())

    var list: List<CurrencyPrice>
        get() = diffUtil.currentList
        set(value) = diffUtil.submitList(value)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

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
        val context = holder.itemView.context
        val transitionName = context.getString(R.string.shared_element_home_to_details, currency.id)
        val changePercent24HrColor = if (currency.isPositiveChangePercent24Hr) {
            context.getColor(R.color.positiveNumbersColor)
        } else {
            context.getColor(R.color.negativeNumbersColor)
        }
        with(holder.binding) {
            name.text = currency.name
            symbol.text = currency.symbol
            price.text = currency.priceUsd
            changePercent24Hr.text = currency.changePercent24Hr
            changePercent24Hr.setTextColor(changePercent24HrColor)
            cardView.transitionName = transitionName
            cardView.setOnClickListener { view ->
                onClickCurrency.invoke(currency, view)
            }
        }
    }

    inner class Holder(val binding: HomeItemListBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DiffUtilCallback : DiffUtil.ItemCallback<CurrencyPrice>() {
        override fun areItemsTheSame(
            oldItem: CurrencyPrice,
            newItem: CurrencyPrice
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CurrencyPrice,
            newItem: CurrencyPrice
        ): Boolean {
            return oldItem == newItem
        }

    }
}