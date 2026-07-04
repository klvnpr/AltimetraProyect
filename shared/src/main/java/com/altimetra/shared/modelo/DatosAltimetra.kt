package com.altimetra.shared.modelo

import kotlinx.serialization.Serializable

@Serializable
data class DatosAltimetra(

    val lectura: LecturaPresion,

    val altitud: Double,

    val estado: EstadoAlerta,

    val modoFuncionamiento: ModoFuncionamiento,

    val tipoSimulacion: TipoSimulacion?

)