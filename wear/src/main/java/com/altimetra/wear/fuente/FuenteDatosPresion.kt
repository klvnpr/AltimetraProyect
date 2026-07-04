package com.altimetra.wear.fuente

import com.altimetra.shared.modelo.LecturaPresion
import kotlinx.coroutines.flow.Flow

interface FuenteDatosPresion {

    fun obtenerLecturas(): Flow<LecturaPresion>

}