package com.altimetra.wear.presentation.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.altimetra.shared.modelo.EstadoConexion
import com.altimetra.wear.presentation.theme.AmarilloConectando
import com.altimetra.wear.presentation.theme.RojoDesconectado
import com.altimetra.wear.presentation.theme.VerdeConectado

@Composable
fun IndicadorConexion(
    estado: EstadoConexion
) {

    val (color, texto) = when (estado) {

        EstadoConexion.CONECTADO ->
            VerdeConectado to "Conectado"

        EstadoConexion.CONECTANDO ->
            AmarilloConectando to "Conectando..."

        EstadoConexion.DESCONECTADO ->
            RojoDesconectado to "Sin conexión"

    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {

        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color = color, shape = CircleShape)
        )

        Text(
            text = "  $texto",
            style = MaterialTheme.typography.labelMedium
        )

    }

}