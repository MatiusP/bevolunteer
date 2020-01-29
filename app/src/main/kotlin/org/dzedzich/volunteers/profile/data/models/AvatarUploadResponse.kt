package org.dzedzich.volunteers.profile.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by aleksejskrobot on 21.05.17.
 */
class AvatarUploadResponse {

    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("success")
    @Expose
    var success: Boolean = false

}