package org.dzedzich.volunteers.tasks.ui.presenters

import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.tasks.business.TaskInteractor
import org.dzedzich.volunteers.tasks.ui.views.ICompletedView

/**
 * Created by alexscrobot on 16.05.17.
 */
class CompletedTasksPresenter(private val interactor: TaskInteractor): Presenter<ICompletedView>() {
    override var view: ICompletedView? = null

    override fun bindView(view: ICompletedView) {
        super.bindView(view)

        loadList()
    }

    fun loadList() {
        subscriptions.add(interactor.completedTasks().subscribe ({
            view?.renderTasks(it)
        }, Throwable::printStackTrace))
    }

}