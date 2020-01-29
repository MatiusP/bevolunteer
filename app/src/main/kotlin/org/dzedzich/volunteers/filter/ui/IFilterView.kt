package org.dzedzich.volunteers.filter.ui

import org.dzedzich.volunteers.profile.data.models.Activity
import org.dzedzich.volunteers.profile.data.models.Company
import org.dzedzich.volunteers.root.structure.IView

/**
 * Created by aleksejskrobot on 03.06.17.
 */
interface IFilterView: IView {

    fun loadRegions(regions: Array<String?>)
    fun loadCompanies(companies: List<Company>)
    fun checkCurrentCompanies(companies: List<Company>?)
    fun loadActivities(activities: List<Activity>?)
    fun checkCurrentRegion(region: Int)
    fun checkCurrentActivities(activities: List<Activity>?)
    fun close()

}