package org.dzedzich.volunteers.rating.di

import dagger.Component
import org.dzedzich.volunteers.rating.ui.presenters.RatingPresenter
import org.dzedzich.volunteers.rating.ui.views.impl.RatingFragment
import org.dzedzich.volunteers.root.di.ApplicationComponent

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@RatingScope
@Component(modules = arrayOf(RatingModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface RatingComponent {

    fun inject(view: RatingFragment)
    fun presenter(): RatingPresenter

}