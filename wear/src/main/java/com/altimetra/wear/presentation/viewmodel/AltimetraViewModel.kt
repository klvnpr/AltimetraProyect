package com.altimetra.wear.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altimetra.shared.modelo.EstadoConexion
import com.altimetra.shared.modelo.TipoSimulacion
import com.altimetra.wear.comunicacion.ClienteMessageWear
import com.altimetra.wear.modelo.EstadoPantalla
import com.altimetra.wear.repositorio.RepositorioAltimetra
import kotlinx.coroutines.launch

class AltimetraViewModel(

    clienteMessage: ClienteMessageWear

) : ViewModel() {

    private val repositorio =
        RepositorioAltimetra(clienteMessage)

    private val _estadoPantalla =
        mutableStateOf(EstadoPantalla())

    val estadoPantalla: State<EstadoPantalla>
        get() = _estadoPantalla

    init {
        observarDatos()
    }

    private fun observarDatos() {

        viewModelScope.launch {

            repositorio.obtenerDatos().collect { datos ->

                _estadoPantalla.value =
                    _estadoPantalla.value.copy(

                        presion = datos.lectura.presion,

                        altitud = datos.altitud,

                        estado = datos.estado,

                        modo = datos.modoFuncionamiento,

                        simulacion = datos.tipoSimulacion
                            ?: _estadoPantalla.value.simulacion,

                        conexion = EstadoConexion.CONECTADO,

                        cantidadLecturas =
                            repositorio.cantidadLecturas()

                    )

            }

        }

    }

    fun cambiarSimulacion(
        tipo: TipoSimulacion
    ) {

        repositorio.cambiarSimulacion(tipo)

        _estadoPantalla.value =
            _estadoPantalla.value.copy(
                simulacion = tipo
            )

    }

    fun abrirConfiguracion() {

        _estadoPantalla.value =
            _estadoPantalla.value.copy(
                mostrarConfiguracion = true
            )

    }

    fun cerrarConfiguracion() {

        _estadoPantalla.value =
            _estadoPantalla.value.copy(
                mostrarConfiguracion = false
            )

    }

}