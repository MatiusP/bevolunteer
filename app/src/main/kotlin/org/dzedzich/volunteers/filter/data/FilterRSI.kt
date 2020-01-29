package org.dzedzich.volunteers.filter.data

import org.dzedzich.volunteers.profile.data.models.Activity
import org.dzedzich.volunteers.profile.data.models.Company
import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.tasks.data.models.Filter
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by aleksejskrobot on 03.06.17.
 */
interface FilterRSI {

    @GET("/api/v1/activities")
    fun activities(): Observable<List<Activity>>

    @GET("/api/v1/companies")
    fun companies(): Observable<List<Company>>

    @GET("/api/v1/regions")
    fun regions(): Observable<List<Region>>

    @GET("/api/v1/profile/filter")
    fun filter(): Observable<Filter>

    @GET("/api/v1/tasks/filter")
    fun filter(@Query("region") region: String?, @Query("companies") companies: String?, @Query("activities") activities: String?): Observable<Filter>

    @GET("/api/v1/tasks/filter/abort")
    fun abortFilter(): Observable<Filter>

}