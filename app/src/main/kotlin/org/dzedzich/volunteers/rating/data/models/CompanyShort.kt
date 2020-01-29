package org.dzedzich.volunteers.rating.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by alexscrobot on 15.05.17.
 */
class CompanyShort {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("rating")
    @Expose
    var rating: Float = 0.0F
    @SerializedName("logo")
    @Expose
    var logo: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("task_count")
    @Expose
    var taskCount: Int = 0
    @SerializedName("atmosphere")
    @Expose
    var atmosphere: Float = 0.0F
    @SerializedName("conformity")
    @Expose
    var conformity: Float = 0.0F
    @SerializedName("organization_level")
    @Expose
    var organizationLevel: Float = 0.0F


}