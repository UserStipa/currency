package com.userstipa.currency.di

import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrencies
import com.userstipa.currency.ui.home.HomeFragment
import com.userstipa.currency.ui.search_currency.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataModule::class, ViewModelFactoryModule::class, DomainModule::class, DispatcherModule::class])
@Singleton
interface AppComponent {

    fun getRemoteCurrencies(): GetRemoteCurrencies

    fun getDispatcherProvider(): DispatcherProvider

    fun inject(homeFragment: HomeFragment)

    fun inject(searchFragment: SearchFragment)

}