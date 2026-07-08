package com.altimetra.wear.presentation.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.altimetra.wear.comunicacion.ClienteMessageWear
import com.altimetra.wear.presentation.viewmodel.AltimetraViewModel

class AltimetraViewModelFactory(
    private val application: Application,
    private val clienteMessage: ClienteMessageWear
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(AltimetraViewModel::class.java)) {
            return AltimetraViewModel(
                application,
                clienteMessage
            ) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}