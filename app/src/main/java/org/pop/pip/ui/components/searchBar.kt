package org.pop.pip.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
public fun PackageSearchBar(onSearch: (String) -> Unit = {}) {
    var searchValue by remember { mutableStateOf<String>(String()) }
    var oldValue by remember { mutableStateOf<String>(String()) }
    OutlinedTextField(
            value = searchValue,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { value -> searchValue = value },
            colors =
                    TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                    ),
            label = { Text(text = "input the package name") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            isError = false,
            keyboardActions =
                    KeyboardActions(
                            onDone = done@{
                                        if (oldValue == searchValue) return@done
                                        oldValue = searchValue
                                        onSearch(searchValue)
                                    }
                    )
    )
}
