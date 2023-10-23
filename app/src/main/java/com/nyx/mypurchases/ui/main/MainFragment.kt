package com.nyx.mypurchases.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyx.mypurchases.App
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.R
import com.nyx.mypurchases.common.swipehandler.SwipeHandler
import com.nyx.mypurchases.databinding.FragmentMainBinding
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.ui.main.presenter.MainPresenter
import com.nyx.mypurchases.ui.main.presenter.MainView
import com.nyx.mypurchases.ui.main.recyclerview.PurchasesAdapter
import com.nyx.mypurchases.ui.viewingpurchases.ViewingProductsFragment.Companion.ARGS_KEY
import javax.inject.Inject

class MainFragment : Fragment(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var purchasesAdapter: PurchasesAdapter
    private lateinit var swipeHandler: SwipeHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.injectMainFragment(this)

        swipeHandler = SwipeHandler(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPurchasesRecyclerAdapter()
        initSwipeHandler()
        setButtonsListeners()

        presenter.attachView(this, viewLifecycleOwner.lifecycleScope)
    }

    private fun setupPurchasesRecyclerAdapter() {
        binding.purchasesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            purchasesAdapter = PurchasesAdapter()
            adapter = purchasesAdapter
        }

        purchasesAdapter.onPurchaseClick = { purchaseId ->
            Bundle().apply {
                putInt(ARGS_KEY, purchaseId)

                findNavController().navigate(
                    R.id.action_FirstFragment_to_viewingPurchasesFragment,
                    this
                )
            }
        }
    }

    private fun initSwipeHandler() {
        swipeHandler.init<PurchasesAdapter.PurchasesViewHolder>(
            recyclerView = binding.purchasesListRecyclerView,
            cornerRadiusDp = 8,
            onSwiped = { viewHolder, _ ->
                val purchase = purchasesAdapter.getPurchasesList[viewHolder.adapterPosition]
                purchasesAdapter.removeAt(viewHolder.adapterPosition)

                presenter.removePurchase(purchase.id)
            }
        )
    }

    override fun onResume() {
        super.onResume()

        (activity as? MainActivity)?.setupActionBar(getString(R.string.main_app_bar_title))
        println("debug: ONRESUME")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setButtonsListeners() {
        binding.addList.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun setupPurchasesList(purchases: List<PurchaseModel>) {
        purchasesAdapter.setPurchasesList(purchases)
    }
}