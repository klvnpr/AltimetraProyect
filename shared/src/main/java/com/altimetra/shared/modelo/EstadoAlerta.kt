package com.altimetra.shared.modelo

import kotlinx.serialization.Serializable

@Serializable
enum class EstadoAlerta {

    ESTABLE,

    PRECAUCION,

    PELIGRO

}