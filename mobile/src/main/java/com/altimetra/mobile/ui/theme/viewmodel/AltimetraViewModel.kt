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

    private val historialTemporal = mutableListOf<Double>()

    init {
        observarDatos()
    }

    private fun observarDatos() {
        viewModelScope.launch {
            RepositorioAltimetra.datos.collect { datos ->
                if (datos == null) return@collect

                // Guardamos la presión para la gráfica (máximo 60 puntos)
                historialTemporal.add(datos.lectura.presion)
                if (historialTemporal.size > 60) {
                    historialTemporal.removeAt(0)
                }

                _estadoPantalla.value =
                    EstadoPantalla(
                        presion = datos.lectura.presion,
                        altitud = datos.altitud,
                        estado = datos.estado,
                        modo = datos.modoFuncionamiento,
                        simulacion = datos.tipoSimulacion ?: com.altimetra.shared.modelo.TipoSimulacion.ESTABLE,
                        conectado = true,
                        historialPresion = historialTemporal.toList()
                    )
            }
        }
    }
}