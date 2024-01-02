package presentations.main

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jkhanh.fluidfit.Activity

class MainViewModel(
    private val repository: ActivityRepository
): ScreenModel {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            repository.getActivityList().collect { newActivities ->
                Napier.d(newActivities.toString())
                _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        activities = newActivities
                    )
                }
        }
    }

    fun addActivity(activityName: String, activityDescription: String) {
        screenModelScope.launch {
        }
    }
}

data class MainUiState(
    val isLoading: Boolean = false,
    val activities: List<Activity> = emptyList()
)
