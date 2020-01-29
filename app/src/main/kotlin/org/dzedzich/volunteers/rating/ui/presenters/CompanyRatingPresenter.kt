package org.dzedzich.volunteers.rating.ui.presenters

import org.dzedzich.volunteers.rating.bussiness.RatingInteractor
import org.dzedzich.volunteers.rating.ui.views.ICompanyRatingView
import org.dzedzich.volunteers.root.structure.Presenter
import rx.Observable
import rx.lang.kotlin.onError

/**
 * Created by alexscrobot on 15.05.17.
 */
class CompanyRatingPresenter(private val interactor: RatingInteractor) : Presenter<ICompanyRatingView>() {

    override var view: ICompanyRatingView? = null

    override fun bindView(view: ICompanyRatingView) {
        super.bindView(view)
        subscribe()
    }

    private fun subscribe() {
        subscriptions.add(
                Observable.zip(interactor.companies(0, "all"), interactor.regions()) { companies, regions ->
                    view?.loadData(companies)
                    view?.loadRegions(regions)
                }.onError {
                    view?.showError(it.message)
                }.subscribe()
        )
    }

    fun filter(region: String?) {
        subscriptions.add(interactor.companies(0, region)
                .onError {
                    view?.showError(it.message)
                }.subscribe(
                        { view?.loadData(it) }
                )
        )
    }

}