package com.altimetra.mobile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
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
    private val clienteMessage by lazy { ClienteMessageMobile(this) }

    // Lanzador para pedir permiso de notificaciones en Android 13+
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Pedir permiso de notificaciones si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != 
                PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Iniciamos el cliente de comunicación
        clienteMessage.iniciar { }

        // Si el usuario ya está logueado localmente, avisamos al reloj
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
                                sessionManager.setLoginState(false)
                                clienteMessage.enviarEstadoAutenticacion(false)
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
