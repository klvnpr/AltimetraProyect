package com.altimetra.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.altimetra.mobile.presentation.pantallas.PantallaPrincipal
import com.altimetra.mobile.ui.theme.AltimetraProyectTheme
import com.altimetra.mobile.ui.theme.viewmodel.AltimetraViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: AltimetraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            AltimetraProyectTheme {

                PantallaPrincipal(
                    viewModel = viewModel
                )

            }

        }

    }

}