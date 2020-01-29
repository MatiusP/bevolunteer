package org.dzedzich.volunteers.profile.di

import dagger.Component
import org.dzedzich.volunteers.profile.ui.views.impl.UserProfileFragment
import org.dzedzich.volunteers.root.di.ApplicationComponent

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@ProfileScope
@Component(modules = arrayOf(ProfileModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface ProfileComponent {

    fun inject(view: UserProfileFragment)

}