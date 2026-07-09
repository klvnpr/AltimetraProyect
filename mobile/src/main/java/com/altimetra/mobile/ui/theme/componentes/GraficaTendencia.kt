package com.altimetra.mobile.ui.theme.componentes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altimetra.mobile.ui.theme.TealAltimetra

@Composable
fun GraficaTendencia(
    datos: List<Double>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Tendencia de Presión (Últimos 60s)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TealAltimetra
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        if (datos.size < 2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Esperando datos...", color = Color.Gray)
            }
            return
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            val minPresion = datos.minOrNull() ?: 0.0
            val maxPresion = datos.maxOrNull() ?: 1.0
            val range = (maxPresion - minPresion).coerceAtLeast(0.1)
            
            val width = size.width
            val height = size.height
            val spacing = width / (datos.size - 1)

            val points = datos.mapIndexed { index, value ->
                val x = index * spacing
                val y = height - ((value - minPresion) / range * height).toFloat()
                Offset(x, y.toFloat())
            }

            val path = Path().apply {
                moveTo(points[0].x, points[0].y)
                for (i in 1 until points.size) {
                    lineTo(points[i].x, points[i].y)
                }
            }

            // Dibujar la línea
            drawPath(
                path = path,
                color = TealAltimetra,
                style = Stroke(width = 3.dp.toPx())
            )

            // Dibujar el área bajo la curva (Gradiente)
            val fillPath = Path().apply {
                addPath(path)
                lineTo(points.last().x, height)
                lineTo(points.first().x, height)
                close()
            }
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(TealAltimetra.copy(alpha = 0.3f), Color.Transparent)
                )
            )
        }
    }
}
