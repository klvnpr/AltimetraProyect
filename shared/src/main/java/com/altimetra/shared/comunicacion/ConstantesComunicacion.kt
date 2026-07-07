package com.altimetra.shared.comunicacion

object ConstantesComunicacion {

    /**
     * Ruta utilizada para sincronizar
     * el estado del reloj.
     */
    const val RUTA_DATOS = "/altimetra/datos"

    /**
     * Clave donde se almacena el JSON.
     */
    const val CLAVE_DATOS = "datos"

    /**
     * NUEVA: Ruta utilizada para enviar el estado de
     * inicio de sesión desde el teléfono hacia el reloj.
     */
    const val RUTA_AUTH = "/altimetra/auth"

    /**
     * NUEVA: Ruta utilizada por el reloj para solicitar
     * el estado de autenticación al teléfono.
     */
    const val RUTA_SOLICITAR_AUTH = "/altimetra/request_auth"
}