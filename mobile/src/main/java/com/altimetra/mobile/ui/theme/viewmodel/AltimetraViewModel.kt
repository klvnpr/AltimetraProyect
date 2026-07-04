package com.altimetra.mobile.ui.theme.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altimetra.mobile.modelo.EstadoPantalla
import com.altimetra.mobile.repositorio.RepositorioAltimetra
import kotlinx.coroutines.launch

class AltimetraViewModel : ViewModel() {

    private val _estadoPantalla =
        mutableStateOf(EstadoPantalla())

    val estadoPantalla: State<EstadoPantalla>
        get() = _estadoPantalla

    init {

        observarDatos()

    }

    private fun observarDatos() {

        viewModelScope.launch {

            RepositorioAltimetra.datos.collect { datos ->

                if (datos == null) return@collect

                _estadoPantalla.value =
                    EstadoPantalla(

                        presion = datos.lectura.presion,

                        altitud = datos.altitud,

                        estado = datos.estado,

                        modo = datos.modoFuncionamiento,

                        simulacion = datos.tipoSimulacion
                            ?: _estadoPantalla.value.simulacion,

                        conectado = true

                    )

            }

        }

    }

}