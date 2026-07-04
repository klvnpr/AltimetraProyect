package com.altimetra.mobile.repositorio

import com.altimetra.shared.modelo.DatosAltimetra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object RepositorioAltimetra {

    private val _datos =
        MutableStateFlow<DatosAltimetra?>(null)

    val datos: StateFlow<DatosAltimetra?>
        get() = _datos

    fun actualizar(
        datos: DatosAltimetra
    ) {

        _datos.value = datos

    }

}