package com.nyx.mypurchases.di

import androidx.lifecycle.ViewModel
import com.nyx.mypurchases.ui.viewingpurchases.viewModel.ViewingProductsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ViewingProductsViewModel::class)
    abstract fun viewingProductsFragmentViewModel(viewModel: ViewingProductsViewModel): ViewModel
}