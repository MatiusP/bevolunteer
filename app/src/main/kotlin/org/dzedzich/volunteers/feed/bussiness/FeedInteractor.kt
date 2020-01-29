package org.dzedzich.volunteers.feed.bussiness

import org.dzedzich.volunteers.feed.data.FeedRepository
import org.dzedzich.volunteers.feed.data.models.Feed
import rx.Observable

/**
 * Created by alexscrobot on 15.05.17.
 */
class FeedInteractor(private val repository: FeedRepository) {

    fun getFeed(): Observable<List<Feed>> {
        return repository.feed()
    }

}