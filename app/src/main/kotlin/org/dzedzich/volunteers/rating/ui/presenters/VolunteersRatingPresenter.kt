package org.dzedzich.volunteers.rating.ui.presenters

import org.dzedzich.volunteers.rating.bussiness.RatingInteractor
import org.dzedzich.volunteers.rating.ui.views.IVolunteersRatingView
import org.dzedzich.volunteers.root.structure.Presenter
import rx.Observable
import rx.lang.kotlin.onError

/**
 * Created by alexscrobot on 15.05.17.
 */
class VolunteersRatingPresenter(private val interactor: RatingInteractor) : Presenter<IVolunteersRatingView>() {
    override var view: IVolunteersRatingView? = null
    var offset = 0
    var limit = 50
    var region = "all"
    var isLoading = false

    override fun bindView(view: IVolunteersRatingView) {
        super.bindView(view)
        subscribe()
    }

    private fun subscribe() {
        offset = 0
        region = "all"
        isLoading = true
        subscriptions.add(
                Observable.zip(interactor.volunteers(offset, limit, region), interactor.regions()) { volunteers, regions ->
                    offset += volunteers.size
                    view?.loadData(volunteers)
                    view?.loadRegions(regions)
                    isLoading = false
                }.onError {
                    isLoading = false
                    //view?.showError(it.message)
                }.subscribe()
        )
    }

    fun loadMore() {
        isLoading = true
        subscriptions.add(interactor.volunteers(offset, limit, region)
                .subscribe(
                        {
                            view?.addData(it)
                            offset += it.size
                            isLoading = false
                        },
                        {
                            isLoading = false
                        }
                )
        )
    }

    fun filter(region: String?) {
        subscriptions.add(interactor.volunteers(0, 100, region)
                .onError {
                    //view?.showError(it.message)
                }
                .subscribe(
                        { view?.loadData(it) }
                )
        )
    }
}