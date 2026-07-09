package com.altimetra.wear.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.altimetra.shared.modelo.EstadoAlerta
import com.altimetra.shared.modelo.EstadoConexion
import com.altimetra.shared.modelo.ModoFuncionamiento
import com.altimetra.shared.modelo.TipoSimulacion
import com.altimetra.wear.comunicacion.ClienteMessageWear
import com.altimetra.wear.modelo.EstadoPantalla
import com.altimetra.wear.repositorio.RepositorioAltimetra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AltimetraViewModel(
    application: Application,
    private val clienteMessage: ClienteMessageWear
) : AndroidViewModel(application) {

    private val repositorio =
        RepositorioAltimetra(application, clienteMessage)

    private val _estadoPantalla =
        mutableStateOf(EstadoPantalla())

    val estadoPantalla: State<EstadoPantalla>
        get() = _estadoPantalla

    private var observationJob: Job? = null
    
    private val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private var ultimoEstadoVibrado: EstadoAlerta? = null

    init {
        observarDatos()
    }

    private fun observarDatos() {
        observationJob?.cancel()
        observationJob = viewModelScope.launch {
            // Recolectamos datos en un hilo de computación (Default)
            repositorio.obtenerDatos().collect { datos ->
                
                // Lógica de vibración al cambiar de estado
                if (datos.estado != ultimoEstadoVibrado) {
                    vibrarSegunEstado(datos.estado)
                    ultimoEstadoVibrado = datos.estado
                }

                // Solo volvemos al hilo principal (Main) para actualizar la UI
                withContext(Dispatchers.Main) {
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
    }

    private fun vibrarSegunEstado(estado: EstadoAlerta) {
        when (estado) {
            EstadoAlerta.PRECAUCION -> {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            EstadoAlerta.PELIGRO -> {
                val pattern = longArrayOf(0, 400, 200, 400, 200, 400)
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            }
            else -> {}
        }
    }

    fun cambiarSimulacion(tipo: TipoSimulacion) {
        repositorio.cambiarSimulacion(tipo)
        _estadoPantalla.value = _estadoPantalla.value.copy(
            simulacion = tipo,
            modo = ModoFuncionamiento.SIMULACION
        )
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
        _estadoPantalla.value = _estadoPantalla.value.copy(
            mostrarConfiguracion = true
        )
    }

    fun cerrarConfiguracion() {
        _estadoPantalla.value = _estadoPantalla.value.copy(
            mostrarConfiguracion = false
        )
    }
}
