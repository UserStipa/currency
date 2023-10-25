package com.userstipa.currency.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.userstipa.currency.databinding.HomeItemListBinding
import com.userstipa.currency.domain.model.CurrencyPrice

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.Holder>() {

    private val diffUtil = AsyncListDiffer(this, DiffUtilCallback())

    var list: List<CurrencyPrice>
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
            price.text = currency.priceUsd
        }
    }

    inner class Holder(val binding: HomeItemListBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DiffUtilCallback : DiffUtil.ItemCallback<CurrencyPrice>() {
        override fun areItemsTheSame(oldItem: CurrencyPrice, newItem: CurrencyPrice): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CurrencyPrice, newItem: CurrencyPrice): Boolean {
            return oldItem == newItem
        }

    }
}