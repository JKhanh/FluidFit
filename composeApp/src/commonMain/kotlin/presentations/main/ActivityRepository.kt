package presentations.main

import kotlinx.coroutines.flow.Flow
import org.jkhanh.fluidfit.Activity

interface ActivityRepository {
    suspend fun insertActivity(activity: Activity)
    suspend fun insertUserActivity(activity: Activity)
    suspend fun getActivityList(): Flow<List<Activity>>
}