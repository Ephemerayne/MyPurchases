package com.nyx.mypurchases.ui.createlist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.nyx.mypurchases.App
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.R
import com.nyx.mypurchases.databinding.FragmentCreateListBinding
import com.nyx.mypurchases.extensions.setTint
import com.nyx.mypurchases.ui.createlist.presenter.CreateListPresenter
import com.nyx.mypurchases.ui.createlist.presenter.CreateListView
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel
import javax.inject.Inject


class CreateListFragment : Fragment(), CreateListView {

    @Inject
    lateinit var presenter: CreateListPresenter

    private var _binding: FragmentCreateListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.injectFragment(this)
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

        presenter.attachView(this, viewLifecycleOwner.lifecycleScope)

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

    override fun setupChips(categories: List<CategoryChipModel>) {
        binding.chipGroup.removeAllViews()

        categories.forEachIndexed { index, chipModel ->
            val chip = Chip(this.context)
            chip.text = chipModel.title

            chip.isCheckable = true

            chip.isChecked = index == 0
            chip.isChecked = chipModel.id == presenter.lastCreatedChipId

            chip.closeIcon = getDrawable(requireContext(), android.R.drawable.ic_menu_delete)
            chip.isCloseIconVisible = presenter.isEditModeEnabled && chipModel.isCustom

            binding.chipGroup.addView(chip)

            chip.setOnClickListener {
                onChipClick(chipModel)
            }
        }

        setupNewCategoryChip()
    }

    private fun setupNewCategoryChip() {
        val newChip = Chip(this.context)
        newChip.id = 0
        newChip.text = getString(R.string.new_category_text)
        binding.chipGroup.addView(newChip)

        newChip.setOnClickListener {
            onNewCategoryChipClick(newChip)
        }
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.editCategoriesIcon.setOnClickListener {
            activateEditCategoriesMode()
        }

        binding.addCategoryButton.setOnClickListener {
            onAddCategoryClick(
                CategoryChipModel(
                    id = 0,
                    title = binding.categoryNameEditText.text.toString(),
                    isCustom = true
                )
            )
        }
    }

    private fun onChipClick(chipModel: CategoryChipModel) {
        presenter.onChipCategoryClick(chipModel)
    }

    private fun onNewCategoryChipClick(chip: Chip) {
        presenter.onNewCategoryChipClick(chip)
    }

    private fun onAddCategoryClick(chipModel: CategoryChipModel) {
        presenter.onAddCategoryClick(chipModel)
        toggleCustomCategoryFieldVisibility(false)
    }

    private fun activateEditCategoriesMode() {
        presenter.toggleEditCategoriesMode()
    }

    override fun toggleCustomCategoryFieldVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.createCustomCategoryField.visibility = View.VISIBLE
        } else {
            binding.createCustomCategoryField.visibility = View.GONE
            hideKeyboardFrom(requireContext(), requireView())
        }
    }

    override fun setDeleteCategoryVisibility(isVisible: Boolean) {
        binding.editCategoriesIcon.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun toggleEditCategoriesMode(isEnabled: Boolean) {
        val color = if (isEnabled) R.color.black else null
        binding.editCategoriesIcon.setTint(color)
    }
}

private fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}