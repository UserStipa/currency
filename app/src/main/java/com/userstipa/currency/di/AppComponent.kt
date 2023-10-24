package com.userstipa.currency.di

import android.content.Context
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.domain.usecases.add_currency.AddCurrency
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrencies
import com.userstipa.currency.ui.home.HomeFragment
import com.userstipa.currency.ui.search_currency.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataModule::class, ViewModelFactoryModule::class, DomainModule::class, DispatcherModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getRemoteCurrencies(): GetRemoteCurrencies

    fun getAddCurrency(): AddCurrency

    fun getDispatcherProvider(): DispatcherProvider

    fun inject(homeFragment: HomeFragment)

    fun inject(searchFragment: SearchFragment)

}