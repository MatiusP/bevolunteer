package org.dzedzich.volunteers.tasks.ui.views

import org.dzedzich.volunteers.tasks.data.models.Task

/**
 * Created by alexscrobot on 16.05.17.
 */
interface ITasksView : ITasks {

    fun getLoadedTasks(): List<Task?>

}