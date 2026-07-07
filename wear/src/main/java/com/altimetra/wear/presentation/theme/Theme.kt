package com.altimetra.wear.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme

// Esquema de color del reloj basado en la paleta oficial ALTÍMETRA,
// para que la app en el reloj comparta la misma identidad visual
// (logo, azules, teal y naranja) que la app del teléfono.
private val AltimetraWearColorScheme = ColorScheme(
    primary = TealClaro,
    onPrimary = AzulProfundo,
    primaryContainer = AzulPetroleo,
    onPrimaryContainer = TealClaro,
    secondary = TealAltimetra,
    onSecondary = BlancoNieve,
    tertiary = NaranjaSendero,
    onTertiary = AzulProfundo,
    background = AzulProfundo,
    onBackground = BlancoNieve,
    surfaceContainer = SurfaceWear,
    onSurface = BlancoNieve,
)

@Composable
fun AltimetraProyectTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AltimetraWearColorScheme,
        content = content
    )
}