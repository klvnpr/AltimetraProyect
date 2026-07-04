package com.altimetra.shared.comunicacion

import kotlinx.serialization.Serializable

@Serializable
data class MensajeAltimetra(

    val tipo: TipoMensaje,

    val contenido: String

)