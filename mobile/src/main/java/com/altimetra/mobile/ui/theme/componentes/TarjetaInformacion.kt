package com.altimetra.mobile.ui.theme.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaInformacion(

    titulo: String,

    valor: String,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier,

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(

                text = titulo,

                style = MaterialTheme.typography.labelLarge

            )

            Text(

                text = valor,

                style = MaterialTheme.typography.headlineSmall

            )

        }

    }

}