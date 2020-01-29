package org.dzedzich.volunteers.rating.ui.views

import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.rating.data.models.VolunteerShort
import org.dzedzich.volunteers.root.structure.IView

/**
 * Created by alexscrobot on 15.05.17.
 */
interface IVolunteersRatingView : IView {

    fun loadData(list: List<VolunteerShort>)

    fun addData(list: List<VolunteerShort>)

    fun loadRegions(list: List<Region>)

    fun showError(message: String?)
}