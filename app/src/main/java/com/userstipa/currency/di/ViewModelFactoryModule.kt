package com.userstipa.currency.di

import com.userstipa.currency.ui.search_currency.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideSearchViewModelFactory(): SearchViewModelFactory {
        return SearchViewModelFactory()
    }

}