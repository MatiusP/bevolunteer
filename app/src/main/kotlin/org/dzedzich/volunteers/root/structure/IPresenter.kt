package org.dzedzich.volunteers.root.structure

import rx.subscriptions.CompositeSubscription

/**
 * Created by aleksejskrobot on 22.03.17.
 */
interface IPresenter<T> {

    var view: T?
    val subscriptions: CompositeSubscription

    fun bindView(view: T) {
        this.view = view
    }

    fun unbindView() {
        this.view = null

        if(subscriptions.hasSubscriptions()) {
            subscriptions.unsubscribe()
        }

        subscriptions.clear()
    }
}