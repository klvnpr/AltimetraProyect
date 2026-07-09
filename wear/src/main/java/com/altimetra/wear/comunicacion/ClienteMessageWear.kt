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
) : MessageClient.OnMessageReceivedListener {

    private val nodeClient = Wearable.getNodeClient(context)
    private val messageClient = Wearable.getMessageClient(context)
    
    private var authCallback: ((Boolean) -> Unit)? = null

    // Caché de nodos para no pedirlos cada segundo
    private var nodosCacheados = listOf<String>()
    private var ultimoUpdateNodos = 0L

    fun iniciar(authCallback: (Boolean) -> Unit) {
        this.authCallback = authCallback
        messageClient.addListener(this)
            .addOnSuccessListener {
                solicitarEstadoAutenticacion()
                actualizarNodos()
            }
    }

    fun solicitarEstadoAutenticacion() {
        nodeClient.connectedNodes.addOnSuccessListener { nodos ->
            nodos.forEach { nodo ->
                messageClient.sendMessage(nodo.id, ConstantesComunicacion.RUTA_SOLICITAR_AUTH, byteArrayOf())
            }
        }
    }

    fun detener() {
        messageClient.removeListener(this)
    }

    fun enviar(datos: DatosAltimetra) {
        val ahora = System.currentTimeMillis()
        if (ahora - ultimoUpdateNodos > 15000) {
            actualizarNodos()
        }

        if (nodosCacheados.isEmpty()) {
            if (ahora - ultimoUpdateNodos > 2000) actualizarNodos()
            return
        }

        Thread {
            try {
                val json = SerializadorDatos.serializar(datos)
                val bytes = json.toByteArray()
                nodosCacheados.forEach { nodoId ->
                    messageClient.sendMessage(nodoId, ConstantesComunicacion.RUTA_DATOS, bytes)
                }
            } catch (e: Exception) {}
        }.start()
    }

    private fun actualizarNodos() {
        ultimoUpdateNodos = System.currentTimeMillis()
        nodeClient.connectedNodes.addOnSuccessListener { nodos ->
            nodosCacheados = nodos.map { it.id }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == ConstantesComunicacion.RUTA_AUTH) {
            val unblocked = String(messageEvent.data).toBoolean()
            authCallback?.invoke(unblocked)
        }
    }
}
