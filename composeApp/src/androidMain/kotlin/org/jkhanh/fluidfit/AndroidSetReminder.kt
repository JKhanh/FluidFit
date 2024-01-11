package org.jkhanh.fluidfit

import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

actual class SetReminder(
    private val workManager: WorkManager
) {
    actual fun setReminder(activity: Activity, reminders: Reminders) {
        val isRepeat = reminders.RepeatInterval != null && reminders.RepeatInterval > 0L
        val inputData = Data.Builder()
            .putString("name", activity.ActivityName)
            .putString("reminder", activity.Description)
            .build()

        if (!isRepeat) {
            val oneTimeWorker = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInputData(inputData)
                .setInitialDelay(getInitialDelay(reminders), TimeUnit.SECONDS)
                .build()

            workManager.enqueue(oneTimeWorker)
        } else {
            val periodicWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(reminders.RepeatInterval!!, TimeUnit.MINUTES)
                .setInputData(inputData)
                .setInitialDelay(getInitialDelay(reminders), TimeUnit.SECONDS)
                .build()
            workManager.enqueueUniquePeriodicWork(activity.ActivityName, ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, periodicWorkRequest)
        }
    }

    private fun getInitialDelay(reminders: Reminders): Long {
        return Calendar.getInstance().timeInMillis + reminders.ReminderTime
    }
}