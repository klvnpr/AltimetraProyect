package com.altimetra.mobile.comunicacion

import android.util.Log
import com.altimetra.mobile.repositorio.RepositorioAltimetra
import com.altimetra.mobile.repositorio.SessionManager
import com.altimetra.shared.comunicacion.ConstantesComunicacion
import com.altimetra.shared.comunicacion.SerializadorDatos
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService

class ServicioMessageMobile : WearableListenerService() {

    companion object {

        private const val TAG = "Altimetra"

    }

    override fun onMessageReceived(
        messageEvent: MessageEvent
    ) {

        super.onMessageReceived(messageEvent)

        Log.d(TAG, "MENSAJE RECIBIDO - Ruta: ${messageEvent.path}")

        when (messageEvent.path) {
            ConstantesComunicacion.RUTA_DATOS -> {
                val json = String(messageEvent.data)
                val datos = SerializadorDatos.deserializar(json)
                RepositorioAltimetra.actualizar(datos)
                Log.d(TAG, "Datos de altimetría recibidos y actualizados")
            }
            ConstantesComunicacion.RUTA_SOLICITAR_AUTH -> {
                val sessionManager = SessionManager(this)
                val isLoggedIn = sessionManager.isUserLoggedIn()
                val bytes = isLoggedIn.toString().toByteArray()

                // Respondemos al nodo que solicitó la información
                Wearable.getMessageClient(this).sendMessage(
                    messageEvent.sourceNodeId,
                    ConstantesComunicacion.RUTA_AUTH,
                    bytes
                ).addOnSuccessListener {
                    Log.d(TAG, "Respuesta de autenticación ($isLoggedIn) enviada al reloj")
                }
            }
        }
    }

}