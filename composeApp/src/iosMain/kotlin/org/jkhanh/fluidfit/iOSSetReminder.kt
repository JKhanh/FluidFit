package org.jkhanh.fluidfit

import platform.Foundation.NSTimer
import platform.Foundation.NSRunLoop
import platform.Foundation.NSRunLoopCommonModes
import platform.Foundation.NSDate

actual class iOSSetReminder {
    private var timers: HashMap<String, NSTimer> = HashMap()

    actual fun setReminder(activity: Activity, reminders: Reminders) {
        start(reminders) {
            val notifier = NotifierManager.getLocalNotifier()
            notifier.notify(activity.ActivityName, activity.Description)
        }
    }

    private fun startAt(reminder: Reminders, action: () -> Unit) {
        val date = NSDate.dateWithTimeIntervalSince1970(reminder.ReminderTime.toDouble() / 1000.0)
        val timeInterval = date.timeIntervalSinceNow

        // Convert repeatInterval from milliseconds to seconds
        val repeatIntervalSeconds = reminder.RepeatInterval.toDouble() / 1000.0

        if (timeInterval <= 0) {
            println("Scheduled timestamp is in the past. Timer not scheduled.")
            return
        }
        // set timer to wait for time inverval to fire action()
        val timer = NSTimer.scheduledTimerWithTimeInterval(timeInterval, false) {
            action()

            // If repeating, adjust the timer to start repeating after the initial delay
            if (repeatInterval > 0) {
                timer?.invalidate()
                timer = NSTimer.scheduledTimerWithTimeInterval(repeatIntervalSeconds, true) {
                    action()
                }
                timer?.let {
                    NSRunLoop.currentRunLoop().addTimer(it, NSRunLoopCommonModes)
                }
            }
        }
        timer?.let {
            NSRunLoop.currentRunLoop().addTimer(it, NSRunLoopCommonModes)
        }
        timers[reminder.ReminderID] = timer
    }

    fun stopTimer(reminderId: String) {
        timers[reminderId]?.invalidate()
        timers.remove(reminderId)
    }
}