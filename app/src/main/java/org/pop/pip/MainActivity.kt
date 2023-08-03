package org.pop.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.pop.pip.ui.theme.PopAndPipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TopUi() }
    }
}

@Composable
fun TopUi() {
    PopAndPipTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { LoginPage(navController) }
            composable("context") { SecondPage(navController) }
        }
    }
}

@Composable
fun LoginPage(navController: NavController) {
    var name = "sssss"
    Column {
        Text("$name is ")
        Text("$name is ")
        Button(onClick = { navController.navigate("context") }) { Text("To Second One") }
        Text("$name is ")
        Text("$name is ")
    }
}

@Composable
fun SecondPage(navController: NavController) {
    var name = "eeee"
    Column {
        Text("$name is ")
        Text("$name is ")
        Button(onClick = { navController.navigate("login") }) { Text("to Main Screen") }
        Text("$name is ")
        Text("$name is ")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PopAndPipTheme {}
}
