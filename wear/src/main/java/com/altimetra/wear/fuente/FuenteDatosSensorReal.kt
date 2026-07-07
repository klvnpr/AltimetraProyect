package com.altimetra.wear.fuente

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.altimetra.shared.modelo.LecturaPresion
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FuenteDatosSensorReal(context: Context) : FuenteDatosPresion {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    override fun obtenerLecturas(): Flow<LecturaPresion> = callbackFlow {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    if (it.sensor.type == Sensor.TYPE_PRESSURE) {
                        val lectura = LecturaPresion(
                            presion = it.values[0].toDouble(),
                            tiempo = System.currentTimeMillis()
                        )
                        trySend(lectura)
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (sensor != null) {
            sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
        } else {
            // Si el dispositivo no tiene barómetro (raro en Watch 4), cerramos el flujo
            close(Exception("Sensor de presión no disponible"))
        }

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}
