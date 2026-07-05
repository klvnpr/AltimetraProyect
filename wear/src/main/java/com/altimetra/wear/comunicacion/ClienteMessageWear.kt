package com.altimetra.wear.comunicacion

import android.content.Context
import android.util.Log
import com.altimetra.shared.comunicacion.ConstantesComunicacion
import com.altimetra.shared.comunicacion.SerializadorDatos
import com.altimetra.shared.modelo.DatosAltimetra
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable

class ClienteMessageWear(
    private val context: Context
) : MessageClient.OnMessageReceivedListener { // Implementamos el Listener para recibir mensajes del móvil

    private val nodeClient =
        Wearable.getNodeClient(context)

    private val messageClient =
        Wearable.getMessageClient(context)

    private var authCallback: ((Boolean) -> Unit)? = null

    /**
     * NUEVA FUNCIÓN: Registra el escucha para recibir el estado de login desde el tlf
     */
    fun iniciar(authCallback: (Boolean) -> Unit) {
        this.authCallback = authCallback
        messageClient.addListener(this)
            .addOnSuccessListener {
                Log.d("Altimetra", "Reloj listo para escuchar autenticación")
            }
            .addOnFailureListener {
                Log.e("Altimetra", "Error al registrar escucha de autenticación en reloj", it)
            }
    }

    /**
     * NUEVA FUNCIÓN: Elimina el escucha al cerrar el reloj
     */
    fun detener() {
        messageClient.removeListener(this)
    }

    fun enviar(
        datos: DatosAltimetra
    ) {
        val json =
            SerializadorDatos.serializar(datos)

        val bytes =
            json.toByteArray()

        nodeClient.connectedNodes
            .addOnSuccessListener { nodos ->
                Log.d("Altimetra", "Nodos: ${nodos.size}")
                nodos.forEach { nodo ->
                    messageClient.sendMessage(
                        nodo.id,
                        ConstantesComunicacion.RUTA_DATOS,
                        bytes
                    ).addOnSuccessListener {
                        Log.d("Altimetra", "Mensaje enviado")
                    }.addOnFailureListener {
                        Log.e("Altimetra", "Error enviando", it)
                    }
                }
            }
    }

    /**
     * NUEVA FUNCIÓN: Captura el mensaje enviado desde el teléfono.
     * Si la ruta es RUTA_AUTH, lee el booleano y actualiza la interfaz.
     */
    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == ConstantesComunicacion.RUTA_AUTH) {
            val unblocked = String(messageEvent.data).toBoolean()
            Log.d("Altimetra", "Reloj recibió señal de desbloqueo: $unblocked")
            authCallback?.invoke(unblocked)
        }
    }
}