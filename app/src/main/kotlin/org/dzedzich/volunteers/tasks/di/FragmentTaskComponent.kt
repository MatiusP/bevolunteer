package org.dzedzich.volunteers.tasks.di

import dagger.Component
import org.dzedzich.volunteers.tasks.ui.views.impl.CompletedTasksFragment
import org.dzedzich.volunteers.tasks.ui.views.impl.TasksFragment
import org.dzedzich.volunteers.tasks.ui.views.impl.UserTasksFragment

/**
 * Created by alexscrobot on 16.05.17.
 */
@FragmentTaskScope
@Component(dependencies = arrayOf(TaskComponent::class), modules = arrayOf(FragmentTaskModule::class))
interface FragmentTaskComponent {

    fun inject(view: TasksFragment)
    fun inject(view: UserTasksFragment)
    fun inject(view: CompletedTasksFragment)

}