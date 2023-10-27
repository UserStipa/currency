package com.userstipa.currency.di.ui_modules

import com.userstipa.currency.ui.home.HomeViewModelFactory
import com.userstipa.currency.ui.search_currency.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideSearchViewModelFactory(): SearchViewModelFactory {
        return SearchViewModelFactory()
    }

    @Provides
    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory()
    }

}