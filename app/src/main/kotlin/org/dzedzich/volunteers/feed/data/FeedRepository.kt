package org.dzedzich.volunteers.feed.data

import com.haipclick.app.root.structure.ServiceRepository
import org.dzedzich.volunteers.feed.data.FeedRepository.FeedRSI
import org.dzedzich.volunteers.feed.data.models.Feed
import org.dzedzich.volunteers.utils.rx.RxDecorator
import retrofit2.http.GET
import rx.Observable

/**
 * Created by alexscrobot on 15.05.17.
 */
class FeedRepository : ServiceRepository<FeedRSI>(FeedRSI::class.java) {

    fun feed(): Observable<List<Feed>> {
        return RxDecorator<List<Feed>>().decorate(service.feed())
    }

    interface FeedRSI {

        @GET("api/v1/feed")
        fun feed(): Observable<List<Feed>>

    }

}