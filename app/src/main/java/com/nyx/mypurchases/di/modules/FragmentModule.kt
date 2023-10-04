package com.nyx.mypurchases.di.modules

import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.ui.createlist.presenter.CreateListPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideCreateListPresenter(categoryRepository: CategoryRepository) =
        CreateListPresenter(categoryRepository)
}