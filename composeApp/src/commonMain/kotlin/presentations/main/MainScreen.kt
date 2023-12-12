package presentations.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jkhanh.fluidfit.Activity
import presentations.activity.AddActivityScreen

class MainScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<MainViewModel>()
        val uiState = viewModel.uiState.collectAsState().value
        val refreshing = uiState.isLoading
        val pullRefreshState =
            rememberPullRefreshState(refreshing, {  })
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Hello")
                Spacer(Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(uiState.activities, key = { activity -> activity.ActivityID}) {
                        ActivityItem(it)
                    }
                }
                FloatingActionButton(
                    onClick = { navigator?.push(AddActivityScreen) },
                    modifier = Modifier.weight(1f, false).align(Alignment.End)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
            PullRefreshIndicator(
                refreshing,
                pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

    @Composable
    fun ActivityItem(activity: Activity) {
        Card(modifier = Modifier.fillMaxWidth(), elevation = 16.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(activity.ActivityName)
                    if (activity.Description != null) {
                        Text(activity.Description)
                    }
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.ArrowRight, contentDescription = null)
                }
            }
        }
    }
}