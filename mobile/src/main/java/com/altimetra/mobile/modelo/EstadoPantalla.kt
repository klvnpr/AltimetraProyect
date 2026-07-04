package com.altimetra.mobile.modelo

import com.altimetra.shared.modelo.EstadoAlerta
import com.altimetra.shared.modelo.ModoFuncionamiento
import com.altimetra.shared.modelo.TipoSimulacion

data class EstadoPantalla(

    val presion: Double = 0.0,

    val altitud: Double = 0.0,

    val estado: EstadoAlerta = EstadoAlerta.ESTABLE,

    val modo: ModoFuncionamiento = ModoFuncionamiento.SIMULACION,

    val simulacion: TipoSimulacion = TipoSimulacion.ESTABLE,

    val conectado: Boolean = false

)