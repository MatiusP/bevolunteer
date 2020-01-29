package org.dzedzich.volunteers.filter.business

import android.util.Log
import org.dzedzich.volunteers.filter.data.FilterRepository
import org.dzedzich.volunteers.profile.data.models.Activity
import org.dzedzich.volunteers.profile.data.models.Company
import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.tasks.data.models.Filter
import rx.Observable

/**
 * Created by aleksejskrobot on 03.06.17.
 */
class FilterInteractor(private val repository: FilterRepository) {

    fun loadFilter(): Observable<Filter> {
        return Observable.zip(
                repository.filter(),
                repository.regions(),
                repository.companies(),
                repository.activities(),
                {filter, regions, companies, activities -> compressData(filter, regions, companies, activities)}
        )
    }

    private fun compressData(filter: Filter, regions: List<Region>, companies: List<Company>, activities: List<Activity>): Filter {
        Log.d("filter", " $regions\n $companies\n $activities")

        filter.data.activities = activities
        filter.data.regions = regions
        filter.data.companies = companies

        return filter
    }

    fun saveFilter(filter: Filter?, checked: List<Activity>, regionPosition: Int, companies: IntArray): Observable<Filter> {
        filter?.activities = checked
        filter?.region = filter?.data?.regions?.get(regionPosition)?.id

        return repository.saveFilter(filter, companies)
    }

    fun abortFilter(): Observable<Filter> {
        return repository.abortFilter().flatMap { loadFilter() }
    }

}