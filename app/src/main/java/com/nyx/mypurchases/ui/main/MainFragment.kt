package com.nyx.mypurchases.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.nyx.mypurchases.App
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.R
import com.nyx.mypurchases.databinding.FragmentMainBinding
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.ui.main.presenter.MainPresenter
import com.nyx.mypurchases.ui.main.presenter.MainView
import com.nyx.mypurchases.ui.main.recyclerview.PurchasesAdapter
import javax.inject.Inject

class MainFragment : Fragment(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var purchasesAdapter: PurchasesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.injectMainFragment(this)
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

        binding.purchasesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            purchasesAdapter = PurchasesAdapter()
            adapter = purchasesAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        presenter.attachView(this, viewLifecycleOwner.lifecycleScope)

        setButtonsListeners()

    }

    override fun onResume() {
        super.onResume()

        (activity as? MainActivity)?.setActionBarTitle(getString(R.string.main_app_bar_title))
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