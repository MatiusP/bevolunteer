package org.dzedzich.volunteers.push

import com.github.vkguests.utils.rx.RxSchedulers
import com.haipclick.app.root.structure.ServiceRepository
import org.dzedzich.volunteers.auth.data.models.User
import rx.Observable

/**
 * Created by aleksejskrobot on 03.06.17.
 */
class PushRepository: ServiceRepository<PushRSI>(PushRSI::class.java) {

    fun refreshToken(token: String?): Observable<User> {
        return service
                .refreshToken(token)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

}