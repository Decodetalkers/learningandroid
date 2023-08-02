package org.pop.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.pop.pip.ui.theme.PopAndPipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainUi(name = "ssscc") }
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        Text("$name is ")
        Text("$name is ")
        Button(onClick = { println("ss") }) { Text("beta2") }
        Text("$name is ")
        Text("$name is ")
    }
}

@Composable
fun MainUi(name: String) {
    PopAndPipTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { Greeting(name) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PopAndPipTheme { Greeting("Android") }
}
