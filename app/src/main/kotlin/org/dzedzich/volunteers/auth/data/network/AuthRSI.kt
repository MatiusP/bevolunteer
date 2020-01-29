package org.dzedzich.volunteers.auth.data.network

import org.dzedzich.volunteers.auth.data.models.GoogleUserProfile
import org.dzedzich.volunteers.auth.data.models.SmsConfirmResponse
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.data.models.Region
import retrofit2.http.*
import rx.Observable

/**
 * Created by aleksejskrobot on 21.05.17.
 */
interface AuthRSI {

    @FormUrlEncoded
    @POST("/api/v1/auth")
    fun auth(@FieldMap params: Map<String, String?>): Observable<User>

    @FormUrlEncoded
    @POST("/api/v1/sms-confirm")
    fun confirm(@Field("code") code: String): Observable<SmsConfirmResponse>

    @GET("/api/v1/regions")
    fun regions(@Query("without_all") withAll: Boolean): Observable<List<Region>>

    @GET("https://www.googleapis.com/oauth2/v1/userinfo")
    fun getGoogleUserProfile(@Query("alt") alt: String, @Query("access_token") token: String): Observable<GoogleUserProfile>

}