package org.dzedzich.volunteers.filter.di

import dagger.Component
import org.dzedzich.volunteers.filter.ui.FilterActivity
import org.dzedzich.volunteers.root.di.ApplicationComponent

/**
 * Created by aleksejskrobot on 03.06.17.
 */
@FilterScope
@Component(modules = arrayOf(FilterModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface FilterComponent {

    fun inject(view: FilterActivity)

}