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
import com.altimetra.wear.repositorio.SessionManagerWear

enum class DestinoWear { SPLASH, BLOQUEADO, APP_PRINCIPAL }

class MainActivity : ComponentActivity() {

    private val clienteMessage by lazy {
        ClienteMessageWear(this)
    }

    private val sessionManager by lazy {
        SessionManagerWear(this)
    }

    private val viewModel: AltimetraViewModel by viewModels {
        AltimetraViewModelFactory(application, clienteMessage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AltimetraProyectTheme {
                // Iniciamos con el estado guardado localmente para que sea instantáneo
                var cuentaEstaActivaEnTelefono by remember {
                    mutableStateOf(sessionManager.isUserLoggedIn())
                }
                var pantallaActual by remember { mutableStateOf(DestinoWear.SPLASH) }

                // ESCUCHA BLUETOOTH REAL EN TIEMPO REAL:
                LaunchedEffect(Unit) {
                    clienteMessage.iniciar { activa ->
                        cuentaEstaActivaEnTelefono = activa
                        // Guardamos localmente lo que diga el teléfono para la próxima vez
                        sessionManager.setLoginState(activa)
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

                        if (cuentaEstaActivaEnTelefono) {
                            pantallaActual = DestinoWear.APP_PRINCIPAL
                        }
                    }
                    DestinoWear.APP_PRINCIPAL -> {
                        PantallaPrincipal(viewModel = viewModel)

                        // Si de pronto el teléfono dice que ya no está logueado, bloqueamos
                        if (!cuentaEstaActivaEnTelefono) {
                            pantallaActual = DestinoWear.BLOQUEADO
                        }
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