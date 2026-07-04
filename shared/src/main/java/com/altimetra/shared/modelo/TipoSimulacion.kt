package com.altimetra.shared.modelo

import kotlinx.serialization.Serializable

@Serializable
enum class TipoSimulacion {

    ASCENSO,

    DESCENSO,

    ESTABLE,

    TORMENTA

}