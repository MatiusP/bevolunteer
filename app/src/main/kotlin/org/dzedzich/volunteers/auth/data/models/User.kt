package org.dzedzich.volunteers.auth.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.data.models.VoteResponse
import java.util.*


/**
 * Created by aleksejskrobot on 14.05.17.
 */
class User {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Float = 300.0F
    @SerializedName("rating_year")
    @Expose
    var ratingYear: Float = 300.0F
    @SerializedName("completed_tasks_count")
    @Expose
    var completedTasksCount: Int = 0
    @SerializedName("site")
    @Expose
    var site: String? = null
    @SerializedName("slogan")
    @Expose
    var slogan: String? = null
    @SerializedName("about")
    @Expose
    var about: String? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("google_id")
    @Expose
    var googleId: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("region")
    @Expose
    var region: String? = null
    @SerializedName("confirmed")
    @Expose
    var confirmed: Int? = 0
    @SerializedName("place")
    @Expose
    var place: Int? = 0
    @SerializedName("volunteers_count")
    @Expose
    var volunteersCount: Int? = 0
    @SerializedName("tasks")
    @Expose
    var tasks: List<Task> = ArrayList()
    @SerializedName("viewPhoneNumber")
    @Expose
    var isViewPhoneNumber: Int = 0
    @SerializedName("vote")
    @Expose
    var vote: VoteResponse? = null

    fun site(): String? {
        return if(site == null) "" else if((site?.contains("http://") as Boolean) || (site?.contains("https://") as Boolean)) this.site else "http://$site"
    }

    override fun toString(): String {
        return "User(id=$id, name=$name, phone=$phone, avatar=$avatar, country=$country, rating=$rating, completedTasksCount=$completedTasksCount, site=$site, slogan=$slogan, about=$about, token=$token, createdAt=$createdAt, updatedAt=$updatedAt, region=$region, confirmed=$confirmed, place=$place, volunteersCount=$volunteersCount, showPhoneNumber=$isViewPhoneNumber)"
    }

}