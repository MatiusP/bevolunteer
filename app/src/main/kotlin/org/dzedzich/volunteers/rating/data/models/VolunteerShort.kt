package org.dzedzich.volunteers.rating.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by alexscrobot on 15.05.17.
 */
class VolunteerShort {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("completed_tasks_count")
    @Expose
    var completedTasksCount: Int = 0
    @SerializedName("rating")
    @Expose
    var rating: Float = 0.0F
    @SerializedName("rating_year")
    @Expose
    var ratingYear: Float = 0.0F
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("slogan")
    @Expose
    var slogan: String? = null
}