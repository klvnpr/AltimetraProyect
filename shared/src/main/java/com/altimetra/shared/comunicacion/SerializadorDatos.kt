package com.altimetra.shared.comunicacion

import com.altimetra.shared.modelo.DatosAltimetra
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SerializadorDatos {

    private val json = Json {

        ignoreUnknownKeys = true

        encodeDefaults = true

    }

    fun serializar(
        datos: DatosAltimetra
    ): String {

        return json.encodeToString(datos)

    }

    fun deserializar(
        texto: String
    ): DatosAltimetra {

        return json.decodeFromString(texto)

    }

}