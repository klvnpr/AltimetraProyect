package com.altimetra.mobile.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.altimetra.mobile.R
import com.altimetra.shared.modelo.EstadoAlerta

object NotificadorAltimetra {
    private const val CHANNEL_ID = "altimetra_alertas"
    private const val NOTIFICATION_ID = 1001
    
    // Para evitar repetir la misma notificación si el estado no ha cambiado
    private var ultimoEstadoNotificado: EstadoAlerta? = null

    fun mostrarNotificacionSiEsNecesario(context: Context, estado: EstadoAlerta) {
        if (estado == EstadoAlerta.ESTABLE) {
            ultimoEstadoNotificado = estado
            return
        }

        // Si el estado es el mismo que el anterior, no repetimos la notificación
        if (estado == ultimoEstadoNotificado) return

        val titulo = if (estado == EstadoAlerta.PELIGRO) "¡ALERTA DE PELIGRO!" else "Aviso de Precaución"
        val mensaje = if (estado == EstadoAlerta.PELIGRO) 
            "Se detectó un cambio brusco de presión. ¡Posible tormenta o ascenso peligroso!" 
            else "La presión está variando. Mantente atento."

        crearCanal(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_altimetra) // Asegúrate de que este recurso existe
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
        
        ultimoEstadoNotificado = estado
    }

    private fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alertas de Altimetría"
            val descriptionText = "Notificaciones de cambios bruscos de presión"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
