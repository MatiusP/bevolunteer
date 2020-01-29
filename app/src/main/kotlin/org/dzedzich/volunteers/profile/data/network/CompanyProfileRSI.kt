package org.dzedzich.volunteers.profile.data.network

import org.dzedzich.volunteers.profile.data.models.Company
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by alexscrobot on 24.05.17.
 */
interface CompanyProfileRSI {

    @GET("api/v1/company/{id}")
    fun getCompany(@Path("id") id: Int?): Observable<Company>

}