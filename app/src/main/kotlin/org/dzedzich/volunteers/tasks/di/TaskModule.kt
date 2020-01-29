package org.dzedzich.volunteers.tasks.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.tasks.business.TaskInteractor
import org.dzedzich.volunteers.tasks.data.TaskRepository
import org.dzedzich.volunteers.tasks.ui.presenters.TaskPresenter
import org.dzedzich.volunteers.tasks.ui.views.adapters.SubscribersAdapter
import org.dzedzich.volunteers.tasks.ui.views.adapters.TaskPagerAdapter
import org.dzedzich.volunteers.tasks.ui.views.adapters.VotingAdapter
import org.dzedzich.volunteers.tasks.ui.views.impl.TaskTabFragment
import org.dzedzich.volunteers.tasks.ui.views.models.CompanyVotingItem
import org.dzedzich.volunteers.tasks.ui.views.models.VolunterVotingItem

/**
 * Created by alexscrobot on 16.05.17.
 */
@Module
class TaskModule {

    @TaskScope
    @Provides
    fun pagerAdapter(): TaskPagerAdapter {
        return TaskPagerAdapter(TaskTabFragment.instance.context, TaskTabFragment.instance.childFragmentManager)
    }

    @TaskScope
    @Provides
    fun presenter(interactor: TaskInteractor): TaskPresenter {
        return TaskPresenter(interactor)
    }

    @TaskScope
    @Provides
    fun interactor(repository: TaskRepository): TaskInteractor {
        return TaskInteractor(repository)
    }

    @TaskScope
    @Provides
    fun repository(): TaskRepository {
        return TaskRepository()
    }

    @TaskScope
    @Provides
    fun subscribersAdapter(): SubscribersAdapter {
        return SubscribersAdapter()
    }

    @TaskScope
    @Provides
    fun volunteersVotingAdapter(): VotingAdapter<VolunterVotingItem> {
        return VotingAdapter()
    }

    @TaskScope
    @Provides
    fun companyVotingAdapter(): VotingAdapter<CompanyVotingItem> {
        return VotingAdapter()
    }

}