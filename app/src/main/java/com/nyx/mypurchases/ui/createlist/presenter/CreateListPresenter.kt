package com.nyx.mypurchases.ui.createlist.presenter

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.chip.Chip
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel
import com.nyx.mypurchases.ui.createlist.presenter.models.ProductsListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateListPresenter @Inject constructor(val categoryRepository: CategoryRepository) {

    private lateinit var view: CreateListView
    private lateinit var lifecycleCoroutineScope: LifecycleCoroutineScope
    private var checkedCategoryId: Int? = null

    private var isEditModeEnabled = false
    private var isEditModeAvailable = false

    fun attachView(view: CreateListView, lifecycleCoroutineScope: LifecycleCoroutineScope) {
        this.view = view
        this.lifecycleCoroutineScope = lifecycleCoroutineScope

        view.setupTitleFieldView(0, LIST_TITLE_MAX_CHARS)

        refreshCategories()
    }

    fun onChipCategoryClick(chip: CategoryChipModel) {
        lifecycleCoroutineScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                if (isEditModeEnabled && chip.isCustom) {
                    categoryRepository.deleteCategory(chip)
                } else {
                    checkedCategoryId = chip.id
                    productsListModel = productsListModel.copy(categoryChipModel = chip)
                }
            }.join()

            refreshCategories()
        }

        view.toggleCustomCategoryFieldVisibility(false)
    }

    fun onNewCategoryChipClick(chip: Chip) {
        productsListModel = productsListModel.copy(
            categoryChipModel = CategoryChipModel(
                chip.id,
                chip.text.toString()
            )
        )
        view.toggleCustomCategoryFieldVisibility(
            isVisible = chip.id == 0
        )

        setupCreateListButton()
    }

    fun onAddCategoryClick(categoryTitle: String) {
        lifecycleCoroutineScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val newCategory = CategoryChipModel(title = categoryTitle, isCustom = true)
                checkedCategoryId = categoryRepository.insertCategory(newCategory).toInt()
                productsListModel = productsListModel.copy(categoryChipModel = newCategory)
            }.join()

            refreshCategories()
        }

        view.toggleCustomCategoryFieldVisibility(false)
    }

    fun setNewCategoryTitle(categoryTitle: String) {
        view.addCategoryButtonClickable(isEnabled = categoryTitle.isNotBlank())
    }

    private fun setupCreateListButton() {
        view.createListButtonClickable(isEnabled = isCreateListButtonEnabled())
    }

    fun getTitleText(text: String) {
        productsListModel = productsListModel.copy(title = text.trim())
        view.setupTitleFieldView(text.length, LIST_TITLE_MAX_CHARS)

        setupCreateListButton()
    }

    fun getProductsText(text: String) {
        val productsList = text.trim().split(",")
        productsListModel = productsListModel.copy(products = productsList)

        setupCreateListButton()
    }

    // вкл/выкл иконку editMode
    fun toggleEditCategoriesMode() {
        checkedCategoryId = null
        isEditModeEnabled = !isEditModeEnabled

        // смена стиля переключателя
        view.toggleEditCategoriesMode(isEditModeEnabled)

        // видимость поля с кастомным тайтлом категории
        view.toggleCustomCategoryFieldVisibility(false)
        refreshCategories()
    }

    private suspend fun getAllCategories(): List<CategoryChipModel> {
        return withContext(Dispatchers.IO) {
            categoryRepository.getAllCategories()
        }
    }

    private fun refreshCategories() {
        lifecycleCoroutineScope.launch {
            val categories = withContext(Dispatchers.IO) {
                getAllCategories()
            }
            checkedCategoryId = checkedCategoryId ?: categories.firstOrNull()?.id

            // иконка EditMode доступна, если есть в репе хотя бы 1 кастомная категория
            isEditModeAvailable = categories.any { it.isCustom }

            if (!isEditModeAvailable) {
                isEditModeEnabled = false
            }

            categories.firstOrNull { it.id == checkedCategoryId }?.let { chip ->
                productsListModel = productsListModel.copy(categoryChipModel = chip)
            }

            // видимость иконки editMode
            view.setDeleteCategoryVisibility(isEditModeAvailable)

            // визуальное переключение иконки EditMode
            view.toggleEditCategoriesMode(isEditModeEnabled)

            view.setupChips(categories, checkedCategoryId, isEditModeEnabled)

            setupCreateListButton()
        }
    }

    fun createList() {
        println("debug: $productsListModel")
        // отправка в репо productsListModel
    }

    companion object {
        private const val LIST_TITLE_MAX_CHARS = 100

        private var productsListModel: ProductsListModel = ProductsListModel()

        private fun isCreateListButtonEnabled(): Boolean {
            return with(productsListModel) {
                products?.isNotEmpty() == true && title.isNotBlank() && categoryChipModel.id != 0
            }
        }
    }
}