package presentations.main

import kotlinx.coroutines.flow.Flow
import model.ReminderModel
import org.jkhanh.fluidfit.Reminders

interface ReminderRepository {
    suspend fun createReminder(reminder: ReminderModel)
    suspend fun logActivity(activityLogId: String)
    suspend fun getAllReminder(): Flow<List<Reminders>>
}