package org.dzedzich.volunteers.tasks.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alexscrobot on 26.05.17.
 */
class Response {

    @SerializedName("status")
    @Expose
    var status: String? = null

}