package com.nyx.mypurchases.data.categories

import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dao: CategoryDao) :
    CategoryRepository {

    override fun insertCategory(category: CategoryModel): Long {
        return dao.insertCategory(category)
    }

    override fun updateCategory(category: CategoryModel) {
        dao.updateCategory(category)
    }

    override fun deleteCategory(category: CategoryModel) {
        dao.deleteCategory(category)
    }

    override fun deleteAllCustomCategories() {
        dao.deleteAllCustomCategories()
    }

    override fun getAllCategories():List<CategoryModel> {
        return dao.getAllCategories()
    }
}