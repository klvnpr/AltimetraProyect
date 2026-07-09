package com.altimetra.shared.algoritmo

import kotlin.math.pow

/**
 * Calcula la altitud relativa tomando como
 * referencia la presión inicial al arrancar
 * la aplicación.
 */
object CalculadoraAltitud {

    /**
     * Presión de referencia capturada al iniciar.
     */
    private var presionReferencia: Double? = null

    /**
     * Calcula la altitud relativa.
     */
    fun calcular(
        presion: Double
    ): Double {

        if (presionReferencia == null) {
            presionReferencia = presion
        }

        val altitudActual = calcularBarometrica(presion)
        val altitudReferencia = calcularBarometrica(presionReferencia!!)

        return altitudActual - altitudReferencia
    }

    /**
     * Permite recalibrar el altímetro.
     */
    fun recalibrar() {
        presionReferencia = null
    }

    /**
     * Fórmula barométrica real.
     */
    private fun calcularBarometrica(
        presion: Double
    ): Double {
        return 44330.0 * (1 - (presion / 1013.25).pow(0.1903))
    }
}
