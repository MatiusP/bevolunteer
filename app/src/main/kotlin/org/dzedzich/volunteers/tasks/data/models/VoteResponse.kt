package org.dzedzich.volunteers.tasks.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by alexscrobot on 26.05.17.
 */
class VoteResponse {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("owner_id")
    @Expose
    var ownerId: Int = 0
    @SerializedName("aim_id")
    @Expose
    var aimId: Int = 0
    @SerializedName("task_id")
    @Expose
    var taskId: Int = 0
    @SerializedName("value")
    @Expose
    var value: Float = 0.00F
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("batch")
    @Expose
    var batch: Int = 0
    @SerializedName("vote_value")
    @Expose
    var voteValue: Int? = null


}