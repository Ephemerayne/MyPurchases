package com.nyx.mypurchases.data.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.nyx.mypurchases.data.mappers.toEntity
import com.nyx.mypurchases.data.mappers.toModel
import com.nyx.mypurchases.domain.entity.CategoryModel
import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dao: CategoryDao) :
    CategoryRepository {

    override fun insertCategory(category: CategoryModel): Long {
        return dao.insertCategory(category.toEntity())
    }

    override fun updateCategory(category: CategoryModel) {
        dao.updateCategory(category.toEntity())
    }

    override fun deleteCategory(category: CategoryModel) {
        dao.deleteCategory(category.toEntity())
    }

    override fun deleteAllCustomCategories() {
        dao.deleteAllCustomCategories()
    }

    override fun getAllCategories(): List<CategoryModel> {
        return dao.getAllCategories().map { it.toModel() }
    }

    override fun getAllCategoriesLiveData(): LiveData<List<CategoryModel>> {
        return dao.getAllCategoriesLiveData().map { it.map { it.toModel() } }
    }
}