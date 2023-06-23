package org.pop.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.pop.pip.ui.theme.PopAndPipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PopAndPipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) { Greeting("Android") }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        Text("$name is ")
        Text("$name is ")
        Button(onClick = { println("ss") }) { Text("beta") }
        Text("$name is ")
        Text("$name is ")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PopAndPipTheme { Greeting("Android") }
}
