package org.dzedzich.volunteers.comments.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.comments.business.CommentsInteractor
import org.dzedzich.volunteers.comments.ui.presenters.CommentsPresenter
import org.dzedzich.volunteers.comments.ui.views.CommentsAdapter
import org.dzedzich.volunteers.tasks.data.TaskRepository

/**
 * Created by aleksejskrobot on 27.05.17.
 */
@Module
class CommentsModule {

    @Provides
    @CommentsScope
    fun repository(): TaskRepository {
        return TaskRepository()
    }

    @CommentsScope
    @Provides
    fun interactor(taskRepository: TaskRepository): CommentsInteractor {
        return CommentsInteractor(taskRepository)
    }

    @CommentsScope
    @Provides
    fun presenter(commentsInteractor: CommentsInteractor): CommentsPresenter {
        return CommentsPresenter(commentsInteractor)
    }

    @CommentsScope
    @Provides
    fun adapter(): CommentsAdapter {
        return CommentsAdapter()
    }
}