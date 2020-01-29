package org.dzedzich.volunteers.feed.ui

import org.dzedzich.volunteers.feed.bussiness.FeedInteractor
import org.dzedzich.volunteers.root.structure.Presenter



/**
 * Created by alexscrobot on 15.05.17.
 */
class FeedPresenter(val interactor: FeedInteractor) : Presenter<IFeedView>() {

    override var view: IFeedView? = null

    override fun bindView(view: IFeedView) {
        super.bindView(view)

        subscriptions.add(interactor.getFeed().subscribe {
            view.renderAdapter(it)
        })
    }

}