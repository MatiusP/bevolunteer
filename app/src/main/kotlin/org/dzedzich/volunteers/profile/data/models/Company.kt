package org.dzedzich.volunteers.profile.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.dzedzich.volunteers.tasks.data.models.Task
import java.util.*


class Company {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("site")
    @Expose
    var site: String? = null
    @SerializedName("about")
    @Expose
    var about: String? = null
    @SerializedName("activities")
    @Expose
    var activities: List<Activity>? = null
    @SerializedName("string_activities")
    @Expose
    var activitiesName: String? = null
    @SerializedName("logo")
    @Expose
    var logo: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Float = 300.0F
    @SerializedName("founder_id")
    @Expose
    var founderId: Int = 0
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("deleted_at")
    @Expose
    var deletedAt: Any? = null
    @SerializedName("region")
    @Expose
    var region: String? = null
    @SerializedName("added_tasks_count")
    @Expose
    var tasksCount: Int = 0
    @SerializedName("place")
    @Expose
    var place: Int = 0
    @SerializedName("companies_count")
    @Expose
    var companiesCount: Int = 0
    @SerializedName("tasks")
    @Expose
    var tasks: List<Task> = ArrayList()
    @SerializedName("owner")
    @Expose
    var owner: Owner? = null

    inner class Owner {
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("email")
        @Expose
        var email: String? = null
        @SerializedName("phone")
        @Expose
        var phone: String? = null
    }

    fun site(): String? {
        return if((site?.contains("http://") as Boolean) || (site?.contains("https://") as Boolean)) this.site else "http://$site"
    }

    override fun toString(): String {
        return "Company(id=$id, name=$name, site=$site, about=$about, activities=$activities, activitiesName=$activitiesName, logo=$logo, rating=$rating, founderId=$founderId, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, region=$region, tasksCount=$tasksCount, place=$place, companiesCount=$companiesCount, tasks=$tasks, owner=$owner)"
    }


}
