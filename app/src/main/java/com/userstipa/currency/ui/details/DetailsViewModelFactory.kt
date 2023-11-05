package com.userstipa.currency.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.userstipa.currency.App

class DetailsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = checkNotNull(extras[APPLICATION_KEY])
        val dispatcher = (application as App).appComponent.getDispatcherProvider()
        val getCurrency = application.appComponent.useCaseGetCurrency()
        return DetailsViewModel(dispatcher, getCurrency) as T
    }
}