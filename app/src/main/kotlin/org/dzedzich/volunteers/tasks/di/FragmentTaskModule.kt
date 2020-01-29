package org.dzedzich.volunteers.tasks.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.tasks.business.TaskInteractor
import org.dzedzich.volunteers.tasks.data.TaskRepository
import org.dzedzich.volunteers.tasks.ui.presenters.CompletedTasksPresenter
import org.dzedzich.volunteers.tasks.ui.presenters.TasksPresenter
import org.dzedzich.volunteers.tasks.ui.presenters.UserTasksPresenter
import org.dzedzich.volunteers.tasks.ui.views.adapters.TaskAdapter

/**
 * Created by alexscrobot on 16.05.17.
 */
@Module
class FragmentTaskModule {

    @Provides
    @FragmentTaskScope
    fun adapter(): TaskAdapter {
        return TaskAdapter()
    }

    @Provides
    @FragmentTaskScope
    fun repository(): TaskRepository {
        return TaskRepository()
    }

    @Provides
    @FragmentTaskScope
    fun interactor(repository: TaskRepository): TaskInteractor {
        return TaskInteractor(repository)
    }

    @Provides
    @FragmentTaskScope
    fun taskspresenter(interactor: TaskInteractor): TasksPresenter {
        return TasksPresenter(interactor)
    }

    @Provides
    @FragmentTaskScope
    fun userTaskspresenter(interactor: TaskInteractor): UserTasksPresenter {
        return UserTasksPresenter(interactor)
    }

    @Provides
    @FragmentTaskScope
    fun completedTasksPresenter(interactor: TaskInteractor): CompletedTasksPresenter {
        return CompletedTasksPresenter(interactor)
    }
}