package com.nyx.mypurchases.ui.viewingpurchases.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nyx.mypurchases.R
import com.nyx.mypurchases.domain.entity.ProductModel

private typealias OnProductClick = (productId: Int) -> Unit

internal class ProductsAdapter(val onProductClick: OnProductClick) :
    RecyclerView.Adapter<ProductsAdapter.PurchasesViewHolder>() {

//    var onProductClick: OnProductClick? = null
    private val products = mutableListOf<ProductModel>()
    val getProductsList get(): List<ProductModel> = products

    fun setProductsList(products: List<ProductModel>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    internal inner class PurchasesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                println("debug: ololo")
                onProductClick.invoke(products[adapterPosition].id)
            }
        }

      //  val cardView: CardView = itemView.findViewById(R.id.product_card_view)
        val title: TextView = itemView.findViewById(R.id.product_title)
      //  val checkbox: CheckBox = itemView.findViewById(R.id.product_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return PurchasesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PurchasesViewHolder, position: Int) {
        val item = products[position]
        holder.title.text = item.title
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun removeAt(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
    }
}