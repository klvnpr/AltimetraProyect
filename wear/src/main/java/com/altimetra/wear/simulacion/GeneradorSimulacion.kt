package com.altimetra.wear.simulacion

import com.altimetra.shared.modelo.LecturaPresion
import com.altimetra.shared.modelo.TipoSimulacion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class GeneradorSimulacion {

    companion object {

        private const val PRESION_INICIAL = 1013.25

        private const val PRESION_MINIMA = 870.0

        private const val PRESION_MAXIMA = 1085.0

    }

    private var presionActual = PRESION_INICIAL

    private var tipoActual = TipoSimulacion.ESTABLE

    fun cambiarEscenario(
        tipo: TipoSimulacion
    ) {

        tipoActual = tipo

    }

    fun iniciar(
        intervalo: Long = 1000L
    ): Flow<LecturaPresion> = flow {

        while (true) {

            presionActual += calcularVariacion(tipoActual)

            presionActual = presionActual.coerceIn(
                PRESION_MINIMA,
                PRESION_MAXIMA
            )

            emit(
                LecturaPresion(
                    presion = presionActual,
                    tiempo = System.currentTimeMillis()
                )
            )

            delay(intervalo)

        }

    }

    private fun calcularVariacion(
        tipo: TipoSimulacion
    ): Double {

        return when (tipo) {

            TipoSimulacion.ESTABLE ->

                Random.nextDouble(
                    -0.05,
                    0.05
                )

            TipoSimulacion.ASCENSO ->

                -Random.nextDouble(
                    0.20,
                    0.80
                )

            TipoSimulacion.DESCENSO ->

                Random.nextDouble(
                    0.20,
                    0.80
                )

            TipoSimulacion.TORMENTA ->

                -Random.nextDouble(
                    0.80,
                    2.00
                )

        }

    }

}