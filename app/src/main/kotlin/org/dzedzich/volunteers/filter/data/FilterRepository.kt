package org.dzedzich.volunteers.filter.data

import com.github.vkguests.utils.rx.RxSchedulers
import com.haipclick.app.root.structure.ServiceRepository
import org.dzedzich.volunteers.profile.data.models.Activity
import org.dzedzich.volunteers.profile.data.models.Company
import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.tasks.data.models.Filter
import rx.Observable

/**
 * Created by aleksejskrobot on 03.06.17.
 */
class FilterRepository: ServiceRepository<FilterRSI>(FilterRSI::class.java) {

    fun activities(): Observable<List<Activity>> {
        return service
                .activities()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun filter(): Observable<Filter> {
        return service.
                filter()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun companies(): Observable<List<Company>> {
        return service.
                companies()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun regions(): Observable<List<Region>> {
        return service.
                regions()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun saveFilter(filter: Filter?, companies: IntArray): Observable<Filter> {
        return service
                .filter(filter?.region, companies.joinToString(","), filter?.activitiesAsString())
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun abortFilter(): Observable<Filter> {
        return service
                .abortFilter()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }


}