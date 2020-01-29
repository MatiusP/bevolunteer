package org.dzedzich.volunteers.tasks.di

import dagger.Component
import org.dzedzich.volunteers.root.di.ApplicationComponent
import org.dzedzich.volunteers.tasks.ui.views.impl.TaskActivity
import org.dzedzich.volunteers.tasks.ui.views.impl.TaskTabFragment

/**
 * Created by alexscrobot on 16.05.17.
 */
@TaskScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(TaskModule::class))
interface TaskComponent {

    fun inject(view: TaskTabFragment)
    fun inject(view: TaskActivity)

}