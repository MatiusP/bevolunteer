package org.dzedzich.volunteers.profile.data.network

import okhttp3.MultipartBody
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.data.models.AvatarUploadResponse
import org.dzedzich.volunteers.profile.data.models.TaskQuery
import retrofit2.http.*
import rx.Observable

/**
 * Created by aleksejskrobot on 21.05.17.
 */
interface UserProfileRSI {

    @GET("/api/v1/profile/show")
    fun profile(): Observable<User>

    @GET("/api/v1/profile/view/{id}")
    fun profile(@Path("id") id: Long?): Observable<User>

    @FormUrlEncoded
    @POST("/api/v1/profile/update")
    fun update(@FieldMap map: Map<String, String?>): Observable<User>

    @FormUrlEncoded
    @POST("/api/v1/profile/task-query")
    fun makeTask(@Field("message") message: String): Observable<TaskQuery>

    @Multipart
    @POST("/api/v1/profile/avatar")
    fun uploadAvatar(@Part file: MultipartBody.Part?): Observable<AvatarUploadResponse>
}