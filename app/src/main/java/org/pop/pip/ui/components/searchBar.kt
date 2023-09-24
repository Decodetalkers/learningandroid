package org.pop.pip.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
public fun searchBar(onConfirmed: (String) -> Unit = {}) {
    OutlinedTextField(
            value = "",
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxSize(),
            onValueChange = {},
            colors =
                    TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                    ),
            label = { Text(text = "sss") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            isError = false,
            keyboardActions = KeyboardActions(onDone = { onConfirmed("") })
    )
}
