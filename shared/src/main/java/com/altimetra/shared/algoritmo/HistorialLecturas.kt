package com.altimetra.shared.algoritmo

import com.altimetra.shared.modelo.LecturaPresion

/**
 * Mantiene un historial de las últimas lecturas de presión.
 */
class HistorialLecturas(

    private val capacidadMaxima: Int = 100

) {

    private val lecturas = mutableListOf<LecturaPresion>()

    /**
     * Agrega una nueva lectura al historial.
     */
    fun agregar(lectura: LecturaPresion) {

        lecturas.add(lectura)

        while (lecturas.size > capacidadMaxima) {
            lecturas.removeAt(0)
        }

    }

    /**
     * Devuelve una copia del historial.
     */
    fun obtenerLecturas(): List<LecturaPresion> {

        return lecturas.toList()

    }

    /**
     * Elimina todas las lecturas.
     */
    fun limpiar() {

        lecturas.clear()

    }

    /**
     * Cantidad de lecturas almacenadas.
     */
    fun cantidad(): Int {

        return lecturas.size

    }

}