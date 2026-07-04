package com.altimetra.wear.presentation.pantallas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.altimetra.wear.presentation.componentes.IndicadorConexion
import com.altimetra.wear.presentation.componentes.TarjetaDato
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Text(
                text = "ALTÍMETRA",
                style = MaterialTheme.typography.titleLarge
            )

        }

        item {

            IndicadorConexion(

                estado = estado.conexion

            )

        }

        item {

            TarjetaDato(
                titulo = "Presión",
                valor = String.format("%.2f hPa", estado.presion)
            )
        }



        item {

            TarjetaDato(
                titulo = "Altitud",
                valor = String.format("%.1f m", estado.altitud)
            )

        }

        item {

            TarjetaDato(
                titulo = "Estado",
                valor = estado.estado.name
            )

        }
        item {

            TarjetaDato(
                titulo = "Escenario",
                valor = estado.simulacion.name
            )

        }

        item {
            TarjetaDato(
                titulo = "Modo",
                valor = estado.modo.name
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