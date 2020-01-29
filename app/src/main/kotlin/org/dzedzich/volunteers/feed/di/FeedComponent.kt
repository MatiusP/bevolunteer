package org.dzedzich.volunteers.feed.di

import dagger.Component
import org.dzedzich.volunteers.feed.ui.FeedFragment
import org.dzedzich.volunteers.root.di.ApplicationComponent

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@FeedScope
@Component(modules = arrayOf(FeedModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface FeedComponent {

    fun inject(view: FeedFragment)

}