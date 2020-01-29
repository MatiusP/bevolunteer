package org.dzedzich.volunteers.tasks.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.data.models.Activity
import org.dzedzich.volunteers.profile.data.models.Company
import org.dzedzich.volunteers.profile.data.models.Region
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.*


/**
 * Created by alexscrobot on 26.05.17.
 */
class Filter {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("region")
    @Expose
    var region: String? = null
    @SerializedName("activities")
    @Expose
    var activities: List<Activity> = ArrayList()
    @SerializedName("volunteer")
    @Expose
    var volunteer: User? = null
    @SerializedName("companies")
    @Expose
    var companies: List<Company>? = null

    @SerializedName("data")
    @Expose
    var data: Filter.Data = Data()

    inner class Data {
        @SerializedName("companies")
        @Expose
        var companies: List<Company> = ArrayList()

        @SerializedName("regions")
        @Expose
        var regions: List<Region> = ArrayList()

        @SerializedName("activities")
        @Expose
        var activities: List<Activity> = ArrayList()

        override fun toString(): String {
            return "Data(companies=$companies, regions=$regions, activities=$activities)"
        }


    }

    fun activitiesAsString(): String? {
        val arr: MutableList<String?> = ArrayList()

        activities.forEachWithIndex { i, item -> arr.add(item.id) }

        return arr.joinToString(",")
    }

    fun regionsToSpinner(): Array<String?> {
        val arr: MutableList<String?> = ArrayList()

        data.regions.forEachWithIndex { i, region -> arr.add(region.label) }

        return arr.toTypedArray()
    }

    fun currentRegion(): Int {
        data.regions.forEachWithIndex { index, region ->
            if(region.id == this.region) {
                return index
            }
        }

        return 0
    }

    override fun toString(): String {
        return "Filter(id=$id, region=$region, activities=$activities, volunteer=$volunteer, companies=$companies, data=$data)"
    }


}