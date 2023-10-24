package com.userstipa.currency

import android.app.Application
import com.userstipa.currency.di.AppComponent
import com.userstipa.currency.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}