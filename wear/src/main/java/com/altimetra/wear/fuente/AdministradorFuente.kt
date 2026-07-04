package com.altimetra.wear.fuente

import com.altimetra.shared.modelo.ModoFuncionamiento

object AdministradorFuente {

    private var modo =
        ModoFuncionamiento.SIMULACION

    private val simulada =
        FuenteDatosSimulada()

    fun obtenerFuente(): FuenteDatosPresion {

        return when (modo) {

            ModoFuncionamiento.SIMULACION ->
                simulada

            ModoFuncionamiento.REAL ->
                simulada // temporalmente

        }

    }

    fun usarSimulacion() {

        modo = ModoFuncionamiento.SIMULACION

    }

    fun usarSensorReal() {

        modo = ModoFuncionamiento.REAL

    }

    fun cambiarEscenario(
        tipo: com.altimetra.shared.modelo.TipoSimulacion
    ) {

        simulada.cambiarEscenario(tipo)

    }

}