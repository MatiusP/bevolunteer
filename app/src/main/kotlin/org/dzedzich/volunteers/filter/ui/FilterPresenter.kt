package org.dzedzich.volunteers.filter.ui

import android.util.Log
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.filter.business.FilterInteractor
import org.dzedzich.volunteers.profile.data.models.Activity
import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.root.text
import org.dzedzich.volunteers.tasks.data.models.Filter

/**
 * Created by aleksejskrobot on 03.06.17.
 */
class FilterPresenter(override var view: IFilterView?, private val interactor: FilterInteractor) : Presenter<IFilterView>() {

    var filter: Filter? = null

    override fun bindView(view: IFilterView) {
        super.bindView(view)

        subscriptions.add(interactor.loadFilter().subscribe({ filter -> renderDataToView(filter)}, Throwable::printStackTrace))
    }

    private fun renderDataToView(filter: Filter) {
        this.filter = filter

        Log.d("filter", filter.toString())

        if(filter.id == 0) {
            filter.data.activities.forEach { it.checked = true }
        }

        view?.loadRegions(filter.regionsToSpinner())
        view?.loadCompanies(filter.data.companies)
        view?.loadActivities(filter.data.activities)

        view?.checkCurrentRegion(filter.currentRegion())
        view?.checkCurrentCompanies(filter.companies)
        view?.checkCurrentActivities(filter.activities)
    }

    fun saveFilter(checked: List<Activity>, regionPosititon: Int, companies: IntArray) {
        interactor.saveFilter(filter, checked, regionPosititon, companies).subscribe({
            text(R.string.filter_save_successfull)
            view?.close()
        }, {
            Log.e("filter", "", it)
        })
    }

    fun abortFilter() {
        subscriptions.add(interactor.abortFilter().subscribe({ filter -> renderDataToView(filter)}, Throwable::printStackTrace))
    }
}