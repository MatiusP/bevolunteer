package org.dzedzich.volunteers.splash.di

import dagger.Component
import org.dzedzich.volunteers.root.di.ApplicationComponent
import org.dzedzich.volunteers.splash.SplashActivity

/**
 * Created by aleksejskrobot on 21.05.17.
 */
@SplashScope
@Component(modules = arrayOf(SplashModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface SplashComponent {

    fun inject(view: SplashActivity)

}