package com.altimetra.wear.presentation.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.altimetra.wear.presentation.theme.TealClaro

@Composable
fun SelectorOpcion(

    texto: String,

    seleccionado: Boolean,

    onClick: () -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = if (seleccionado) TealClaro.copy(alpha = 0.15f) else Color.Transparent,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 10.dp, vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Text(
            text = texto,
            style = MaterialTheme.typography.labelMedium,
            color = if (seleccionado) TealClaro else MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = if (seleccionado) TealClaro else Color.Gray.copy(alpha = 0.4f),
                    shape = CircleShape
                )
        )

    }

}