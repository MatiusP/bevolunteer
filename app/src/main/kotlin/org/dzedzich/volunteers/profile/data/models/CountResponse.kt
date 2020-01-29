package org.dzedzich.volunteers.profile.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by aleksejskrobot on 15.05.17.
 */
class CountResponse {

    @SerializedName("count")
    @Expose
    var count: Int = 1234

}