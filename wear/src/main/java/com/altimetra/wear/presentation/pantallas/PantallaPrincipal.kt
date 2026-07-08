package com.altimetra.wear.presentation.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.altimetra.wear.R
import com.altimetra.wear.presentation.componentes.IndicadorConexion
import com.altimetra.wear.presentation.componentes.TarjetaDato
import com.altimetra.wear.presentation.theme.NaranjaSendero
import com.altimetra.wear.presentation.theme.TealAltimetra
import com.altimetra.wear.presentation.theme.TealClaro
import com.altimetra.wear.presentation.viewmodel.AltimetraViewModel


@Composable
fun PantallaPrincipal(
    viewModel: AltimetraViewModel
) {


    val estado = viewModel.estadoPantalla.value

    if (estado.mostrarConfiguracion) {

        PantallaConfiguracion(viewModel)

        return

    }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- Encabezado de marca con logo ALTÍMETRA ---
        item {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Image(
                    painter = painterResource(id = R.drawable.logo_altimetra),
                    contentDescription = "Logo ALTÍMETRA",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = "ALTÍMETRA",
                    style = MaterialTheme.typography.titleLarge,
                    color = TealClaro,
                    modifier = Modifier.padding(top = 4.dp)
                )

            }

        }

        item {

            IndicadorConexion(

                estado = estado.conexion

            )

        }

        item {

            TarjetaDato(
                titulo = "Presión",
                valor = String.format("%.2f hPa", estado.presion),
                icono = Icons.Filled.Speed,
                colorAcento = NaranjaSendero
            )
        }



        item {

            TarjetaDato(
                titulo = "Altitud",
                valor = String.format("%.1f m", estado.altitud),
                icono = Icons.Filled.Terrain,
                colorAcento = TealAltimetra
            )

        }

        item {

            TarjetaDato(
                titulo = "Estado",
                valor = estado.estado.name,
                icono = Icons.Filled.Landscape,
                colorAcento = TealClaro
            )

        }
        item {

            TarjetaDato(
                titulo = "Escenario",
                valor = estado.simulacion.name,
                icono = Icons.Filled.Terrain,
                colorAcento = NaranjaSendero
            )

        }

        item {
            TarjetaDato(
                titulo = "Modo",
                valor = estado.modo.name,
                icono = Icons.Filled.Tune,
                colorAcento = MaterialTheme.colorScheme.secondary
            )
        }

        item {

            Button(

                onClick = {

                    viewModel.abrirConfiguracion()

                }

            ) {

                Text("Configuración")

            }

        }

    }
}