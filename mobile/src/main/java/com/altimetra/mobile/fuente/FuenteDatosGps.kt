package com.altimetra.mobile.fuente

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

data class UbicacionGps(
    val latitud: Double,
    val longitud: Double,
    val altitud: Double,
    val precision: Float
)

class FuenteDatosGps(
    private val context: Context
) {

    private val cliente =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun obtenerUbicacion(): UbicacionGps? {

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        return suspendCancellableCoroutine { cont ->

            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setDurationMillis(10000)
                .build()

            cliente
                .getCurrentLocation(request, null)
                .addOnSuccessListener { location ->

                    if (location == null) {
                        cont.resume(null)
                        return@addOnSuccessListener
                    }

                    cont.resume(
                        UbicacionGps(
                            latitud = location.latitude,
                            longitud = location.longitude,
                            altitud = location.altitude,
                            precision = location.accuracy
                        )
                    )

                }
                .addOnFailureListener {
                    cont.resume(null)
                }

        }
    }

}