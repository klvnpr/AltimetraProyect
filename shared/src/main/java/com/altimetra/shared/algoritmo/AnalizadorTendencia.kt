package com.altimetra.shared.algoritmo

import com.altimetra.shared.modelo.LecturaPresion
import com.altimetra.shared.modelo.TendenciaPresion

object AnalizadorTendencia {

    fun calcular(
        historial: List<LecturaPresion>
    ): TendenciaPresion {

        if (historial.size < 2) {
            return TendenciaPresion.ESTABLE
        }

        val anterior = historial[historial.size - 2].presion
        val actual = historial.last().presion

        val diferencia = actual - anterior

        return when {

            diferencia > 0.05 ->
                TendenciaPresion.SUBIENDO

            diferencia < -0.05 ->
                TendenciaPresion.BAJANDO

            else ->
                TendenciaPresion.ESTABLE

        }

    }

}