package org.dzedzich.volunteers.tasks.ui.presenters

import android.util.Log
import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.tasks.business.TaskInteractor
import org.dzedzich.volunteers.tasks.ui.views.ITasksView

/**
 * Created by alexscrobot on 16.05.17.
 */
class TasksPresenter(private val interactor: TaskInteractor): Presenter<ITasksView>() {

    override var view: ITasksView? = null

    override fun bindView(view: ITasksView) {
        super.bindView(view)
        loadList()
    }

    fun loadList() {
        subscriptions.add(interactor.tasks().subscribe({
            view?.renderTasks(it)
        }, Throwable::printStackTrace))
    }

    fun searchTask(query: String) {
        subscriptions.add(
                interactor.tasks(query)
                        .subscribe({
                            Log.d("search:takeAPart:size", "${it?.size}")
                            view?.renderTasks(it)
                        }, Throwable::printStackTrace))
    }



}