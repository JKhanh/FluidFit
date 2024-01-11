package presentations.reminder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.jkhanh.fluidfit.Activity

class AddReminderScreen(
    private val activity: Activity
): Screen {

    @Composable
    override fun Content() {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize()
            ) {
                var repeatInterval by remember {
                    mutableStateOf("")
                }
                var startHour by remember {
                    mutableStateOf("")
                }
                var endHour by remember {
                    mutableStateOf("")
                }
                val isInputValid = repeatInterval.isNotBlank() && startHour.isNotBlank() && endHour.isNotBlank()
                var isRepeat by remember { mutableStateOf(false) }
                Text(
                    "Add Reminder for ${activity.ActivityName}",
                    style = MaterialTheme.typography.displayMedium
                )
                // Start hour input
                OutlinedTextField(
                    value = startHour,
                    onValueChange = { startHour = it },
                    label = { Text("Start hour") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // End hour input
                OutlinedTextField(
                    value = endHour,
                    onValueChange = { endHour = it },
                    label = { Text("End hour") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Repeat",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.clickable { isRepeat = !isRepeat }
                    )
                    Switch(
                        checked = isRepeat,
                        onCheckedChange = { isRepeat = it }
                    )
                }

                AnimatedVisibility(visible = isRepeat) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Repeat interval (hour) input
                        OutlinedTextField(
                            value = repeatInterval,
                            onValueChange = { repeatInterval = it },
                            label = { Text("Repeat interval (hour)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        // Day of the week toggle buttons
                        DayOfWeekToggleButtonGroup()
                    }
                }

                // Add and Cancel buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { /* Handle cancel */ }) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { /* Handle add */ },
                        enabled = isInputValid
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }

    @Composable
    fun DayOfWeekToggleButtonGroup() {
        val daysOfWeek = listOf("M", "T", "W", "TH", "F", "SA", "SU")
        // State for toggle buttons
        val selectedDays = remember { mutableStateOf(mutableSetOf<String>()) }
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
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
        val backgroundColor = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
        OutlinedButton(
            onClick = { onSelectedChange(!selected) },
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = backgroundColor),
            modifier = Modifier.padding(2.dp)
        ) {
            Text(text, color = Color.White, fontSize = 16.sp)
        }
    }
}