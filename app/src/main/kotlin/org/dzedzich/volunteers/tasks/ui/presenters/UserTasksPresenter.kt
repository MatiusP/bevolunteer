package org.dzedzich.volunteers.tasks.ui.presenters

import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.tasks.business.TaskInteractor
import org.dzedzich.volunteers.tasks.ui.views.IUserTasksView

/**
 * Created by alexscrobot on 16.05.17.
 */
class UserTasksPresenter(private val interactor: TaskInteractor) : Presenter<IUserTasksView>() {
    override var view: IUserTasksView? = null

    override fun bindView(view: IUserTasksView) {
        super.bindView(view)

        loadList()
    }

    fun loadList() {
        subscriptions.add(interactor.activeTasks().subscribe ({
            view?.renderTasks(it)
        }, Throwable::printStackTrace))
    }
}