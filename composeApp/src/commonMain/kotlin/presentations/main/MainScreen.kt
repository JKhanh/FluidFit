package presentations.main

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jkhanh.fluidfit.Activity
import presentations.activity.AddActivityScreen

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<MainViewModel>()
        val uiState = viewModel.uiState.collectAsState().value
        val refreshing = uiState.isLoading
//        val pullRefreshState =
//            rememberPullRefreshState(refreshing, { })

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navigator?.push(AddActivityScreen) },
                    modifier = Modifier
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            Box(
                modifier = Modifier
//                    .pullRefresh(pullRefreshState)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Hello")
                    Spacer(Modifier.height(16.dp))
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.activities, key = { activity -> activity.ActivityID }) {
                                ActivityItem(it)
                            }
                        }
                    }

                }
//                PullRefreshIndicator(
//                    refreshing,
//                    pullRefreshState,
//                    modifier = Modifier.align(Alignment.TopCenter)
//                )
            }
        }
    }

        @Composable
        fun ActivityItem(activity: Activity) {
            Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(16.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(activity.ActivityName)
                    Button(onClick = {

                    },colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ), elevation = ButtonDefaults.buttonElevation(0.dp),
                        contentPadding = PaddingValues(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Try out!", color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.ArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }