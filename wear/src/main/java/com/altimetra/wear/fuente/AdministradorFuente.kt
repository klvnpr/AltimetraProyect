package com.altimetra.wear.fuente

import android.content.Context
import com.altimetra.shared.modelo.ModoFuncionamiento

object AdministradorFuente {

    private var modo =
        ModoFuncionamiento.SIMULACION

    private val simulada =
        FuenteDatosSimulada()

    private var real: FuenteDatosSensorReal? = null

    fun obtenerFuente(context: Context): FuenteDatosPresion {

        return when (modo) {

            ModoFuncionamiento.SIMULACION ->
                simulada

            ModoFuncionamiento.REAL -> {
                if (real == null) {
                    real = FuenteDatosSensorReal(context)
                }
                real!!
            }

        }

    }

    fun usarSimulacion() {
        modo = ModoFuncionamiento.SIMULACION
    }

    fun usarSensorReal() {
        modo = ModoFuncionamiento.REAL
    }
    
    fun obtenerModoActual() = modo

    fun cambiarEscenario(
        tipo: com.altimetra.shared.modelo.TipoSimulacion
    ) {

        simulada.cambiarEscenario(tipo)

    }

}