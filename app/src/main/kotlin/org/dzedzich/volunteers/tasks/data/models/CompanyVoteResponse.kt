package org.dzedzich.volunteers.tasks.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by alexscrobot on 26.05.17.
 */
class CompanyVoteResponse {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("volunteer_id")
    @Expose
    var volunteerId: Int = 0
    @SerializedName("company_id")
    @Expose
    var companyId: Int = 0
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("task_id")
    @Expose
    var taskId: Int = 0
    @SerializedName("atmosphere_stars")
    @Expose
    var atmosphereStars: Int = 0
    @SerializedName("conformity_stars")
    @Expose
    var conformityStars: Int = 0
    @SerializedName("organization_level_stars")
    @Expose
    var organizationLevelStars: Int = 0

}