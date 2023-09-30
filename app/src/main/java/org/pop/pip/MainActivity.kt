package org.pop.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.pop.pip.aur.*
import org.pop.pip.aur.Resource
import org.pop.pip.data.HttpViewModel
import org.pop.pip.ui.components.AurCardError
import org.pop.pip.ui.components.AurResultCard
import org.pop.pip.ui.components.PackageSearchBar
import org.pop.pip.ui.theme.PopAndPipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent { TopUi() }
        setContent {
            PopAndPipTheme {
                Surface(modifier = Modifier.fillMaxSize()) { Conversation() }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun Conversation() {
    val model = HttpViewModel()
    val state by model.state
    LazyColumn {
        item {
            Text(
                    text =
                            when (state) {
                                is Resource.Failure -> "Failure"
                                Resource.Loading -> "Loading"
                                Resource.Begin -> "Loading"
                                is Resource.Success -> "Success"
                            }
            )
        }

        item { PackageSearchBar(onSearch = { input -> model.searchPackage(input) }) }
        when (val smartCastData = state) {
            is Resource.Success ->
                    if (smartCastData.data.error != null) {
                        item { AurCardError(smartCastData.data.error) }
                    } else {
                        items(smartCastData.data.results) { message -> AurResultCard(message) }
                    }
            else -> item { Text(text = "Loading") }
        }
    }
}

@Composable
fun MessageCardR(msg: Message) {
    Row(
            modifier = Modifier.padding(all = 8.dp).fillMaxSize(),
            horizontalArrangement = Arrangement.End,
    ) {
        Column {
            Text(
                    textAlign = TextAlign.End,
                    text = msg.author,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
                Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
                painter = painterResource(R.drawable.lala),
                contentDescription = "contentDescription",
                modifier = Modifier.size(40.dp).clip(CircleShape)
        )
    }
}

@Composable
fun MessageCardL(msg: Message) {
    Row(
            modifier = Modifier.padding(all = 8.dp).fillMaxSize(),
    ) {
        Image(
                painter = painterResource(R.drawable.lala),
                contentDescription = "contentDescription",
                modifier = Modifier.size(40.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                    text = msg.author,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
                Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                )
            }
        }
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
