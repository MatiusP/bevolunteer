package org.dzedzich.volunteers.tasks.data

import org.dzedzich.volunteers.tasks.data.models.*
import retrofit2.http.*
import rx.Observable

/**
 * Created by alexscrobot on 26.05.17.
 */
interface TaskRSI {

    @GET("/api/v1/tasks")
    fun getTasks(): Observable<List<Task>>

    @GET("/api/v1/tasks/filter")
    fun getFilter(@Query("region") region: String?, @Query("companies") companyId: Int?, @Query("activities") activities: String?): Observable<Filter>

    @GET("/api/v1/tasks/my")
    fun getMyTasks(): Observable<List<Task>>

    @GET("/api/v1/tasks/my/completed")
    fun getMyCompletedTasks(): Observable<List<Task>>

    @GET("/api/v1/task/{id}")
    fun task(@Path("id") id: Int): Observable<Task>

    @FormUrlEncoded
    @POST("/api/v1/task/subscribe")
    fun subscribe(@Field("task_id") id: Int?): Observable<Response>

    @GET("/api/v1/task/comments/{id}")
    fun comments(@Path("id") id: Int): Observable<List<Comment>>

    @FormUrlEncoded
    @POST("/api/v1/task/comments/{id}")
    fun comments(@Path("id") id: Int, @Field("message") message: String): Observable<Comment>

    @FormUrlEncoded
    @POST("/api/v1/vote")
    fun vote(@Field("task") taskId: Int?, @Field("aim") toVolunteerId: Int, @Field("value") stars: Int): Observable<VoteResponse>

    @FormUrlEncoded
    @POST("/api/v1/vote/company")
    fun voteCompanyAtmosphere(@Field("task") taskId: Int, @Field("company") companyId: Int, @Field("atmosphere") atmosphereStars: Int): Observable<CompanyVoteResponse>

    @FormUrlEncoded
    @POST("/api/v1/vote/company")
    fun voteCompanyConformity(@Field("task") taskId: Int, @Field("company") companyId: Int, @Field("conformity") conformityStars: Int): Observable<CompanyVoteResponse>

    @FormUrlEncoded
    @POST("/api/v1/vote/company")
    fun voteCompanyOrganizationLevel(@Field("task") taskId: Int, @Field("company") companyId: Int, @Field("organization_level") organizationLevelStars: Int): Observable<CompanyVoteResponse>
}