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
        var ultimaLecturaTiempo = 0L

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val ahora = System.currentTimeMillis()
                // Limitamos a máximo 2 lecturas por segundo para ahorrar batería y fluidez
                if (ahora - ultimaLecturaTiempo < 500) return 

                event?.let {
                    if (it.sensor.type == Sensor.TYPE_PRESSURE) {
                        ultimaLecturaTiempo = ahora
                        val lectura = LecturaPresion(
                            presion = it.values[0].toDouble(),
                            tiempo = ahora
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
                SensorManager.SENSOR_DELAY_NORMAL // Menos agresivo que UI
            )
        } else {
            close(Exception("Sensor de presión no disponible"))
        }

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}
