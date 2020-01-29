package org.dzedzich.volunteers.rating.di

import dagger.Component
import org.dzedzich.volunteers.rating.ui.views.impl.CompaniesRatingFragment
import org.dzedzich.volunteers.rating.ui.views.impl.VolunteersRatingFragment

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@FragmentRatingScope
@Component(modules = arrayOf(FragmentRatingModule::class), dependencies = arrayOf(RatingComponent::class))
interface FragmentRatingComponent {

    fun inject(view: VolunteersRatingFragment)
    fun inject(view: CompaniesRatingFragment)

}