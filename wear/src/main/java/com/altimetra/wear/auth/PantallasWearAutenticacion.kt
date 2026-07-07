package com.altimetra.wear.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.Text
import com.altimetra.wear.R
import com.altimetra.wear.presentation.theme.AzulProfundo
import com.altimetra.wear.presentation.theme.BlancoNieve
import com.altimetra.wear.presentation.theme.RojoDesconectado
import kotlinx.coroutines.delay

@Composable
fun SplashWearPantalla(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(AzulProfundo),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_altimetra),
                contentDescription = "Logo ALTÍMETRA",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "ALTÍMETRA",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BlancoNieve
            )
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun PantallaBloqueoWear() {
    Box(
        modifier = Modifier.fillMaxSize().background(AzulProfundo).padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "⚠️\nAcceso Bloqueado",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = RojoDesconectado,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Inicia sesión o regístrate en la app móvil para activar el reloj.",
                fontSize = 11.sp,
                color = BlancoNieve.copy(alpha = 0.75f),
                textAlign = TextAlign.Center
            )
        }
    }
}