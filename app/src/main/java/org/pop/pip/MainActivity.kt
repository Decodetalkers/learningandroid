package org.pop.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Scaffold(
                floatingActionButton = { FloatActionBtn() },
                bottomBar = { PopAndPipBottomBar(listOf("login", "context"), navController) }
        ) { padding ->
            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginPage(padding) }
                composable("context") { SecondPage() }
            }
        }
    }
}

@Composable
fun FloatActionBtn() {
    ExtendedFloatingActionButton(
            onClick = { println("ss") },
            icon = { Icon(Icons.Filled.Favorite, "Localized description") },
            text = { Text(text = "Extended FAB") },
    )
}

@Composable
fun LoginPage(dp: PaddingValues) {
    val name = "sssss"
    Column(modifier = Modifier.padding(dp)) {
        Text("$name is ")
        Text("$name is ")
        Text("$name is ")
    }
}

@Composable
fun PopAndPipBottomBar(list: List<String>, navController: NavController) {

    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        list.forEachIndexed { index, item ->
            NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item)
                    }
            )
        }
    }
}

@Composable
fun SecondPage() {
    val name = "eeee"
    Column {
        Text("$name is ")
        Text("$name is ")
        Text("$name is ")
        Text("$name is ")
    }
}
