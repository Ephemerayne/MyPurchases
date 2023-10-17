package com.nyx.mypurchases.ui.createlist.presenter

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.chip.Chip
import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.domain.entity.ProductModel
import com.nyx.mypurchases.domain.entity.PurchaseModel
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateListPresenter @Inject constructor(
    val categoryRepository: CategoryRepository,
    val purchaseRepository: PurchaseRepository,
) {

    private lateinit var view: CreateListView
    private lateinit var lifecycleCoroutineScope: LifecycleCoroutineScope
    private var checkedCategoryId: Int? = null

    private var isEditModeEnabled = false
    private var isEditModeAvailable = false

    fun attachView(view: CreateListView, lifecycleCoroutineScope: LifecycleCoroutineScope) {
        this.view = view
        this.lifecycleCoroutineScope = lifecycleCoroutineScope

        view.setupTitleFieldView(0, PURCHASES_TITLE_MAX_CHARS)

        refreshCategories()
    }

    fun onChipCategoryClick(chip: CategoryModel) {
        lifecycleCoroutineScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                if (isEditModeEnabled && chip.isCustom) {
                    categoryRepository.deleteCategory(chip)
                } else {
                    checkedCategoryId = chip.id
                    purchaseModel = purchaseModel.copy(category = chip)
                }
            }.join()

            refreshCategories()
        }

        view.toggleCustomCategoryFieldVisibility(false)
    }

    fun onNewCategoryChipClick(chip: Chip) {
        purchaseModel = purchaseModel.copy(
            category = CategoryModel(
                chip.id,
                chip.text.toString()
            )
        )
        view.toggleCustomCategoryFieldVisibility(
            isVisible = chip.id == 0
        )

        view.setupCategoryTitleFieldView(0, CATEGORY_TITLE_MAX_CHARS)

        setupCreateListButton()
    }

    fun onAddCategoryClick(categoryTitle: String) {
        lifecycleCoroutineScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val newCategory = CategoryModel(title = categoryTitle, isCustom = true)
                checkedCategoryId = categoryRepository.insertCategory(newCategory).toInt()
                purchaseModel = purchaseModel.copy(category = newCategory)
            }.join()

            refreshCategories()
        }

        view.toggleCustomCategoryFieldVisibility(false)
    }

    fun setNewCategoryTitle(categoryTitle: String) {
        view.setupCategoryTitleFieldView(categoryTitle.length, CATEGORY_TITLE_MAX_CHARS)
        view.addCategoryButtonClickable(isEnabled = categoryTitle.isNotBlank())
    }

    private fun setupCreateListButton() {
        view.createListButtonClickable(isEnabled = isCreateListButtonEnabled())
    }

    fun getTitleText(text: String) {
        purchaseModel = purchaseModel.copy(title = text.trim())
        view.setupTitleFieldView(text.length, PURCHASES_TITLE_MAX_CHARS)

        setupCreateListButton()
    }

    fun getProductsText(text: String) {
        val productsList = text.trim().split(",")
        purchaseModel = purchaseModel.copy(
            products = productsList.map { ProductModel(title = it) }
        )

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

    private suspend fun getAllCategories(): List<CategoryModel> {
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
                purchaseModel = purchaseModel.copy(category = chip)
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
        lifecycleCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                purchaseRepository.savePurchase(purchaseModel, purchaseModel.products)
            }

            view.backToMainScreen()
        }
    }

    companion object {
        private const val PURCHASES_TITLE_MAX_CHARS = 100
        private const val CATEGORY_TITLE_MAX_CHARS = 20

        private var purchaseModel: PurchaseModel = PurchaseModel()

        private fun isCreateListButtonEnabled(): Boolean {
            return with(purchaseModel) {
                products.isNotEmpty() && title.isNotBlank() && category.id != 0
            }
        }
    }
}