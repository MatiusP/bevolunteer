package org.dzedzich.volunteers.profile.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by aleksejskrobot on 14.05.17.
 */
class Activity {

    constructor(id: String, label: String) {
        this.id = id
        this.label = label
    }

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("label")
    @Expose
    var label: String? = null
    @SerializedName("checked")
    @Expose
    var checked: Boolean = true

    override fun toString(): String {
        return "Activity(id=$id, label=$label, checked=$checked)"
    }


}