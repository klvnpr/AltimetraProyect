package com.altimetra.wear.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.altimetra.shared.modelo.EstadoConexion
import com.altimetra.shared.modelo.ModoFuncionamiento
import com.altimetra.shared.modelo.TipoSimulacion
import com.altimetra.wear.comunicacion.ClienteMessageWear
import com.altimetra.wear.modelo.EstadoPantalla
import com.altimetra.wear.repositorio.RepositorioAltimetra
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AltimetraViewModel(
    application: Application,
    clienteMessage: ClienteMessageWear
) : AndroidViewModel(application) {

    private val repositorio =
        RepositorioAltimetra(application, clienteMessage)

    private val _estadoPantalla =
        mutableStateOf(EstadoPantalla())

    val estadoPantalla: State<EstadoPantalla>
        get() = _estadoPantalla

    private var observationJob: Job? = null

    init {
        observarDatos()
    }

    private fun observarDatos() {
        observationJob?.cancel()
        observationJob = viewModelScope.launch {
            repositorio.obtenerDatos().collect { datos ->
                _estadoPantalla.value =
                    _estadoPantalla.value.copy(
                        presion = datos.lectura.presion,
                        altitud = datos.altitud,
                        estado = datos.estado,
                        modo = datos.modoFuncionamiento,
                        simulacion = datos.tipoSimulacion ?: _estadoPantalla.value.simulacion,
                        conexion = EstadoConexion.CONECTADO,
                        cantidadLecturas = repositorio.cantidadLecturas()
                    )
            }
        }
    }

    fun cambiarSimulacion(tipo: TipoSimulacion) {
        repositorio.cambiarSimulacion(tipo)
        _estadoPantalla.value = _estadoPantalla.value.copy(
            simulacion = tipo,
            modo = ModoFuncionamiento.SIMULACION
        )
        // Reiniciar observación para cambiar de fuente si es necesario
        observarDatos()
    }

    fun usarSensorReal() {
        repositorio.usarSensorReal()
        _estadoPantalla.value = _estadoPantalla.value.copy(
            modo = ModoFuncionamiento.REAL
        )
        observarDatos()
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