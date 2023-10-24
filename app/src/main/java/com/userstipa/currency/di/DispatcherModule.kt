package com.userstipa.currency.di

import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.di.dispatchers.DispatcherProviderImpl
import dagger.Module
import dagger.Provides

@Module
class DispatcherModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }

}