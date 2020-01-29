
package org.dzedzich.volunteers.rating.data

import android.util.Log
import com.github.vkguests.utils.rx.RxSchedulers
import com.haipclick.app.root.structure.ServiceRepository
import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.rating.data.models.CompanyShort
import org.dzedzich.volunteers.rating.data.models.VolunteerShort
import org.dzedzich.volunteers.rating.data.network.RatingRSI
import org.dzedzich.volunteers.utils.rx.RxDecorator
import rx.Observable
import rx.functions.Func1
import rx.lang.kotlin.onError

/**
 * Created by alexscrobot on 15.05.17.
 */
class RatingRepository : ServiceRepository<RatingRSI>(RatingRSI::class.java) {

    private fun companies(offset: Int, region: String): Observable<List<CompanyShort>> {
        return RxDecorator<List<CompanyShort>>().decorate(service.companies(offset, region))
    }

    private fun volunteers(offset: Int, limit: Int, region: String): Observable<List<VolunteerShort>> {
        return RxDecorator<List<VolunteerShort>>().decorate(service.volunteers(offset, limit, region))
    }

    fun regions(): Observable<List<Region>> {
        return service.
                regions()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun loadCompanies(offset: Int, region: String): Observable<List<CompanyShort>> {
        var counter = offset

        return companies(offset, region)
                .concatMap(Func1<List<CompanyShort>, Observable<List<CompanyShort>>> { response ->
                    // Terminal case.
                    if (response.isEmpty()) {
                        return@Func1 Observable.just(response)
                    }

                    counter += response.size

                    val obs = Observable.just(response)

                    if(response.isNotEmpty()) {
                        obs.concatWith(loadCompanies(counter, region))
                    }

                    obs
                })
    }

    fun loadVolunteers(offset: Int, limit: Int, region: String): Observable<List<VolunteerShort>> {
        var counter = offset
        Log.d("lol", "offset " + offset + " region " + region)
        return volunteers(offset, limit, region)
                .concatMap(Func1<List<VolunteerShort>, Observable<List<VolunteerShort>>> { response ->
                    // Terminal case.
                    if (response.isEmpty()) {
                        return@Func1 Observable.just(response)
                    }

                    counter += response.size

                    val obs = Observable.just(response)

                    if(response.isNotEmpty()) {
                        obs.concatWith(loadVolunteers(counter, limit, region))
                    }

                    obs
                })
    }


}