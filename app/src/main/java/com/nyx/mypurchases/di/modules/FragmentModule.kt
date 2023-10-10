package com.nyx.mypurchases.di.modules

import com.nyx.mypurchases.domain.reposinterfaces.CategoryRepository
import com.nyx.mypurchases.domain.reposinterfaces.PurchaseRepository
import com.nyx.mypurchases.ui.createlist.presenter.CreateListPresenter
import com.nyx.mypurchases.ui.main.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideCreateListPresenter(
        categoryRepository: CategoryRepository,
        purchaseRepository: PurchaseRepository,
    ) =
        CreateListPresenter(categoryRepository, purchaseRepository)

    @Provides
    fun provideMainPresenter(purchaseRepository: PurchaseRepository) =
        MainPresenter(purchaseRepository)
}