package com.nyx.mypurchases.ui.main.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.nyx.mypurchases.R
import com.nyx.mypurchases.domain.entity.PurchaseModel


internal class PurchasesAdapter() :
    RecyclerView.Adapter<PurchasesAdapter.PurchasesViewHolder>() {

    private val purchases = mutableListOf<PurchaseModel>()

    fun setPurchasesList(purchases: List<PurchaseModel>) {
        this.purchases.clear()
        this.purchases.addAll(purchases)
        notifyDataSetChanged()
    }

    internal inner class PurchasesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = itemView.findViewById<TextView>(R.id.purchases_title)
        val purchasesList = itemView.findViewById<TextView>(R.id.purchases_list)
        val purchasesCategory = itemView.findViewById<Chip>(R.id.purchases_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.purchase_item, parent, false)
        return PurchasesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PurchasesViewHolder, position: Int) {
        val item = purchases[position]
        holder.title.text = item.title
        val products = buildString {
            item.purchases?.forEach {
                append(it)
            }
        }
        holder.purchasesList.text = products
        holder.purchasesCategory.text = item.category.title
    }

    override fun getItemCount(): Int {
        return purchases.size
    }
}