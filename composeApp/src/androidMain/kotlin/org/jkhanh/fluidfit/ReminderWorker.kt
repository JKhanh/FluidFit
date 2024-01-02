package org.jkhanh.fluidfit

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mmk.kmpnotifier.notification.NotifierManager

class ReminderWorker(appContext: Context, private val workerParams: WorkerParameters): Worker(appContext, workerParams) {
    override fun doWork(): Result {

        val data = workerParams.inputData
        val activityName = data.getString("name") ?: "activity"
        val activityReminder = data.getString("reminder") ?: "reminder"

        val notifier = NotifierManager.getLocalNotifier()
        notifier.notify(activityName, activityReminder)

        return Result.success()
    }
}