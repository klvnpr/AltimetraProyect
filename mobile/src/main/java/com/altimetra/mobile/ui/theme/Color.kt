package com.altimetra.mobile.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================================
// Paleta de colores ALTÍMETRA
// Extraída del logo oficial (montañas + anillos de presión + traza naranja)
// ============================================================

// --- Tonos principales (identidad de marca) ---
val AzulProfundo = Color(0xFF0F2A3B)      // Fondo del círculo del logo (navy profundo)
val AzulPetroleo = Color(0xFF16404D)      // Anillo intermedio, tono oscuro
val TealAltimetra = Color(0xFF1E7A8C)     // Anillo principal (teal medio) -> color primario
val TealClaro = Color(0xFF4FC3C7)         // Anillo brillante (teal claro) -> acentos/realces
val NaranjaSendero = Color(0xFFE8813F)    // Traza naranja del sendero -> color de acción/alerta
val BlancoNieve = Color(0xFFF4FAFB)       // Blanco frío de las montañas / fondo claro

// --- Esquema claro (Light) ---
val Primary40 = TealAltimetra
val Secondary40 = AzulPetroleo
val Tertiary40 = NaranjaSendero
val Background40 = BlancoNieve
val Surface40 = Color(0xFFFFFFFF)
val OnPrimary40 = Color(0xFFFFFFFF)
val OnSurface40 = AzulProfundo

// --- Esquema oscuro (Dark) ---
val Primary80 = TealClaro
val Secondary80 = Color(0xFF7FB8C4)
val Tertiary80 = Color(0xFFFFA870)
val Background80 = AzulProfundo
val Surface80 = Color(0xFF163244)
val OnPrimary80 = AzulProfundo
val OnSurface80 = BlancoNieve

// --- Colores de estado (usados en tarjetas e indicadores) ---
val VerdeConectado = Color(0xFF3FAE6B)
val RojoDesconectado = Color(0xFFE05A4E)