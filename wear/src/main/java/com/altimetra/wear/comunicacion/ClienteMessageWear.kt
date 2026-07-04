package com.altimetra.wear.comunicacion

import android.content.Context
import android.util.Log
import com.altimetra.shared.comunicacion.ConstantesComunicacion
import com.altimetra.shared.comunicacion.SerializadorDatos
import com.altimetra.shared.modelo.DatosAltimetra
import com.google.android.gms.wearable.Wearable

class ClienteMessageWear(

    private val context: Context

) {

    private val nodeClient =
        Wearable.getNodeClient(context)

    private val messageClient =
        Wearable.getMessageClient(context)

    fun enviar(
        datos: DatosAltimetra
    ) {

        val json =
            SerializadorDatos.serializar(datos)

        val bytes =
            json.toByteArray()

        nodeClient.connectedNodes
            .addOnSuccessListener { nodos ->

                Log.d(
                    "Altimetra",
                    "Nodos: ${nodos.size}"
                )

                nodos.forEach { nodo ->

                    messageClient.sendMessage(

                        nodo.id,

                        ConstantesComunicacion.RUTA_DATOS,

                        bytes

                    ).addOnSuccessListener {

                        Log.d(
                            "Altimetra",
                            "Mensaje enviado"
                        )

                    }.addOnFailureListener {

                        Log.e(
                            "Altimetra",
                            "Error enviando",
                            it
                        )

                    }

                }

            }

    }

}