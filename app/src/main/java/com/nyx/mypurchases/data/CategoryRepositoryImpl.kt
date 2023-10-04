package com.nyx.mypurchases.data

import androidx.lifecycle.LiveData
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dao: CategoryDao) :
    CategoryRepository {

    override fun insertCategory(category: CategoryChipModel) {
        dao.insertCategory(category)
    }

    override fun updateCategory(category: CategoryChipModel) {
        dao.updateCategory(category)
    }

    override fun deleteCategory(category: CategoryChipModel) {
        dao.deleteCategory(category)
    }

    override fun deleteAllCustomCategories() {
        dao.deleteAllCustomCategories()
    }

    override fun getAllCategories(): LiveData<List<CategoryChipModel>> {
        return dao.getAllCategories()
    }
}