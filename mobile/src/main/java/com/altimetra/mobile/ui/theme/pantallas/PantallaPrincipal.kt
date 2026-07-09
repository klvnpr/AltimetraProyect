package com.altimetra.mobile.presentation.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.altimetra.mobile.R
import com.altimetra.mobile.ui.theme.NaranjaSendero
import com.altimetra.mobile.ui.theme.RojoDesconectado
import com.altimetra.mobile.ui.theme.TealAltimetra
import com.altimetra.mobile.ui.theme.TealClaro
import com.altimetra.mobile.ui.theme.VerdeConectado
import com.altimetra.mobile.ui.theme.componentes.GraficaTendencia
import com.altimetra.mobile.ui.theme.componentes.TarjetaInformacion
import com.altimetra.mobile.ui.theme.viewmodel.AltimetraViewModel

@Composable
fun PantallaPrincipal(
    viewModel: AltimetraViewModel,
    onCerrarSesion: () -> Unit // Recibe la acción desde MainActivity
) {

    val estado = viewModel.estadoPantalla.value

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // --- Encabezado ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(TealAltimetra, MaterialTheme.colorScheme.secondary)
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_altimetra),
                                contentDescription = "Logo ALTÍMETRA",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                            )
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(
                                    text = "ALTÍMETRA",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White
                                )
                                Text(
                                    text = "Presión y Altura a Tu Alcance",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }

                        TextButton(onClick = onCerrarSesion) {
                            Icon(
                                imageVector = Icons.Filled.Logout,
                                contentDescription = "Cerrar sesión",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "  Salir",
                                color = Color.White
                            )
                        }
                    }

                    // --- Chip de estado de conexión ---
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .background(
                                    color = if (estado.conectado) VerdeConectado else RojoDesconectado,
                                    shape = CircleShape
                                )
                        )
                        Text(
                            text = if (estado.conectado) "  Reloj conectado" else "  Reloj desconectado",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }

            // --- Contenido: tarjetas de datos ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // GRÁFICA DE TENDENCIA
                GraficaTendencia(
                    datos = estado.historialPresion,
                    modifier = Modifier.fillMaxWidth()
                )

                TarjetaInformacion(
                    titulo = "Altitud",
                    valor = String.format("%.2f m", estado.altitud),
                    icono = Icons.Filled.Terrain,
                    colorAcento = TealAltimetra,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TarjetaInformacion(
                        titulo = "Presión",
                        valor = String.format("%.2f hPa", estado.presion),
                        icono = Icons.Filled.Speed,
                        colorAcento = NaranjaSendero,
                        modifier = Modifier.weight(1f)
                    )

                    TarjetaInformacion(
                        titulo = "Estado",
                        valor = estado.estado.name,
                        icono = Icons.Filled.Landscape,
                        colorAcento = TealClaro,
                        modifier = Modifier.weight(1f)
                    )
                }

                val esSimulacion = estado.modo == com.altimetra.shared.modelo.ModoFuncionamiento.SIMULACION

                Row(
                    horizontalArrangement = if (esSimulacion) Arrangement.spacedBy(12.dp) else Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TarjetaInformacion(
                        titulo = "Modo",
                        valor = estado.modo.name,
                        icono = Icons.Filled.Tune,
                        colorAcento = MaterialTheme.colorScheme.secondary,
                        modifier = if (esSimulacion) Modifier.weight(1f) else Modifier.fillMaxWidth(0.48f)
                    )

                    if (esSimulacion) {
                        TarjetaInformacion(
                            titulo = "Escenario",
                            valor = estado.simulacion.name,
                            icono = Icons.Filled.Terrain,
                            colorAcento = NaranjaSendero,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
