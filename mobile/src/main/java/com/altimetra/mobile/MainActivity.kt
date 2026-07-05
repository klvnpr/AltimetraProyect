package com.altimetra.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.altimetra.mobile.presentation.pantallas.PantallaPrincipal
import com.altimetra.mobile.auth.SplashPantalla
import com.altimetra.mobile.auth.LoginPantalla
import com.altimetra.mobile.auth.RegistroPantalla
import com.altimetra.mobile.repositorio.SessionManager
import com.altimetra.mobile.comunicacion.ClienteMessageMobile
import com.altimetra.mobile.ui.theme.AltimetraProyectTheme
import com.altimetra.mobile.ui.theme.viewmodel.AltimetraViewModel

enum class DestinoMovil { SPLASH, LOGIN, REGISTRO, APP_PRINCIPAL }

class MainActivity : ComponentActivity() {

    private val viewModel: AltimetraViewModel by viewModels()
    private val sessionManager by lazy { SessionManager(this) }

    // Inicializamos el cliente de mensajes móvil
    private val clienteMessage by lazy { ClienteMessageMobile(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Iniciamos el cliente de comunicación (necesario por la estructura de tu compañero)
        clienteMessage.iniciar { /* Recibe lecturas si fuera necesario en la UI */ }

        // Si el usuario ya está logueado localmente en el SharedPreferences,
        // le avisamos proactivamente al reloj desde el arranque de la app.
        if (sessionManager.isUserLoggedIn()) {
            clienteMessage.enviarEstadoAutenticacion(true)
        }

        setContent {
            AltimetraProyectTheme {
                var pantallaActual by remember { mutableStateOf(DestinoMovil.SPLASH) }

                when (pantallaActual) {
                    DestinoMovil.SPLASH -> {
                        SplashPantalla(onTimeout = {
                            if (sessionManager.isUserLoggedIn()) {
                                pantallaActual = DestinoMovil.APP_PRINCIPAL
                            } else if (sessionManager.existeUsuario()) {
                                pantallaActual = DestinoMovil.LOGIN
                            } else {
                                pantallaActual = DestinoMovil.REGISTRO
                            }
                        })
                    }
                    DestinoMovil.REGISTRO -> {
                        RegistroPantalla(
                            onRegistroExitoso = {
                                sessionManager.setLoginState(true)
                                // ENVIAR SEÑAL AL RELOJ
                                clienteMessage.enviarEstadoAutenticacion(true)
                                pantallaActual = DestinoMovil.APP_PRINCIPAL
                            },
                            registrar = { u, c -> sessionManager.registrarUsuario(u, c) }
                        )
                    }
                    DestinoMovil.LOGIN -> {
                        LoginPantalla(
                            onLoginExitoso = {
                                sessionManager.setLoginState(true)
                                // ENVIAR SEÑAL AL RELOJ
                                clienteMessage.enviarEstadoAutenticacion(true)
                                pantallaActual = DestinoMovil.APP_PRINCIPAL
                            },
                            onIrARegistro = { pantallaActual = DestinoMovil.REGISTRO },
                            validar = { u, c -> sessionManager.validarCredenciales(u, c) }
                        )
                    }
                    DestinoMovil.APP_PRINCIPAL -> {
                        PantallaPrincipal(
                            viewModel = viewModel,
                            onCerrarSesion = {
                                // 1. Borramos la sesión en el teléfono
                                sessionManager.setLoginState(false)
                                // 2. Bloqueamos la app del reloj enviándole "false"
                                clienteMessage.enviarEstadoAutenticacion(false)
                                // 3. Devolvemos al usuario a la pantalla de Login
                                pantallaActual = DestinoMovil.LOGIN
                            }
                        )
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