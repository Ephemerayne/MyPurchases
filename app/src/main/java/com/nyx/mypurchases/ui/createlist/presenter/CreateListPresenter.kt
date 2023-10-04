package com.nyx.mypurchases.ui.createlist.presenter

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateListPresenter @Inject constructor(val categoryRepository: CategoryRepository){

    private lateinit var view: CreateListView
    private lateinit var lifecycleCoroutineScope: LifecycleCoroutineScope

    fun attachView(view: CreateListView) {
        this.view = view
    }

    fun setLifecycleScope(lifecycleCoroutineScope: LifecycleCoroutineScope) {
        this.lifecycleCoroutineScope = lifecycleCoroutineScope
    }

    val categoriesList: LiveData<List<CategoryChipModel>> =
        categoryRepository.getAllCategories().map {
            mutableListOf<CategoryChipModel>().apply {
                addAll(it)
                add(CategoryChipModel(0, "Новая"))
            }
        }

    fun onTitleFieldClick() {

    }

    fun onListFieldClick() {

    }

    fun onChipCategoryClick(chip: CategoryChipModel) {
        view.toggleCustomCategoryFieldVisibility(chip.title == "Новая")
    }

    fun onAddCategoryClick(chipModel: CategoryChipModel) {
        lifecycleCoroutineScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.insertCategory(chipModel)
            }
        }
    }
}