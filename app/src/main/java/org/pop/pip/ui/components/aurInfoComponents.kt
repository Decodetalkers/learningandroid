package org.pop.pip.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.pop.pip.aur.AurResult

@Composable
fun AurResultCard(data: AurResult) {
    Column(modifier = Modifier.padding(all = 8.dp).fillMaxSize()) {
        Text(
                text = data.Name,
                color = MaterialTheme.colorScheme.primary,
        )
        Text(text = data.URLPath, textAlign = TextAlign.End)
    }
}

@Composable
fun AurCardError(err: String) {
    Text(text = err)
}
