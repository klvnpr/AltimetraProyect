package com.altimetra.mobile.comunicacion

import android.util.Log
import com.altimetra.mobile.repositorio.RepositorioAltimetra
import com.altimetra.shared.comunicacion.SerializadorDatos
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class ServicioMessageMobile : WearableListenerService() {

    companion object {

        private const val TAG = "Altimetra"

        private const val RUTA = "/altimetra/datos"
    }

    override fun onMessageReceived(
        messageEvent: MessageEvent
    ) {

        super.onMessageReceived(messageEvent)

        Log.d(TAG, "===================================")
        Log.d(TAG, "MENSAJE RECIBIDO")
        Log.d(TAG, "Ruta: ${messageEvent.path}")
        Log.d(TAG, "Bytes: ${messageEvent.data.size}")

        if (messageEvent.path != RUTA) {

            Log.d(TAG, "Ruta incorrecta")

            return

        }

        val json = String(messageEvent.data)

        Log.d(TAG, "JSON:")
        Log.d(TAG, json)

        val datos = SerializadorDatos.deserializar(json)

        Log.d(TAG, "--------- DATOS ---------")
        Log.d(TAG, "Presión   : ${datos.lectura.presion}")
        Log.d(TAG, "Altitud   : ${datos.altitud}")
        Log.d(TAG, "Estado    : ${datos.estado}")
        Log.d(TAG, "Modo      : ${datos.modoFuncionamiento}")
        Log.d(TAG, "Escenario : ${datos.tipoSimulacion}")
        Log.d(TAG, "Tiempo    : ${datos.lectura.tiempo}")
        Log.d(TAG, "-------------------------")

        RepositorioAltimetra.actualizar(datos)

    }

}