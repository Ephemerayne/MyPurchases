package com.nyx.mypurchases.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nyx.mypurchases.App
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.R
import com.nyx.mypurchases.databinding.FragmentFirstBinding
import com.nyx.mypurchases.ui.main.presenter.MainPresenter
import com.nyx.mypurchases.ui.main.presenter.MainView
import javax.inject.Inject

class MainFragment : Fragment(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.injectMainFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this, viewLifecycleOwner.lifecycleScope)

        setButtonsListeners()

    }

    override fun onResume() {
        super.onResume()

        (activity as? MainActivity)?.setActionBarTitle(getString(R.string.main_app_bar_title))
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
}