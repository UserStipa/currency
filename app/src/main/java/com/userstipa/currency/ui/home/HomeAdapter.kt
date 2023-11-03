package com.userstipa.currency.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.userstipa.currency.R
import com.userstipa.currency.databinding.HomeItemListBinding
import com.userstipa.currency.domain.model.CurrencyPriceDetail


interface HomeAdapterListener {
    fun onClickCurrency(view: View, currencyId: String)
}


class HomeAdapter(
    context: Context,
    private val listener: HomeAdapterListener,
    private val onLayoutReady: (listSize: Int) -> Unit
) : RecyclerView.Adapter<HomeAdapter.Holder>() {

    private val diffUtil = AsyncListDiffer(this, DiffUtilCallback())
    private val colorPositiveNumber = context.getColor(R.color.positiveNumbersColor)
    private val colorNegativeNumber = context.getColor(R.color.negativeNumbersColor)

    var list: List<CurrencyPriceDetail>
        get() = diffUtil.currentList
        set(value) = diffUtil.submitList(value)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (list.isNotEmpty()) {
                    onLayoutReady.invoke(list.size)
                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
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
        Log.d("TAG", "onBindViewHolder: ${currency}")
        val changePercent24HrColor = if (currency.isPositiveChangePercent24Hr) {
            colorPositiveNumber
        } else {
            colorNegativeNumber
        }
        with(holder.binding) {
            name.text = currency.name
            symbol.text = currency.symbol
            price.text = currency.priceUsd
            changePercent24Hr.text = currency.changePercent24Hr
            cardView.transitionName = currency.id
            changePercent24Hr.setTextColor(changePercent24HrColor)
        }
        holder.itemView.setOnClickListener { view ->
            listener.onClickCurrency(view, currency.id)
        }
    }

    inner class Holder(val binding: HomeItemListBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DiffUtilCallback : DiffUtil.ItemCallback<CurrencyPriceDetail>() {
        override fun areItemsTheSame(
            oldItem: CurrencyPriceDetail,
            newItem: CurrencyPriceDetail
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CurrencyPriceDetail,
            newItem: CurrencyPriceDetail
        ): Boolean {
            return oldItem == newItem
        }

    }
}