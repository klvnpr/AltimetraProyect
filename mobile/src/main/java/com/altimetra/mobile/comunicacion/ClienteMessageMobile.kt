package com.altimetra.mobile.comunicacion

import android.content.Context
import android.util.Log
import com.altimetra.shared.comunicacion.ConstantesComunicacion
import com.altimetra.shared.comunicacion.SerializadorDatos
import com.altimetra.shared.modelo.DatosAltimetra
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable

class ClienteMessageMobile(

    context: Context

) : MessageClient.OnMessageReceivedListener {

    private val messageClient =
        Wearable.getMessageClient(context)

    private var callback: ((DatosAltimetra) -> Unit)? = null

    fun iniciar(

        callback: (DatosAltimetra) -> Unit

    ) {

        this.callback = callback

        messageClient
            .addListener(this)
            .addOnSuccessListener {

                Log.d(
                    "Altimetra",
                    "Listener MessageClient registrado"
                )

            }
            .addOnFailureListener {

                Log.e(
                    "Altimetra",
                    "No se pudo registrar listener",
                    it
                )

            }

    }

    fun detener() {

        messageClient.removeListener(this)

    }

    override fun onMessageReceived(

        messageEvent: MessageEvent

    ) {

        if (messageEvent.path != ConstantesComunicacion.RUTA_DATOS) {

            return

        }

        val json =
            String(messageEvent.data)

        val datos =
            SerializadorDatos.deserializar(json)

        Log.d(

            "Altimetra",

            """
            Mensaje recibido

            Presión: ${datos.lectura.presion}

            Altitud: ${datos.altitud}

            Estado: ${datos.estado}
            """.trimIndent()

        )

        callback?.invoke(datos)

    }

}