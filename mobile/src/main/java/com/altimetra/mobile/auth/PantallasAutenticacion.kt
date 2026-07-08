package com.altimetra.mobile.auth

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altimetra.mobile.R
import com.altimetra.mobile.ui.theme.AzulProfundo
import com.altimetra.mobile.ui.theme.NaranjaSendero
import com.altimetra.mobile.ui.theme.TealAltimetra
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Fondo degradado compartido por las pantallas de autenticación, inspirado
 * en los anillos de presión del logo ALTÍMETRA (navy -> teal).
 */
private fun fondoMarcaAltimetra() = Brush.verticalGradient(
    colors = listOf(AzulProfundo, TealAltimetra)
)

@Composable
fun SplashPantalla(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoMarcaAltimetra()),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_altimetra),
                contentDescription = "Logo ALTÍMETRA",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "ALTÍMETRA",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Presión y Altura a Tu Alcance",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(28.dp))
            CircularProgressIndicator(color = NaranjaSendero)
        }
    }
}

@Composable
fun LoginPantalla(onLoginExitoso: () -> Unit, onIrARegistro: () -> Unit, validar: (String, String) -> Boolean) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    // NUEVAS VARIABLES PARA EL CONTROL DE INTENTOS
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var intentosRestantes by remember { mutableIntStateOf(3) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoMarcaAltimetra())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_altimetra),
                contentDescription = "Logo ALTÍMETRA",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Iniciar Sesión",
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineMedium,
                        color = AzulProfundo
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = TealAltimetra) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = TealAltimetra) },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (errorMsg.isNotEmpty()) {
                        Text(text = errorMsg, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (validar(usuario, contrasena)) {
                                // Si acierta, reseteamos el mensaje y entramos
                                errorMsg = ""
                                onLoginExitoso()
                            } else {
                                // Si falla, restamos un intento
                                intentosRestantes -= 1

                                if (intentosRestantes > 0) {
                                    errorMsg = "Credenciales incorrectas, $intentosRestantes intentos restantes"
                                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                                } else {
                                    errorMsg = "Intentos excedidos. La aplicación se cerrará."
                                    Toast.makeText(context, "Lo siento, intentos excedidos. La aplicación se cerrará.", Toast.LENGTH_LONG).show()

                                    // Damos 2 segundos para que el usuario lea el mensaje y luego cerramos la app
                                    coroutineScope.launch {
                                        delay(2000)
                                        (context as? Activity)?.finishAffinity()
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NaranjaSendero),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        // Bloqueamos el botón si ya no quedan intentos para evitar múltiples clics
                        enabled = intentosRestantes > 0
                    ) {
                        Text("Ingresar", fontWeight = FontWeight.Bold)
                    }

                    TextButton(
                        onClick = onIrARegistro,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("¿No tienes cuenta? Regístrate", color = TealAltimetra)
                    }
                }
            }
        }
    }
}

@Composable
fun RegistroPantalla(onRegistroExitoso: () -> Unit, registrar: (String, String) -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoMarcaAltimetra())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_altimetra),
                contentDescription = "Logo ALTÍMETRA",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Crear Cuenta Local",
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineMedium,
                        color = AzulProfundo
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tus datos se guardarán solo en este teléfono.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Elige un Usuario") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = TealAltimetra) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Elige una Contraseña") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = TealAltimetra) },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (errorMsg.isNotEmpty()) {
                        Text(text = errorMsg, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                                registrar(usuario, contrasena)
                                onRegistroExitoso()
                            } else {
                                errorMsg = "Completa todos los campos"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TealAltimetra),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Registrarse", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}