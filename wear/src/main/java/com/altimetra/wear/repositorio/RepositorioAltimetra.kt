package com.altimetra.wear.repositorio

import com.altimetra.shared.algoritmo.AnalizadorPresion
import com.altimetra.shared.algoritmo.CalculadoraAltitud
import com.altimetra.shared.algoritmo.HistorialLecturas
import com.altimetra.shared.modelo.DatosAltimetra
import com.altimetra.shared.modelo.ModoFuncionamiento
import com.altimetra.shared.modelo.TipoSimulacion
import com.altimetra.wear.comunicacion.ClienteMessageWear
import com.altimetra.wear.fuente.AdministradorFuente
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class RepositorioAltimetra(

    private val clienteMessage: ClienteMessageWear?

) {

    private val historial = HistorialLecturas()

    private val tipoSimulacion =
        MutableStateFlow(TipoSimulacion.ESTABLE)

    fun cambiarSimulacion(
        tipo: TipoSimulacion
    ) {

        historial.limpiar()

        CalculadoraAltitud.recalibrar()

        AdministradorFuente.cambiarEscenario(tipo)

        tipoSimulacion.value = tipo

    }

    fun obtenerDatos(): Flow<DatosAltimetra> {

        return AdministradorFuente
            .obtenerFuente()
            .obtenerLecturas()
            .map { lectura ->

                historial.agregar(lectura)

                val datos = DatosAltimetra(

                    lectura = lectura,

                    altitud = CalculadoraAltitud.calcular(
                        lectura.presion
                    ),

                    estado = AnalizadorPresion.calcularEstado(
                        historial.obtenerLecturas()
                    ),

                    modoFuncionamiento =
                        ModoFuncionamiento.SIMULACION,

                    tipoSimulacion =
                        tipoSimulacion.value

                )

                clienteMessage?.enviar(datos)

                datos

            }

    }

    fun cantidadLecturas(): Int {

        return historial.cantidad()

    }

}