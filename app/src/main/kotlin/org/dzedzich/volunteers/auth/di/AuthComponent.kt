package org.dzedzich.volunteers.auth.di

import dagger.Component
import org.dzedzich.volunteers.auth.ui.views.AuthActivity
import org.dzedzich.volunteers.root.di.ApplicationComponent

/**
 * Created by alexscrobot on 20.04.17.
 */
@AuthScope
@Component(modules = arrayOf(AuthModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface AuthComponent {

    fun inject(view: AuthActivity)

}