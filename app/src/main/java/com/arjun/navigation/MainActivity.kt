package com.arjun.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.unit.dp
import com.arjun.navigation.ui.theme.NavigationTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SubScreen1Destination
import com.ramcosta.composedestinations.generated.destinations.SubScreen2Destination
import com.ramcosta.composedestinations.generated.destinations.SubScreen3Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@NavGraph<RootGraph>
annotation class DetailsGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Destination<RootGraph>(start = true)
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

        Button(onClick = { navigator.navigate(NavGraphs.details) }) {
            Text(text = "Go to Details Graph")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navigator.navigate(SettingsScreenDestination) }) {
            Text(text = "Go to Settings")
        }
    }
}

@Destination<DetailsGraph>(start = true)
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
                navigator.popBackStack(route = HomeScreenDestination, inclusive = false)
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

@Destination<DetailsGraph>
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

@Destination<DetailsGraph>
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

@Destination<DetailsGraph>
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

@Destination<RootGraph>
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