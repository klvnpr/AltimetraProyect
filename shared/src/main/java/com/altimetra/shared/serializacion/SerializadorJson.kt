package com.altimetra.shared.serializacion

import kotlinx.serialization.json.Json

object SerializadorJson {

    val json = Json {

        ignoreUnknownKeys = true

        prettyPrint = false

        encodeDefaults = true

        isLenient = true

    }

}