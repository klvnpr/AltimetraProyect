package com.altimetra.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.altimetra.wear.comunicacion.ClienteMessageWear
import com.altimetra.wear.presentation.factory.AltimetraViewModelFactory
import com.altimetra.wear.presentation.pantallas.PantallaPrincipal
import com.altimetra.wear.auth.SplashWearPantalla
import com.altimetra.wear.auth.PantallaBloqueoWear
import com.altimetra.wear.presentation.theme.AltimetraProyectTheme
import com.altimetra.wear.presentation.viewmodel.AltimetraViewModel

enum class DestinoWear { SPLASH, BLOQUEADO, APP_PRINCIPAL }

class MainActivity : ComponentActivity() {

    private val clienteMessage by lazy {
        ClienteMessageWear(this)
    }

    private val viewModel: AltimetraViewModel by viewModels {
        AltimetraViewModelFactory(clienteMessage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AltimetraProyectTheme {
                var pantallaActual by remember { mutableStateOf(DestinoWear.SPLASH) }
                var cuentaEstaActivaEnTelefono by remember { mutableStateOf(false) }

                // ESCUCHA BLUETOOTH REAL EN TIEMPO REAL:
                // Se ejecuta una sola vez al iniciar la actividad
                LaunchedEffect(Unit) {
                    clienteMessage.iniciar { activa ->
                        cuentaEstaActivaEnTelefono = activa
                    }
                }

                when (pantallaActual) {
                    DestinoWear.SPLASH -> {
                        SplashWearPantalla(onTimeout = {
                            if (cuentaEstaActivaEnTelefono) {
                                pantallaActual = DestinoWear.APP_PRINCIPAL
                            } else {
                                pantallaActual = DestinoWear.BLOQUEADO
                            }
                        })
                    }
                    DestinoWear.BLOQUEADO -> {
                        PantallaBloqueoWear()

                        // Si cambia el estado por Bluetooth mientras está bloqueado,
                        // se desbloquea automáticamente sin reiniciar la app
                        if (cuentaEstaActivaEnTelefono) {
                            pantallaActual = DestinoWear.APP_PRINCIPAL
                        }
                    }
                    DestinoWear.APP_PRINCIPAL -> {
                        PantallaPrincipal(viewModel = viewModel)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clienteMessage.detener()
    }
}