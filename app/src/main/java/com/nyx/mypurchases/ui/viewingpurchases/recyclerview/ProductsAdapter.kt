package com.nyx.mypurchases.ui.viewingpurchases.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nyx.mypurchases.R
import com.nyx.mypurchases.domain.entity.ProductModel

internal class ProductsAdapter(
    val onProductClick: (product: ProductModel, isChecked: Boolean, position: Int) -> Unit,
    val onScrollToTop: () -> Unit, // back deleted items (snackbar)
) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val products = mutableListOf<ProductModel>()
    val getProductsList get(): List<ProductModel> = products

    fun setProductsList(products: List<ProductModel>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    internal inner class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
                onProductClick.invoke(
                    products[adapterPosition],
                    checkbox.isChecked,
                    adapterPosition
                )
            }
        }

        val title: TextView = itemView.findViewById(R.id.product_title)
        val checkbox: CheckBox = itemView.findViewById(R.id.product_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = products[position]
        holder.title.text = item.title
        holder.checkbox.isChecked = item.isChecked
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun removeAt(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
    }

    fun moveCheckedItemToDown(product: ProductModel, position: Int) {

    }

    /* fun backDeletedItems(deletedProducts: MutableList<Pair<Int, ProductModel>>) {
         println("debug: $deletedProducts")
         for (product in deletedProducts) {
             products.add(product.first, product.second)
             notifyItemInserted(product.first)

             if (product.first == 0) {
                 onScrollToTop()
             }
         }

         deletedProducts.clear()
     }*/
}