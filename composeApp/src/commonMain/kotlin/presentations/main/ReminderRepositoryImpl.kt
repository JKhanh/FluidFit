package presentations.main

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import model.ReminderModel
import org.jkhanh.fluidfit.DriverFactory
import org.jkhanh.fluidfit.Reminders
import org.jkhanh.fluidfit.createDatabase

class ReminderRepositoryImpl(
    driverFactory: DriverFactory
): ReminderRepository {
    private val reminderDB = createDatabase(driverFactory).reminderQueries
    private val firestore = Firebase.firestore

    override suspend fun createReminder(reminder: ReminderModel) {
        val res = firestore.collection("reminder").add(reminder)
        reminder.id = res.id
        addReminderToLocal(reminder)
    }

    private fun addReminderToLocal(reminder: ReminderModel) {
        reminderDB.insertReminder(
            reminder.id,
            reminder.activityId,
            reminder.reminderTime,
            reminder.repeatInterval,
            reminder.workingHoursStart,
            reminder.workingHoursEnd,
            reminder.workingDays
        )
    }

    override suspend fun logActivity(activityLogId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllReminder(): Flow<List<Reminders>> {
        reloadFromRemote()
        return reminderDB.selectAllReminders().asFlow()
            .mapToList(Dispatchers.IO)
    }

    private suspend fun reloadFromRemote() {
        val reminderList: List<ReminderModel> = firestore.collection("reminders")
            .where("userId", Firebase.auth.currentUser!!.uid)
            .get()
            .documents.map { it.data() }

        for (reminderModel in reminderList) {
            addReminderToLocal(reminderModel)
        }
    }
}