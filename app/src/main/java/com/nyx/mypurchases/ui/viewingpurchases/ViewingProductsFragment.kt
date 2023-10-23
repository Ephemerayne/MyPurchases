package com.nyx.mypurchases.ui.viewingpurchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyx.mypurchases.App
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.R
import com.nyx.mypurchases.common.CustomSnackbar
import com.nyx.mypurchases.common.swipehandler.SwipeHandler
import com.nyx.mypurchases.databinding.FragmentViewingProductsBinding
import com.nyx.mypurchases.domain.entity.ProductModel
import com.nyx.mypurchases.ui.viewingpurchases.recyclerview.ProductsAdapter
import com.nyx.mypurchases.ui.viewingpurchases.viewModel.ViewingProductsViewModel
import javax.inject.Inject

class ViewingProductsFragment : Fragment() {

    @Inject
    lateinit var viewModel: ViewingProductsViewModel
    var purchaseId: Int? = 0

    private var _binding: FragmentViewingProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var swipeHandler: SwipeHandler
    private lateinit var snackbar: CustomSnackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        swipeHandler = SwipeHandler(requireContext())
        snackbar = CustomSnackbar()
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
        initSwipeHandler(view)

        purchaseId = arguments?.getInt(ARGS_KEY)

        purchaseId?.let {
            viewModel.getPurchaseInfo(it).observe(viewLifecycleOwner) { purchase ->
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
            productsAdapter = ProductsAdapter(onProductClick = { product, isChecked ->
                purchaseId?.let {
                    viewModel.toggleProductCheck(it, product, isChecked)
                }
            },
                onScrollToTop = {
                    scrollToPosition(0)
                })

            adapter = productsAdapter
        }
    }

    private fun initSwipeHandler(view: View) {
        swipeHandler.init<ProductsAdapter.ProductsViewHolder>(
            recyclerView = binding.productsRecyclerView,
            onSwiped = { viewHolder, _ ->
                val product = productsAdapter.getProductsList[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                productsAdapter.removeAt(position)

                onSnackBar(view, Pair(position, product))

                viewModel.removeProduct(product)
            })
    }

    private fun onSnackBar(view: View, product: Pair<Int, ProductModel>) {
        snackbar.createActionSnackbar(
            view = view,
            actionText = getString(R.string.delete_item_snackbar_action),
            duration = SNACKBAR_DURATION,
            action = {
                productsAdapter.backDeletedItem(product)
                viewModel.cancelDeletion()
            }
        )
    }

    companion object {
        const val ARGS_KEY = "KEY"
        const val SNACKBAR_DURATION = 5000
    }
}