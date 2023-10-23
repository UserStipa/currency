package com.userstipa.currency.di

import com.userstipa.currency.ui.home.HomeViewModelFactory
import com.userstipa.currency.ui.search_currency.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideSearchViewModelFactory(): SearchViewModel.Factory {
        return SearchViewModel.Factory()
    }

    @Provides
    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory()
    }


}