package com.altimetra.mobile.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Esquema oscuro basado en la paleta del logo ALTÍMETRA
private val DarkColorScheme = darkColorScheme(
    primary = Primary80,
    onPrimary = OnPrimary80,
    secondary = Secondary80,
    tertiary = Tertiary80,
    background = Background80,
    onBackground = OnSurface80,
    surface = Surface80,
    onSurface = OnSurface80
)

// Esquema claro basado en la paleta del logo ALTÍMETRA
private val LightColorScheme = lightColorScheme(
    primary = Primary40,
    onPrimary = OnPrimary40,
    secondary = Secondary40,
    tertiary = Tertiary40,
    background = Background40,
    onBackground = OnSurface40,
    surface = Surface40,
    onSurface = OnSurface40
)

@Composable
fun AltimetraProyectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Se desactiva el color dinámico (Material You) para que la app siempre
    // respete la identidad visual del logo ALTÍMETRA, sin importar el
    // fondo de pantalla del dispositivo.
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}