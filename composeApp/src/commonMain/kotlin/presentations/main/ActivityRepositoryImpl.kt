package presentations.main

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.jkhanh.fluidfit.Activity
import org.jkhanh.fluidfit.DriverFactory
import org.jkhanh.fluidfit.createDatabase

class ActivityRepositoryImpl(
    driverFactory: DriverFactory
): ActivityRepository {
    private val activityLocalDB =  createDatabase(driverFactory)

    override suspend fun insertActivity(activityName: String, activityDescription: String) {
        activityLocalDB.databaseQueries.insertActivity(activityName, activityDescription)
    }

    override suspend fun getActivityList(): Flow<List<Activity>> {
        return activityLocalDB.databaseQueries.selectAllActivities().asFlow().mapToList(Dispatchers.IO)
    }
}