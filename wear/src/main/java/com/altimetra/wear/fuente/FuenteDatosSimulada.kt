package com.altimetra.wear.fuente

import com.altimetra.shared.modelo.LecturaPresion
import com.altimetra.shared.modelo.TipoSimulacion
import com.altimetra.wear.simulacion.GeneradorSimulacion
import kotlinx.coroutines.flow.Flow

class FuenteDatosSimulada : FuenteDatosPresion {

    private val generador = GeneradorSimulacion()

    private var tipo = TipoSimulacion.ESTABLE

    fun cambiarEscenario(
        nuevoTipo: TipoSimulacion
    ) {

        tipo = nuevoTipo

        generador.cambiarEscenario(
            nuevoTipo
        )

    }

    override fun obtenerLecturas(): Flow<LecturaPresion> {

        return generador.iniciar()

    }

}