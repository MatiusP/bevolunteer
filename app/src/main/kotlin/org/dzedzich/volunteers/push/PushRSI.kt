package org.dzedzich.volunteers.push

import org.dzedzich.volunteers.auth.data.models.User
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

/**
 * Created by aleksejskrobot on 03.06.17.
 */
interface PushRSI {

    @POST("/api/v1/profile/update-token/{token}")
    fun refreshToken(@Path("token") token: String?): Observable<User>

}