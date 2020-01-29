package org.dzedzich.volunteers.rating.data.network

import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.rating.data.models.CompanyShort
import org.dzedzich.volunteers.rating.data.models.VolunteerShort
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by aleksejskrobot on 23.05.17.
 */
interface RatingRSI {

    @GET("/api/v1/rating/volunteers/")
    fun volunteers(@Query("offset") offset: Int?, @Query("limit") limit: Int?, @Query("region") region: String): Observable<List<VolunteerShort>>

    @GET("/api/v1/rating/companies/")
    fun companies(@Query("offset") offset: Int?, @Query("region") region: String): Observable<List<CompanyShort>>

    @GET("/api/v1/regions")
    fun regions(): Observable<List<Region>>

}