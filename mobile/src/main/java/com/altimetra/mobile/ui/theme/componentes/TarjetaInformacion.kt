package com.altimetra.mobile.ui.theme.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Tarjeta de información rediseñada con la identidad visual de ALTÍMETRA.
 *
 * Ahora admite un ícono y un color de acento propios, para que cada dato
 * (Altitud, Presión, Estado, Modo, Escenario) tenga una identidad visual
 * clara dentro del panel principal del teléfono.
 */
@Composable
fun TarjetaInformacion(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier,
    icono: ImageVector = Icons.Filled.Info,
    colorAcento: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(color = colorAcento.copy(alpha = 0.15f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = titulo,
                    tint = colorAcento,
                    modifier = Modifier.size(18.dp)
                )
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = 6.dp))

            Text(
                text = titulo,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}