
package org.dzedzich.volunteers.root.structure

import rx.subscriptions.CompositeSubscription

/**
 * Created by alexscrobot on 13.05.17.
 */
abstract class Presenter<T> : IPresenter<T>, IPresentable {
    abstract override var view: T?
    override val subscriptions: CompositeSubscription = CompositeSubscription()
}