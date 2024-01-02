package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReminderModel(
    @SerialName("UserId")
    val userId: String,
    @SerialName("ActivityId")
    val activityId: String,
    @SerialName("ReminderTime")
    val reminderTime: Long, // UNIX timestamp
    @SerialName("RepeatInterval")
    val repeatInterval: Long = 0,
    @SerialName("WorkingHoursStart")
    val workingHoursStart: Long, // Format: HHMM
    @SerialName("WorkingHoursEnd")
    val workingHoursEnd: Long, // Format: HHMM
    @SerialName("WorkingDays")
    val workingDays: String // Comma-separated days
) {
    var id: String = ""
}
