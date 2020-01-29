package org.dzedzich.volunteers.tasks.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.data.models.Company


/**
 * Created by aleksejskrobot on 24.05.17.
 */

class Task {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("preview")
    @Expose
    var preview: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("started_at")
    @Expose
    var startedAt: String? = null
    @SerializedName("duration")
    @Expose
    var duration: Int = 0
    @SerializedName("participants_count")
    @Expose
    var participantsCount: Int = 0
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("publish")
    @Expose
    var publish: Int = 0
    @SerializedName("voice_value")
    @Expose
    var voiceValue: Float = 0.toFloat()
    @SerializedName("comments_count")
    @Expose
    var comments: Int = 0
    @SerializedName("members_cnt")
    @Expose
    var membersCount: Int = 0
    @SerializedName("timestamp")
    @Expose
    var timestamp: Int = 0
    @SerializedName("formated_date")
    @Expose
    var formatedDate: String? = null
    @SerializedName("ended_at")
    @Expose
    var endedAt: String? = null
    @SerializedName("is_completed")
    @Expose
    var isCompleted: Boolean = false
    @SerializedName("task_owner")
    @Expose
    var company: String? = null
    @SerializedName("company")
    @Expose
    var companyEntity: Company? = null
    @SerializedName("is_my_task")
    @Expose
    var isMy: Boolean = false
    @SerializedName("volunteers")
    @Expose
    var volunteers: List<User>? = null
    @SerializedName("is_under_filter_condition")
    @Expose
    var filtered: Boolean = false
    @SerializedName("company_voting")
    @Expose
    var companyVoting: CompanyVoteResponse? = null

}
