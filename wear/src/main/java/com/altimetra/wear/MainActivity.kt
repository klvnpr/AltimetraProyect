package com.altimetra.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.altimetra.wear.comunicacion.ClienteMessageWear
import com.altimetra.wear.presentation.factory.AltimetraViewModelFactory
import com.altimetra.wear.presentation.pantallas.PantallaPrincipal
import com.altimetra.wear.presentation.theme.AltimetraProyectTheme
import com.altimetra.wear.presentation.viewmodel.AltimetraViewModel

class MainActivity : ComponentActivity() {

    private val clienteMessage by lazy {

        ClienteMessageWear(this)

    }

    private val viewModel: AltimetraViewModel by viewModels {

        AltimetraViewModelFactory(
            clienteMessage
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            AltimetraProyectTheme {

                PantallaPrincipal(
                    viewModel = viewModel
                )

            }

        }

    }

}