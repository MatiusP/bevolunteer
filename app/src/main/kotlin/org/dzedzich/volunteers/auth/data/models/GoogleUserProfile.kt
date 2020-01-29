package org.dzedzich.volunteers.auth.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GoogleUserProfile (
        @SerializedName("id")
        @Expose
        var id: String = "",

        @SerializedName("given_name")
        @Expose
        var givenName: String = "",

        @SerializedName("family_name")
        @Expose
        var familyName: String = ""
)