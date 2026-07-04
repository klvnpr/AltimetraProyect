package com.altimetra.shared.algoritmo

import com.altimetra.shared.modelo.EstadoAlerta
import com.altimetra.shared.modelo.LecturaPresion
import kotlin.math.abs

object AnalizadorPresion {

    fun calcularEstado(
        historial: List<LecturaPresion>
    ): EstadoAlerta {

        if (historial.size < 2) {
            return EstadoAlerta.ESTABLE
        }

        val primera = historial.first().presion

        val ultima = historial.last().presion

        val diferencia = ultima - primera

        println("Diferencia: $diferencia")

        return when {

            diferencia <= -2.0 ->
                EstadoAlerta.PELIGRO

            diferencia <= -0.8 ->
                EstadoAlerta.PRECAUCION

            abs(diferencia) < 0.3 ->
                EstadoAlerta.ESTABLE

            else ->
                EstadoAlerta.ESTABLE

        }

    }

}