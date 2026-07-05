package com.altimetra.mobile.auth

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashPantalla(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "ALTIMETRA", fontSize = 32.sp, color = Color.White, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Montañismo Seguro", fontSize = 16.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator(color = Color.White)
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

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Iniciar Sesión", fontSize = 28.sp, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(value = usuario, onValueChange = { usuario = it }, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = contrasena, onValueChange = { contrasena = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

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
            modifier = Modifier.fillMaxWidth(),
            // Bloqueamos el botón si ya no quedan intentos para evitar múltiples clics
            enabled = intentosRestantes > 0
        ) {
            Text("Ingresar")
        }
    }
}

@Composable
fun RegistroPantalla(onRegistroExitoso: () -> Unit, registrar: (String, String) -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Crear Cuenta Local", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tus datos se guardarán solo en este teléfono.", fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(value = usuario, onValueChange = { usuario = it }, label = { Text("Elige un Usuario") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = contrasena, onValueChange = { contrasena = it }, label = { Text("Elige una Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}