package com.userstipa.currency.di

import android.content.Context
import com.userstipa.currency.di.data_modules.DataModule
import com.userstipa.currency.di.dispatchers.DispatcherModule
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.di.domain_modules.DomainModule
import com.userstipa.currency.di.domain_modules.MapperModule
import com.userstipa.currency.di.ui_modules.ViewModelFactoryModule
import com.userstipa.currency.domain.usecases.add_currency.AddCurrency
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrencies
import com.userstipa.currency.domain.usecases.get_currency.GetCurrency
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrency
import com.userstipa.currency.domain.usecases.subscribe_my_currencies.SubscribeMyCurrencies
import com.userstipa.currency.ui.details.DetailsFragment
import com.userstipa.currency.ui.home.HomeFragment
import com.userstipa.currency.ui.search_currency.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class,
        ViewModelFactoryModule::class,
        DomainModule::class,
        DispatcherModule::class,
        MapperModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun useCaseGetAllCurrencies(): GetAllCurrencies

    fun useCaseAddCurrency(): AddCurrency

    fun useCaseRemoveCurrency(): RemoveCurrency

    fun useCaseSubscribeMyCurrencies(): SubscribeMyCurrencies

    fun useCaseGetCurrency(): GetCurrency

    fun getDispatcherProvider(): DispatcherProvider

    fun inject(homeFragment: HomeFragment)

    fun inject(searchFragment: SearchFragment)

    fun inject(detailsFragment: DetailsFragment)

}