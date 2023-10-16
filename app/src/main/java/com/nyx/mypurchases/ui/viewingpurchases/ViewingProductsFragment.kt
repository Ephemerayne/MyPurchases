package com.nyx.mypurchases.ui.viewingpurchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyx.mypurchases.App
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.databinding.FragmentViewingProductsBinding
import com.nyx.mypurchases.ui.viewingpurchases.recyclerview.ProductsAdapter
import com.nyx.mypurchases.ui.viewingpurchases.viewModel.ViewingProductsViewModel
import javax.inject.Inject

class ViewingProductsFragment : Fragment() {

    @Inject
    lateinit var viewModel: ViewingProductsViewModel

    private var _binding: FragmentViewingProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentViewingProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.appComponent.inject(this)

        setupProductsRecyclerAdapter()

        val purchaseId = arguments?.getInt(ARGS_KEY)

        if (purchaseId != null) {
            viewModel.getPurchases(purchaseId).observe(viewLifecycleOwner) { purchase ->
                setAppBarTitle(purchase.title)
                productsAdapter.setProductsList(purchase.products)
            }
        }
    }

    private fun setAppBarTitle(title: String) {
        (activity as? MainActivity)?.setActionBarTitle(title)
    }

    private fun setupProductsRecyclerAdapter() {
        binding.productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            productsAdapter = ProductsAdapter { productId ->
                println("debug: $productId")
                // checked-unchecked
            }
            adapter = productsAdapter
        }
    }


    companion object {
        const val ARGS_KEY = "KEY"
    }
}