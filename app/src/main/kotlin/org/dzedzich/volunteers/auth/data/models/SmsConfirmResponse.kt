package org.dzedzich.volunteers.auth.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class SmsConfirmResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

}