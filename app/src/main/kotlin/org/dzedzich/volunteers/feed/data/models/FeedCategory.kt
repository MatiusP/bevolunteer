package org.dzedzich.volunteers.feed.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by alexscrobot on 15.05.17.
 */
class FeedCategory {

    @SerializedName("term")
    @Expose
    var term: String? = null
    @SerializedName("scheme")
    @Expose
    var scheme: Any? = null
    @SerializedName("label")
    @Expose
    var label: Any? = null

}