package com.nyx.mypurchases.ui.createlist.presenter

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.chip.Chip
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateListPresenter @Inject constructor(val categoryRepository: CategoryRepository) {

    private lateinit var view: CreateListView
    private lateinit var lifecycleCoroutineScope: LifecycleCoroutineScope
    var lastCreatedChipId: Long? = null

    var isEditModeEnabled = false
    private var isEditModeAvailable = false

    fun attachView(view: CreateListView, lifecycleCoroutineScope: LifecycleCoroutineScope) {
        this.view = view
        this.lifecycleCoroutineScope = lifecycleCoroutineScope

        refreshCategories()
    }

    fun onTitleFieldClick() {

    }

    fun onListFieldClick() {

    }

    fun onChipCategoryClick(chip: CategoryChipModel) {
        if (isEditModeEnabled && chip.isCustom) {
            lifecycleCoroutineScope.launch {
                CoroutineScope(Dispatchers.IO).launch {
                    categoryRepository.deleteCategory(chip)
                }.join()

                refreshCategories()
            }
        }

        view.toggleCustomCategoryFieldVisibility(
            isVisible = chip.id == 0L
        )
    }

    fun onNewCategoryChipClick(chip: Chip) {
        view.toggleCustomCategoryFieldVisibility(
            isVisible = chip.id == 0
        )
    }

    fun onAddCategoryClick(chipModel: CategoryChipModel) {
        lifecycleCoroutineScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                lastCreatedChipId = categoryRepository.insertCategory(chipModel)
            }.join()

            refreshCategories()
        }
    }

    fun toggleEditCategoriesMode() {
        isEditModeEnabled = !isEditModeEnabled

        lifecycleCoroutineScope.launch {
            view.toggleEditCategoriesMode(isEditModeEnabled)

            refreshCategories()
        }
    }

    private suspend fun getAllCategories(): List<CategoryChipModel> {
        return withContext(Dispatchers.IO) {
            categoryRepository.getAllCategories()
        }
    }

    private fun refreshCategories() {
        lifecycleCoroutineScope.launch {
            val allCategories = getAllCategories()
            isEditModeAvailable = allCategories.any { it.isCustom }
            view.setDeleteCategoryVisibility(isEditModeAvailable)
            view.setupChips(allCategories)
        }
    }
}