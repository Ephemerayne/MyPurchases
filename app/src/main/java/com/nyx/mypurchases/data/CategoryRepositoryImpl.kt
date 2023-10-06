package com.nyx.mypurchases.data

import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.ui.createlist.presenter.models.CategoryChipModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dao: CategoryDao) :
    CategoryRepository {

    override fun insertCategory(category: CategoryChipModel): Long {
        return dao.insertCategory(category)
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

    override fun getAllCategories():List<CategoryChipModel> {
        return dao.getAllCategories()
    }
}