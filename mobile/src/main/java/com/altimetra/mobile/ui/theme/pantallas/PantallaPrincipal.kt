package com.altimetra.mobile.presentation.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.altimetra.mobile.ui.theme.componentes.TarjetaInformacion
import com.altimetra.mobile.ui.theme.viewmodel.AltimetraViewModel

@Composable
fun PantallaPrincipal(
    viewModel: AltimetraViewModel,
    onCerrarSesion: () -> Unit // Recibe la acción desde MainActivity
) {

    val estado = viewModel.estadoPantalla.value

    Surface {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Agrupamos el título y el botón en un Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ALTÍMETRA",
                    style = MaterialTheme.typography.headlineLarge
                )

                // NUEVO: Botón de cerrar sesión
                TextButton(onClick = onCerrarSesion) {
                    Text(text = "Cerrar sesión", color = Color.Red)
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .background(
                            color = if (estado.conectado) Color(0xFF4CAF50) else Color.Red,
                            shape = CircleShape
                        )
                        .padding(7.dp)
                )

                Text(
                    text = if (estado.conectado) " Conectado" else " Desconectado",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            TarjetaInformacion(
                titulo = "Altitud",
                valor = String.format("%.1f m", estado.altitud),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                TarjetaInformacion(
                    titulo = "Presión",
                    valor = String.format("%.2f hPa", estado.presion),
                    modifier = Modifier.weight(1f)
                )

                TarjetaInformacion(
                    titulo = "Estado",
                    valor = estado.estado.name,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                TarjetaInformacion(
                    titulo = "Modo",
                    valor = estado.modo.name,
                    modifier = Modifier.weight(1f)
                )

                TarjetaInformacion(
                    titulo = "Escenario",
                    valor = estado.simulacion.name,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}