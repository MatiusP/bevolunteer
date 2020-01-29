package org.dzedzich.volunteers.rating.bussiness

import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.rating.data.RatingRepository
import org.dzedzich.volunteers.rating.data.models.CompanyShort
import org.dzedzich.volunteers.rating.data.models.VolunteerShort
import rx.Observable

/**
 * Created by alexscrobot on 15.05.17.
 */
class RatingInteractor(private val repository: RatingRepository) {

    fun volunteers(offset: Int?, limit: Int, region: String?): Observable<List<VolunteerShort>> {
        return repository.loadVolunteers(offset ?: 0, limit, region ?: "all")
    }

    fun companies(offset: Int?, region: String?): Observable<List<CompanyShort>> {
        return repository.loadCompanies(offset ?: 0, region ?: "all")
    }

    fun regions(): Observable<List<Region>> {
        return repository.regions()
    }

}