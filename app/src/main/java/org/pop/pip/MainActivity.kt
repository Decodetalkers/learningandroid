package org.pop.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.pop.pip.aur.*
import org.pop.pip.data.DetailModel
import org.pop.pip.data.HttpViewModel
import org.pop.pip.data.SearchPanelModel
import org.pop.pip.ui.components.*
import org.pop.pip.ui.theme.PopAndPipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PopAndPipTheme { Surface(modifier = Modifier.fillMaxSize()) { MainPage() } } }
    }
}

@Composable
fun MainPage() {
    val navController = rememberNavController()
    val detailModel: DetailModel = viewModel()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { TopUi(topNav = navController, detailModel = detailModel) }
        composable("DetailPage") {
            DetailPage(navController = navController, detailModel = detailModel)
        }
    }
}

@Composable
fun DetailPage(dp: PaddingValues? = null, navController: NavController, detailModel: DetailModel) {
    val aurInfo by detailModel.detailData
    if (aurInfo == null) return
    val modifier =
            Modifier.fillMaxSize().let done@{
                if (dp == null) return@done it
                it.padding(dp)
            }
    Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
                modifier = Modifier.clip(CircleShape),
                painter = painterResource(R.drawable.lala),
                contentDescription = "contentDescription",
        )
        Button(onClick = { navController.navigateUp() }) { Text(text = aurInfo!!.Name) }
    }
}

@Composable
fun TopUi(topNav: NavController, detailModel: DetailModel) {
    val navController = rememberNavController()
    val viewModel: HttpViewModel = viewModel()
    val searchModel: SearchPanelModel = viewModel()
    Scaffold(bottomBar = { PopAndPipBottomBar(listOf("search", "about"), navController) }) { padding
        ->
        NavHost(navController = navController, startDestination = "search") {
            composable("search") {
                SearchResultPage(
                        viewModel = viewModel,
                        searchModel = searchModel,
                        detailModel = detailModel,
                        dp = padding,
                        navController = topNav
                )
            }
            composable("about") { AboutPage(dp = padding) }
        }
    }
}

@Composable
fun SearchResultPage(
        viewModel: HttpViewModel = viewModel(),
        searchModel: SearchPanelModel = viewModel(),
        detailModel: DetailModel = viewModel(),
        dp: PaddingValues? = null,
        navController: NavController
) {
    val state by viewModel.state
    val searchValue by searchModel.searchValue
    val oldValue by searchModel.oldValue
    Column(
            modifier =
                    Modifier.let done@{
                        if (dp == null) return@done it
                        it.padding(dp)
                    }
    ) {
        PackageSearchBar(
                searchValue = searchValue,
                onValueChanged = { value -> searchModel.onValueChanged(value) },
                onSearch = done@{
                            if (oldValue == searchValue) return@done
                            searchModel.updateOldValue()
                            viewModel.searchPackage(searchValue)
                        }
        )

        when (val smartCastData = state) {
            is Resource.Success ->
                    if (smartCastData.data.error != null) {
                        Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AurCardError(
                                    modifier = Modifier.fillMaxWidth(),
                                    err = smartCastData.data.error,
                                    textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                                modifier = Modifier.padding(4.dp),
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            items(smartCastData.data.results) { message ->
                                AurResultCard(
                                        data = message,
                                        modifier =
                                                Modifier.padding(all = 2.dp)
                                                        .fillMaxSize()
                                                        .clickable {
                                                            detailModel.setData(message)
                                                            navController.navigate("DetailPage")
                                                        }
                                )
                            }
                            item { Spacer(modifier = Modifier.height(4.dp)) }
                            item {
                                Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Above is all I can provide to you",
                                        textAlign = TextAlign.Center,
                                        fontSize = 15.sp,
                                )
                            }
                        }
                    }
            is Resource.Failure ->
                    Text(
                            modifier = Modifier.fillMaxSize(),
                            text = smartCastData.message,
                            textAlign = TextAlign.Center
                    )
            Resource.Begin ->
                    Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Today is a good day, doesn't it?",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                        )
                    }
            else ->
                    Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                trackColor = MaterialTheme.colorScheme.secondary,
                        )
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
fun AboutPage(dp: PaddingValues? = null) {
    val modifier =
            Modifier.fillMaxSize().let done@{
                if (dp == null) return@done it
                it.padding(dp)
            }
    Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
                modifier = Modifier.clip(CircleShape),
                painter = painterResource(R.drawable.lala),
                contentDescription = "contentDescription",
        )
    }
}
