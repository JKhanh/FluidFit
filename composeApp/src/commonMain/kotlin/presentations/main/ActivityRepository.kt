package presentations.main

import kotlinx.coroutines.flow.Flow
import org.jkhanh.fluidfit.Activity

interface ActivityRepository {
    suspend fun insertActivity(activityName: String, activityDescription: String)
    suspend fun getActivityList(): Flow<List<Activity>>
}