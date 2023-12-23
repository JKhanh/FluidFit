package presentations.main

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.orderBy
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import model.ActivityModel
import model.toActivityDto
import org.jkhanh.fluidfit.Activity
import org.jkhanh.fluidfit.DriverFactory
import org.jkhanh.fluidfit.createDatabase

class ActivityRepositoryImpl(
    driverFactory: DriverFactory
) : ActivityRepository {
    private val activityLocalDB = createDatabase(driverFactory)
    private val firestore = Firebase.firestore

    override suspend fun insertActivity(activity: Activity) {
        activityLocalDB.transaction {
            val existedActivity =
                activityLocalDB.activityQueries.selectActivityById(activity.ActivityID)
                    .executeAsOneOrNull()
            if (existedActivity == null) {
                activityLocalDB.activityQueries.insertActivity(
                    activity.ActivityID,
                    activity.ActivityName,
                    activity.Description
                )
            } else if (isNotSameActivity(existedActivity, activity)) {
                activityLocalDB.activityQueries.updateActivity(
                    activity.ActivityName,
                    activity.Description,
                    activity.ActivityID
                )
            }
        }
    }

    override suspend fun insertUserActivity(activity: Activity) {
        firestore.collection("userActivities")
            .add(activity)
    }

    private fun isNotSameActivity(
        existedActivity: Activity,
        activity: Activity
    ) =
        existedActivity.ActivityName != activity.ActivityName || existedActivity.Description != activity.Description

    override suspend fun getActivityList(): Flow<List<Activity>> {
        val activityList: List<ActivityModel> = firestore.collection("activities")
            .orderBy("ActivityName")
            .get()
            .documents.map { it.data<ActivityModel>().apply {
                this.id = it.id
            } }

        Napier.d(activityList.toString())

        for (activity in activityList) {
            insertActivity(activity.toActivityDto())
        }

        return activityLocalDB.activityQueries.selectAllActivities().asFlow()
            .mapToList(Dispatchers.IO)
    }
}