package org.dzedzich.volunteers.tasks.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.dzedzich.volunteers.auth.data.models.User


/**
 * Created by alexscrobot on 26.05.17.
 */
class Comment {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("volunteer")
    @Expose
    var author: User? = null
    @SerializedName("volunteer_id")
    @Expose
    var volunteerId: Int = 0
    @SerializedName("task_id")
    @Expose
    var taskId: Int = 0
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("formated_date")
    @Expose
    var formatedDate: String? = null
    @SerializedName("published")
    @Expose
    var published: Int = 0
    @SerializedName("deleted_at")
    @Expose
    var deletedAt: Any? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

}