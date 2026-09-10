package com.example.firstkmp

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.firstkmp.data.KtorClient
import com.example.firstkmp.data.LayerItem
import com.example.firstkmp.presentation.LayerViewModel


@Composable
@Preview
fun App() {


    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(Screen.Home, Screen.Apps, Screen.Events, Screen.Tab)

   // val client = remember { KtorClient() }

   // var countryLayer by remember { mutableStateOf<List<LayerItem>>(emptyList()) }

   // var searchText by remember { mutableStateOf("") }

   // val scope = rememberCoroutineScope()

   // var isRefreshing by remember { mutableStateOf(false) }

    val state = rememberPullToRefreshState()

    val viewModel : LayerViewModel = viewModel {
        LayerViewModel(client = KtorClient())
    }

    val uiState by viewModel.state.collectAsState()



//    LaunchedEffect(Unit){
//        try {
//            val response = client.getLayer()
//            countryLayer = response
//            println(countryLayer)
//
//
//        } catch (e: Exception) {
//            println("Error: ${e.message}")
//        }
//    }

//    LaunchedEffect(searchText){
//        if (searchText.length > 4){
//            delay(500)
//            countryLayer = client.getLayerBySearch(searchText)
//        }
//    }

//LaunchedEffect(Unit){
//    viewModel.getAllLayers()
//}





    MaterialTheme {

        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationRoute!!) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        ) {

            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
               // modifier = Modifier.padding(paddingValues),
                enterTransition = { fadeIn(animationSpec = tween(300)) + slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start) },
                exitTransition = { fadeOut(animationSpec = tween(300)) + slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End) }
            ) {
                composable(Screen.Home.route) {
                    Scaffold( topBar = {
                        Column(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            TopAppBar(
                                title = {
                                    Text("Flights", style = MaterialTheme.typography.titleLarge, fontSize = 32.sp)
                                }

                            )
                            OutlinedTextField(value = uiState.searchText, onValueChange = {viewModel.onSearchTextChange(it)}, label = {Text("Search")},
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                shape = RoundedCornerShape(32.dp),
                                trailingIcon = {
                                    IconButton(onClick = {
//                                        scope.launch {
//                                            try {
//                                                val searchResponse = client.getLayerBySearch(searchText)
//                                                countryLayer = searchResponse
//                                            }
//                                            catch (e: Exception){
//                                                println("Error: ${e.message}")
//                                            }
//                                        }

                                        viewModel.getLayerBySearch(uiState.searchText)

                                    }){Icon(
                                        Icons.Default.DonutLarge,contentDescription = null)
                                    }
                                }
                            )
                        }
                    }) {paddingValues ->
                        Column(
                            modifier = Modifier
                                .padding(paddingValues),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //Text("${Screen.Home.title}")
                            if (uiState.isLoading) {
                                // Creative Loading State
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        CircularProgressIndicator(strokeWidth = 3.dp)
                                        Spacer(Modifier.height(16.dp))
                                        Text("Searching for flights...", style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            } else {
                                PullToRefreshBox(
                                    isRefreshing = uiState.isRefreshing,
                                    onRefresh = {
                                       viewModel.refreshBox()
                                    },
                                    state = state
                                ) {
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = PaddingValues(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        item {
                                            Text(
                                                text = "Featured Destinations",
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(bottom = 8.dp)
                                            )
                                        }

                                        items(uiState.countryLayer) { item ->
                                            CountryCard(item, onClick = {navController.navigate(Detail(item.name))})
                                        }
                                    }
                                }
                            }
                        }
                    }



                    }
                    composable(Screen.Apps.route) {


                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${Screen.Apps.title}")
                        }
                    }
                    composable(Screen.Events.route) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${Screen.Events.title}")
                        }
                    }
                    composable(Screen.Tab.route) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${Screen.Tab.title}")
                        }
                    }

                composable<Detail> {
                    backStackEntry ->
                    val details = backStackEntry.toRoute<Detail>()

                    val selectedCountry = uiState.countryLayer.find { it.name == details.name }
                    DetailScreen(item = selectedCountry, onPress = {navController.popBackStack()})



                }
                }

            }

        }
    }


@Composable
fun CountryCard(item: LayerItem,onClick : () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            },
            supportingContent = {
                Text(
                    text = item.region,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            leadingContent = {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Public,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
            trailingContent = {
                // A decorative tag
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                ) {
                    Text(
                        text = "Explore",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        )
    }
}


@Composable
fun DetailScreen(onPress : () -> Unit,item : LayerItem?) {


    Scaffold(topBar = {
        TopAppBar(
            title = { Text(item?.name?: "Not Found") },
            navigationIcon = {
                IconButton(onClick = onPress) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    }) { innerPadding ->

        if (item == null){
            Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center){
                Text("Country Not Found")
            }
        }
        else

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            DetailsCard(item)
        }

    }
}

@Composable
fun DetailsCard(item: LayerItem) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            DetailRow(label = "Capital", value = item.capital)
            DetailRow(label = "Region", value = item.region)
            DetailRow(label = "Codes", value = "${item.alpha2Code} / ${item.alpha3Code}")
            DetailRow(label = "Calling Codes", value = item.callingCodes.joinToString(", "))

            if (item.altSpellings.isNotEmpty()) {
                DetailRow(label = "Alt Spellings", value = item.altSpellings.joinToString(", "))
            }
        }
    }
}
@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}





