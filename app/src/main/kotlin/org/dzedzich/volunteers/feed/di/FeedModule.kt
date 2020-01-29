package org.dzedzich.volunteers.feed.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.feed.bussiness.FeedInteractor
import org.dzedzich.volunteers.feed.data.FeedRepository
import org.dzedzich.volunteers.feed.ui.FeedAdapter
import org.dzedzich.volunteers.feed.ui.FeedPresenter
import org.dzedzich.volunteers.feed.ui.IFeedView

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@Module
class FeedModule(val view: IFeedView) {

    @Provides
    @FeedScope
    fun view(): IFeedView {
        return view
    }

    @Provides
    @FeedScope
    fun presenter(interactor: FeedInteractor): FeedPresenter {
        return FeedPresenter(interactor)
    }

    @FeedScope
    @Provides
    fun repo(): FeedRepository {
        return FeedRepository()
    }

    @FeedScope
    @Provides
    fun interactor(repository: FeedRepository): FeedInteractor {
        return FeedInteractor(repository)
    }

    @FeedScope
    @Provides
    fun adapter(): FeedAdapter {
        return FeedAdapter()
    }

}