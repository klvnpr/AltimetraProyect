package com.altimetra.wear.presentation.componentes

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.Text
import com.altimetra.shared.modelo.EstadoConexion

@Composable
fun IndicadorConexion(
    estado: EstadoConexion
) {

    val texto = when (estado) {

        EstadoConexion.CONECTADO ->
            "🟢 Conectado"

        EstadoConexion.CONECTANDO ->
            "🟡 Conectando..."

        EstadoConexion.DESCONECTADO ->
            "⚪ Sin conexión"

    }

    Text(texto)

}