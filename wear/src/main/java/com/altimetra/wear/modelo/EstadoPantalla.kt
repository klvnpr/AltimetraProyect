package com.altimetra.wear.modelo

import com.altimetra.shared.modelo.EstadoAlerta
import com.altimetra.shared.modelo.EstadoConexion
import com.altimetra.shared.modelo.ModoFuncionamiento
import com.altimetra.shared.modelo.TipoSimulacion

data class EstadoPantalla(

    val presion: Double = 1013.25,

    val altitud: Double = 0.0,

    val estado: EstadoAlerta = EstadoAlerta.ESTABLE,

    val modo: ModoFuncionamiento = ModoFuncionamiento.SIMULACION,

    val simulacion: TipoSimulacion = TipoSimulacion.ESTABLE,

    val conexion: EstadoConexion =
        EstadoConexion.DESCONECTADO,

    val cantidadLecturas: Int = 0,

    val mostrarConfiguracion: Boolean = false

)