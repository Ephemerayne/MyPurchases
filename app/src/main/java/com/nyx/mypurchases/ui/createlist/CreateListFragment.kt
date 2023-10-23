package com.nyx.mypurchases.ui.createlist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.nyx.mypurchases.App
import com.nyx.mypurchases.MainActivity
import com.nyx.mypurchases.R
import com.nyx.mypurchases.databinding.FragmentCreateListBinding
import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.ui.createlist.presenter.CreateListPresenter
import com.nyx.mypurchases.ui.createlist.presenter.CreateListView
import com.nyx.mypurchases.utils.setTint
import javax.inject.Inject


class CreateListFragment : Fragment(), CreateListView {

    @Inject
    lateinit var presenter: CreateListPresenter

    private var _binding: FragmentCreateListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.injectCreateListFragment(this)
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

        setButtonsListeners()
        setEditTextFieldsListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        (activity as? MainActivity)?.setupActionBar(getString(R.string.create_list_app_bar_title))
    }


    // update 1 chips not all
    override fun setupChips(
        categories: List<CategoryModel>,
        checkedId: Int?,
        isEditModeEnabled: Boolean,
    ) {
        binding.chipGroup.removeAllViews()

        categories.forEachIndexed { index, category ->
            val chip = Chip(this.context)
            chip.text = category.title
            chip.isCheckable = true

            if (!isEditModeEnabled) {
                chip.isChecked = index == 0
                if (checkedId != null) {
                    chip.isChecked = category.id == checkedId
                }
            }

            chip.closeIcon = getDrawable(requireContext(), android.R.drawable.ic_menu_delete)
            chip.isCloseIconVisible = isEditModeEnabled && category.isCustom

            binding.chipGroup.addView(chip)

            chip.setOnClickListener {
                onChipClick(category)
            }
        }

        setupNewCategoryChip(isEditModeEnabled)
    }

    private fun setupNewCategoryChip(isEditModeEnabled: Boolean) {
        val newChip = Chip(this.context)
        newChip.id = 0
        newChip.isCheckable = true
        newChip.text = getString(R.string.new_category_text)
        newChip.visibility = if (isEditModeEnabled) View.GONE else View.VISIBLE
        binding.chipGroup.addView(newChip)

        newChip.setOnClickListener {
            onNewCategoryChipClick(newChip)
        }
    }

    private fun setButtonsListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.editCategoriesIcon.setOnClickListener {
            presenter.toggleEditCategoriesMode()
        }

        binding.addCategoryButton.setOnClickListener {
            presenter.onAddCategoryClick(binding.categoryNameEditText.text.toString().trim())
        }

        binding.createListButton.setOnClickListener {
            presenter.createList()
        }
    }

    private fun setEditTextFieldsListeners() {
        binding.titleEditText.addTextChangedListener {
            presenter.getTitleText(it.toString())
        }

        binding.listEditText.addTextChangedListener {
            presenter.getProductsText(it.toString())
        }

        binding.categoryNameEditText.addTextChangedListener {
            presenter.setNewCategoryTitle(it.toString())
        }
    }

    override fun addCategoryButtonClickable(isEnabled: Boolean) {
        binding.addCategoryButton.isEnabled = isEnabled
    }

    override fun createListButtonClickable(isEnabled: Boolean) {
        binding.createListButton.isEnabled = isEnabled
    }

    override fun setupTitleFieldView(currentChars: Int, maxChars: Int) {
        binding.titleEditText.filters = arrayOf(InputFilter.LengthFilter(maxChars))
        binding.titleTextLimit.text = getString(R.string.limit_chars_field, currentChars, maxChars)
    }

    override fun setupCategoryTitleFieldView(currentChars: Int, maxChars: Int) {
        binding.categoryNameEditText.filters = arrayOf(InputFilter.LengthFilter(maxChars))
        binding.categoryTitleLimit.text =
            getString(R.string.limit_chars_field, currentChars, maxChars)
    }

    private fun onChipClick(chipModel: CategoryModel) {
        presenter.onChipCategoryClick(chipModel)
    }

    private fun onNewCategoryChipClick(chip: Chip) {
        binding.categoryNameEditText.text?.clear()

        presenter.onNewCategoryChipClick(chip)
    }

    override fun toggleCustomCategoryFieldVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.createCustomCategoryField.visibility = View.VISIBLE
        } else {
            binding.createCustomCategoryField.visibility = View.GONE
            hideKeyboardFrom(requireContext(), requireView())
        }
    }

    override fun setDeleteCategoryVisibility(isAvailable: Boolean) {
        binding.editCategoriesIcon.visibility = if (isAvailable) View.VISIBLE else View.GONE
    }

    override fun toggleEditCategoriesMode(isEnabled: Boolean) {
        val color = if (isEnabled) R.color.black else null
        binding.editCategoriesIcon.setTint(color)
    }

    override fun backToMainScreen() {
        findNavController().popBackStack()
    }
}

private fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}