package org.dzedzich.volunteers.profile.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by aleksejskrobot on 21.05.17.
 */
class TaskQuery {


    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("body")
    @Expose
    var body: String? = null
    @SerializedName("volunteer_id")
    @Expose
    var volunteerId: Int = 0
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("id")
    @Expose
    var id: Int = 0

}