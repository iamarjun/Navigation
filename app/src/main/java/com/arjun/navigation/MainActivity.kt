package com.arjun.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.arjun.navigation.destinations.HomeScreenDestination
import com.arjun.navigation.destinations.SettingsScreenDestination
import com.arjun.navigation.destinations.SubScreen1Destination
import com.arjun.navigation.destinations.SubScreen2Destination
import com.arjun.navigation.destinations.SubScreen3Destination
import com.arjun.navigation.ui.theme.NavigationTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.rememberNavHostEngine

// Define NavGraphs
@RootNavGraph(start = true)
@NavGraph
annotation class RootGraph(
    val start: Boolean = false
)

@RootGraph
@NavGraph
annotation class DetailsGraph(
    val start: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val engine = rememberNavHostEngine(
                        navHostContentAlignment = Alignment.TopCenter,
                        rootDefaultAnimations = RootNavGraphDefaultAnimations(
                            enterTransition = {
                                slideIn(
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = FastOutLinearInEasing
                                    ),
                                    initialOffset = { IntOffset((it.width), 0) }
                                )
                            },
                            exitTransition = {
                                slideOut(
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = FastOutLinearInEasing
                                    ),
                                    targetOffset = { IntOffset(-(0.4 * it.width).toInt(), 0) }
                                )
                            },
                            popEnterTransition = {
                                slideIn(
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = FastOutLinearInEasing
                                    ),
                                    initialOffset = { IntOffset(-(0.4 * it.width).toInt(), 0) }
                                )
                            },
                            popExitTransition = {
                                slideOut(
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = FastOutLinearInEasing
                                    ),
                                    targetOffset = { IntOffset(it.width, 0) }
                                )
                            }
                        ),
                        defaultAnimationsForNestedNavGraph = mapOf(
                            NavGraphs.root to NestedNavGraphDefaultAnimations(
                                enterTransition = {
                                    slideIn(
                                        animationSpec = tween(
                                            durationMillis = 200,
                                            easing = FastOutLinearInEasing
                                        ),
                                        initialOffset = { IntOffset((it.width), 0) }
                                    )
                                },
                                exitTransition = {
                                    slideOut(
                                        animationSpec = tween(
                                            durationMillis = 200,
                                            easing = FastOutLinearInEasing
                                        ),
                                        targetOffset = { IntOffset(-(0.4 * it.width).toInt(), 0) }
                                    )
                                },
                                popEnterTransition = {
                                    slideIn(
                                        animationSpec = tween(
                                            durationMillis = 200,
                                            easing = FastOutLinearInEasing
                                        ),
                                        initialOffset = { IntOffset(-(0.4 * it.width).toInt(), 0) }
                                    )
                                },
                                popExitTransition = {
                                    slideOut(
                                        animationSpec = tween(
                                            durationMillis = 200,
                                            easing = FastOutLinearInEasing
                                        ),
                                        targetOffset = { IntOffset(it.width, 0) }
                                    )
                                }
                            ),
                        )
                    )
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(innerPadding),
                        engine = engine
                    )
                }
            }
        }
    }
}

@RootGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navigator.navigate(NavGraphs.detailsGraph) }) {
            Text(text = "Go to Details Graph")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navigator.navigate(SettingsScreenDestination) }) {
            Text(text = "Go to Settings")
        }
    }
}

@DetailsGraph(start = true)
@Destination
@Composable
fun DetailsScreen(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Details Screen (Main)",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navigator.navigate(HomeScreenDestination) {
                    popUpTo(HomeScreenDestination) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text(text = "Back to Home")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navigator.navigate(SubScreen1Destination) }) {
            Text(text = "Go to Sub Screen 1")
        }
    }
}

@DetailsGraph
@Destination
@Composable
fun SubScreen1(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sub Screen 1",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navigator.navigate(SubScreen2Destination) }) {
            Text("Next Screen (Sub 2)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navigator.popBackStack() }) {
            Text("Back to Details Main")
        }
    }
}

@DetailsGraph
@Destination
@Composable
fun SubScreen2(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sub Screen 2",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { navigator.popBackStack() }) {
                Text("Back (Sub 1)")
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Button(onClick = { navigator.navigate(SubScreen3Destination) }) {
                Text("Next Screen (Sub 3)")
            }
        }
    }
}

@DetailsGraph
@Destination
@Composable
fun SubScreen3(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sub Screen 3",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navigator.popBackStack() }) {
            Text("Back (Sub 2)")
        }
    }
}

@RootGraph
@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Settings Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navigator.popBackStack() }) {
            Text(text = "Go Back")
        }
    }
}