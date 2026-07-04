package com.altimetra.shared.modelo

import kotlinx.serialization.Serializable

@Serializable
data class LecturaPresion(

    val presion: Double,

    val tiempo: Long

)