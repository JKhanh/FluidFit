package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jkhanh.fluidfit.Activity

@Serializable
data class ActivityModel(
    @SerialName("ActivityName")
    val activityName: String,
    @SerialName("Description")
    val description: String
){
    var id: String = ""
}

fun ActivityModel.toActivityDto() =
    Activity(id, activityName, description)
