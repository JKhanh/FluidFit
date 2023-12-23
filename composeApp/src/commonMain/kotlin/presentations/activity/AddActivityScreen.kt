package presentations.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentations.main.MainViewModel

object AddActivityScreen: Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<MainViewModel>()
        var activityName by remember { mutableStateOf("") }
        var activityDescription by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Input field for the activity name
            OutlinedTextField(
                value = activityName,
                onValueChange = { activityName = it },
                label = { Text("Activity Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input field for the activity description
            OutlinedTextField(
                value = activityDescription,
                onValueChange = { activityDescription = it },
                label = { Text("Activity Description") },
                modifier = Modifier.fillMaxWidth()
            )

            // A button to submit the data
            Button(onClick = {
                viewModel.addActivity(activityName, activityDescription)
                navigator.pop()
            }) {
                Text("Add Activity")
            }
        }
    }

    @Composable
    fun AddActivityReminderScreen() {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize()
            ) {
                // Dropdown menu for selecting an activity
                var expanded by remember { mutableStateOf(false) }
                val activities = listOf("Activity 1", "Activity 2", "Activity 3")
                var selectedIndex by remember { mutableStateOf(0) }

                OutlinedTextField(
                    value = activities[selectedIndex],
                    onValueChange = {},
                    label = { Text("Activity") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    activities.forEachIndexed { index, activity ->
                        DropdownMenuItem(
                            onClick = {
                                selectedIndex = index
                                expanded = false
                            }
                        ) {
                            Text(activity)
                        }
                    }
                }

                // Repeat interval (hour) input
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Update your state here */ },
                    label = { Text("Repeat interval (hour)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Start hour input
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Update your state here */ },
                    label = { Text("Start hour") },
                    modifier = Modifier.fillMaxWidth()
                )

                // End hour input
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Update your state here */ },
                    label = { Text("End hour") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Day of the week toggle buttons
                DayOfWeekToggleButtonGroup()

                // Add and Cancel buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { /* Handle cancel */ }) {
                        Text("Cancel")
                    }
                    Button(onClick = { /* Handle add */ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)) {
                        Text("Add")
                    }
                }
            }
        }

    @Composable
    fun DayOfWeekToggleButtonGroup() {
        val daysOfWeek = listOf("M", "T", "W", "TH", "F", "SA", "SU")
        // State for toggle buttons
        val selectedDays = remember { mutableStateOf(mutableSetOf<String>()) }

        Row(
        ) {
            daysOfWeek.forEach { day ->
                ToggleButton(
                    selected = selectedDays.value.contains(day),
                    onSelectedChange = { isSelected ->
                        val newSet = selectedDays.value.toMutableSet()
                        if (isSelected) {
                            newSet.add(day)
                        } else {
                            newSet.remove(day)
                        }
                        selectedDays.value = newSet
                    },
                    text = day
                )
            }
        }
    }

    @Composable
    fun ToggleButton(selected: Boolean, onSelectedChange: (Boolean) -> Unit, text: String) {
        val backgroundColor = if (selected) MaterialTheme.colors.primary else Color.Gray
        OutlinedButton(
            onClick = { onSelectedChange(!selected) },
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = backgroundColor)
        ) {
            Text(text, color = Color.White)
        }
    }

}