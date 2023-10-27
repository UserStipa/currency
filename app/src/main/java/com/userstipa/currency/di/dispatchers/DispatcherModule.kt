package com.userstipa.currency.di.dispatchers

import dagger.Module
import dagger.Provides

@Module
class DispatcherModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }

}