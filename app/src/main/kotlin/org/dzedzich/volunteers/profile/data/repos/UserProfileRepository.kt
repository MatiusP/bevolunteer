package org.dzedzich.volunteers.profile.data.repos

import com.github.vkguests.utils.rx.RxSchedulers
import com.haipclick.app.root.structure.ServiceRepository
import okhttp3.MultipartBody
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.data.models.AvatarUploadResponse
import org.dzedzich.volunteers.profile.data.models.CountResponse
import org.dzedzich.volunteers.profile.data.models.TaskQuery
import org.dzedzich.volunteers.profile.data.network.UserProfileRSI
import org.dzedzich.volunteers.utils.rx.RxDecorator
import rx.Observable

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class UserProfileRepository : ServiceRepository<UserProfileRSI>(UserProfileRSI::class.java) {

    fun user(): Observable<User> {
        return service.profile()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
                .onErrorReturn { this.preferences.user() }
                .flatMap {
                    this.preferences.saveUser(it)
                    Observable.just(it)
                }
    }

    fun saveUser(map: Map<String, String?>): Observable<User> {
        return service.update(map)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
                .onErrorReturn { this.preferences.user() }
                .flatMap {
                    this.preferences.saveUser(it)
                    Observable.just(it)
                }
    }

    fun profile(id: Int?): Observable<User> {
        return service.profile(id?.toLong())
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun volunteersCount(): Observable<CountResponse> {
        return Observable.just(CountResponse())
    }

    fun sendImage(body: MultipartBody.Part?): Observable<AvatarUploadResponse> {
        return RxDecorator<AvatarUploadResponse>().decorate(service.uploadAvatar(body).flatMap {
            if(it.success) {
                preferences.saveUsersAvatar(it.avatar)
            }

            Observable.just(it)
        })
    }

    fun makeTask(task: String): Observable<TaskQuery> {
        return service.makeTask(task)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }
}