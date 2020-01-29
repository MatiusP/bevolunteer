package org.dzedzich.volunteers.main.di

import dagger.Component
import org.dzedzich.volunteers.main.ui.MainActivity
import org.dzedzich.volunteers.root.di.ApplicationComponent

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@MainScope
@Component(modules = arrayOf(MainModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface MainComponent {

    fun inject(view: MainActivity)

}