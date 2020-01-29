package org.dzedzich.volunteers.feed.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by alexscrobot on 15.05.17.
 */
class Feed {


    constructor(title: String?, enclosure: String?, date: String?, permalink: String?, body: String?) {
        this.title = title
        this.enclosure = enclosure
        this.date = date
        this.permalink = permalink
        this.body = body
    }

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: Any? = null
    @SerializedName("enclosure")
    @Expose
    var enclosure: String? = null
    @SerializedName("categories")
    @Expose
    var categories: List<FeedCategory>? = null
    @SerializedName("category")
    @Expose
    var category: FeedCategory? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("permalink")
    @Expose
    var permalink: String? = null
    @SerializedName("body")
    @Expose
    var body: String? = null

}