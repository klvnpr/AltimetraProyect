package com.altimetra.shared.algoritmo

import com.altimetra.shared.modelo.EstadoAlerta
import com.altimetra.shared.modelo.LecturaPresion

object AnalizadorPresion {

    fun calcularEstado(
        historial: List<LecturaPresion>
    ): EstadoAlerta {

        if (historial.size < 10) {
            return EstadoAlerta.ESTABLE
        }

        val primera = historial.first().presion
        val ultima = historial.last().presion
        val diferencia = ultima - primera
        
        // Tiempo transcurrido en segundos entre la primera y última lectura
        val tiempoSegundos = (historial.last().tiempo - historial.first().tiempo) / 1000.0
        
        if (tiempoSegundos < 1.0) return EstadoAlerta.ESTABLE

        // Normalizamos la caída a hPa por minuto para que sea una alerta meteorológica real
        // Una caída de 1hPa en 1 hora es cambio de clima. 
        // Para alertas de "tormenta" rápidas en montaña:
        val tasaCambioPorMinuto = (diferencia / tiempoSegundos) * 60.0

        return when {
            // Caída muy brusca (Tormenta o ascenso rápido)
            tasaCambioPorMinuto <= -1.5 -> EstadoAlerta.PELIGRO
            
            // Caída moderada (Cambio de clima o caminata hacia arriba)
            tasaCambioPorMinuto <= -0.5 -> EstadoAlerta.PRECAUCION
            
            else -> EstadoAlerta.ESTABLE
        }
    }

}