package com.nyx.mypurchases.ui.createlist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.R
import com.nyx.mypurchases.data.CategoriesDatabase
import com.nyx.mypurchases.data.CategoryDao
import com.nyx.mypurchases.data.CategoryRepositoryImpl
import com.nyx.mypurchases.databinding.FragmentCreateListBinding
import com.nyx.mypurchases.ui.createlist.presenter.CreateListPresenter
import com.nyx.mypurchases.ui.createlist.presenter.CreateListView
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel


class CreateListFragment : Fragment(), CreateListView {

    lateinit var presenter: CreateListPresenter
    private lateinit var categoryDatabase: CategoriesDatabase
    private lateinit var categoryDao: CategoryDao
    lateinit var categoryRepository: CategoryRepositoryImpl

    private var _binding: FragmentCreateListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryDatabase = CategoriesDatabase.getInstance(requireContext())
        categoryDao = categoryDatabase.categoryDao()
        categoryRepository = CategoryRepositoryImpl(categoryDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CreateListPresenter(this, categoryRepository, viewLifecycleOwner.lifecycleScope)

        presenter.categoriesList.observe(viewLifecycleOwner) { categories ->
            setupChips(categories)
        }

        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        (activity as? MainActivity)?.setActionBarTitle(getString(R.string.create_list_app_bar_title))
    }

    private fun setupChips(categories: List<CategoryChipModel>) {
        categories.forEachIndexed { index, chipModel ->
            val chip = Chip(this.context)
            chip.text = chipModel.title
            chip.isCheckable = true
            binding.chipGroup.addView(chip)

            println("debug: $chipModel index: $index")

            if (index == 0 && !chipModel.isCustom) {
                println("debug: FIRST")
                chip.isChecked = true
            } else {
                println("debug: SECOND")
                chip.isChecked = chipModel.isCustom
            }

            chip.setOnClickListener {
                onChipClick(chipModel)
            }
        }
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.addCategoryButton.setOnClickListener {
            onAddCategoryClick(
                CategoryChipModel(
                    0,
                    binding.categoryNameEditText.text.toString(),
                    true
                )
            )
            binding.chipGroup.removeAllViews()
        }
    }

    private fun onChipClick(chipModel: CategoryChipModel) {
        presenter.onChipCategoryClick(chipModel)
    }

    private fun onAddCategoryClick(chipModel: CategoryChipModel) {
        presenter.onAddCategoryClick(chipModel)
        toggleCustomCategoryFieldVisibility(false)
    }

    override fun toggleCustomCategoryFieldVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.createCustomCategoryField.visibility = View.VISIBLE
        } else {
            binding.createCustomCategoryField.visibility = View.GONE
            hideKeyboardFrom(requireContext(), requireView())
        }
    }
}

private fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}