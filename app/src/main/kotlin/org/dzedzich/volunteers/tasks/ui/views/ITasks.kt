package org.dzedzich.volunteers.tasks.ui.views

import org.dzedzich.volunteers.root.structure.IView
import org.dzedzich.volunteers.tasks.data.models.Task

/**
 * Created by alexscrobot on 16.05.17.
 */
interface ITasks : IView {

    fun renderTasks(list: List<Task?>?)

}