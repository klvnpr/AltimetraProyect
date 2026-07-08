package com.altimetra.wear.presentation.pantallas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.altimetra.shared.modelo.ModoFuncionamiento
import com.altimetra.shared.modelo.TipoSimulacion
import com.altimetra.wear.presentation.componentes.SelectorOpcion
import com.altimetra.wear.presentation.componentes.TarjetaDato
import com.altimetra.wear.presentation.theme.NaranjaSendero
import com.altimetra.wear.presentation.theme.TealClaro
import com.altimetra.wear.presentation.viewmodel.AltimetraViewModel

@Composable
fun PantallaConfiguracion(

    viewModel: AltimetraViewModel

) {

    val estado = viewModel.estadoPantalla.value

    val modoSimulacionActivo = estado.modo == ModoFuncionamiento.SIMULACION

    ScalingLazyColumn(

        modifier = Modifier.fillMaxSize()

    ) {

        item {

            Text(
                text = "CONFIGURACIÓN",
                style = MaterialTheme.typography.titleMedium,
                color = TealClaro
            )

        }

        item {

            Text("")

        }

        item {

            Text(
                text = "Escenario",
                style = MaterialTheme.typography.labelMedium,
                color = if (modoSimulacionActivo) NaranjaSendero else Color.Gray.copy(alpha = 0.5f)
            )

        }

        item {

            SelectorOpcion(

                texto = "Ascenso",

                seleccionado =
                    modoSimulacionActivo && estado.simulacion == TipoSimulacion.ASCENSO,

                habilitado = modoSimulacionActivo

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
                    modoSimulacionActivo && estado.simulacion == TipoSimulacion.DESCENSO,

                habilitado = modoSimulacionActivo

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
                    modoSimulacionActivo && estado.simulacion == TipoSimulacion.ESTABLE,

                habilitado = modoSimulacionActivo

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
                    modoSimulacionActivo && estado.simulacion == TipoSimulacion.TORMENTA,

                habilitado = modoSimulacionActivo

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
                text = "Modo",
                style = MaterialTheme.typography.labelMedium,
                color = TealClaro
            )

        }

        item {

            SelectorOpcion(

                texto = "Simulación",

                seleccionado = estado.modo == ModoFuncionamiento.SIMULACION,

                onClick = { 
                    viewModel.cambiarSimulacion(estado.simulacion)
                }

            )

        }

        item {

            SelectorOpcion(

                texto = "Sensor real",

                seleccionado = estado.modo == ModoFuncionamiento.REAL,

                onClick = { 
                    viewModel.usarSensorReal()
                }

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