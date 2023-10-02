package org.pop.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(navController: NavController, detailModel: DetailModel) {
    val aurInfo by detailModel.detailData
    if (aurInfo == null) return

    Scaffold(
            topBar = {
                TopAppBar(
                        colors =
                                TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                        title = { Text(text = aurInfo!!.Name) },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Localized description"
                                )
                            }
                        },
                )
            }
    ) { padding ->
        AurResultFullCard(
                modifier = Modifier.fillMaxSize().padding(padding).padding(all = 10.dp),
                data = aurInfo!!
        )
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
    var expanded by remember { mutableStateOf(false) }
    val state by viewModel.state
    val searchValue by searchModel.searchValue
    val oldValue by searchModel.oldValue
    var requestType by remember { mutableStateOf(RequestType.Package) }
    var requestTypeOld by remember { mutableStateOf(RequestType.Package) }
    val foucsManager = LocalFocusManager.current
    Column(
            modifier =
                    Modifier.let done@{
                        if (dp == null) return@done it
                        it.padding(dp)
                    }
    ) {
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
        ) {
            PackageSearchBar(
                    modifier = Modifier.weight(1f),
                    searchValue = searchValue,
                    onValueChanged = { value -> searchModel.onValueChanged(value) },
                    onSearch = done@{
                                foucsManager.clearFocus()
                                if (oldValue == searchValue &&
                                                requestType.toName() == requestTypeOld.toName()
                                )
                                        return@done
                                requestTypeOld = requestType
                                searchModel.updateOldValue()
                                viewModel.searchPackage(
                                        packageName = searchValue,
                                        requestType = requestType
                                )
                            }
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box {
                TextButton(
                        onClick = {
                            expanded = !expanded
                            foucsManager.clearFocus()
                        }
                ) { Text(text = requestType.toName()) }
                DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                            text = { Text(RequestType.Package.toName()) },
                            onClick = {
                                requestType = RequestType.Package
                                expanded = false
                            }
                    )
                    DropdownMenuItem(
                            text = { Text(RequestType.MakeDepends.toName()) },
                            onClick = {
                                requestType = RequestType.MakeDepends
                                expanded = false
                            }
                    )
                    DropdownMenuItem(
                            text = { Text(RequestType.User.toName()) },
                            onClick = {
                                requestType = RequestType.User
                                expanded = false
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.width(2.dp))
        }

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

@Preview
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
