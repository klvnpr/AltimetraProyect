package com.altimetra.wear.presentation.pantallas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import com.altimetra.shared.modelo.TipoSimulacion
import com.altimetra.wear.presentation.componentes.SelectorOpcion
import com.altimetra.wear.presentation.componentes.TarjetaDato
import com.altimetra.wear.presentation.viewmodel.AltimetraViewModel

@Composable
fun PantallaConfiguracion(

    viewModel: AltimetraViewModel

) {

    val estado = viewModel.estadoPantalla.value

    ScalingLazyColumn(

        modifier = Modifier.fillMaxSize()

    ) {

        item {

            Text("CONFIGURACIÓN")

        }

        item {

            Text("")

        }

        item {

            Text(
                text = "Escenario"
            )

        }

        item {

            SelectorOpcion(

                texto = "Ascenso",

                seleccionado =
                    estado.simulacion == TipoSimulacion.ASCENSO

            ) {

                viewModel.cambiarSimulacion(
                    TipoSimulacion.ASCENSO
                )

            }

        }

        item {

            SelectorOpcion(

                texto = "Descenso",

                seleccionado =
                    estado.simulacion == TipoSimulacion.DESCENSO

            ) {

                viewModel.cambiarSimulacion(
                    TipoSimulacion.DESCENSO
                )

            }

        }

        item {

            SelectorOpcion(

                texto = "Estable",

                seleccionado =
                    estado.simulacion == TipoSimulacion.ESTABLE

            ) {

                viewModel.cambiarSimulacion(
                    TipoSimulacion.ESTABLE
                )

            }

        }

        item {

            SelectorOpcion(

                texto = "Tormenta",

                seleccionado =
                    estado.simulacion == TipoSimulacion.TORMENTA

            ) {

                viewModel.cambiarSimulacion(
                    TipoSimulacion.TORMENTA
                )

            }

        }

        item {

            Text("")

        }

        item {

            Text(
                text = "Modo"
            )

        }

        item {

            SelectorOpcion(

                texto = "Simulación",

                seleccionado = true,

                onClick = { }

            )

        }

        item {

            SelectorOpcion(

                texto = "Sensor real (Próximamente)",

                seleccionado = false,

                onClick = { }

            )

        }


        item {
            TarjetaDato(
                titulo = "Lecturas",
                valor = estado.cantidadLecturas.toString()
            )
        }

        item {

            Button(

                onClick = {

                    viewModel.cerrarConfiguracion()

                }

            ) {

                Text("Volver")

            }

        }

    }

}