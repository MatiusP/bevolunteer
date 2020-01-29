package org.dzedzich.volunteers.tasks.ui.views.impl

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.main.ui.IMainView
import org.dzedzich.volunteers.root.structure.BaseFragment
import org.dzedzich.volunteers.tasks.di.DaggerTaskComponent
import org.dzedzich.volunteers.tasks.di.TaskComponent
import org.dzedzich.volunteers.tasks.ui.views.adapters.TaskPagerAdapter
import javax.inject.Inject

/**
 * Created by alexscrobot on 16.05.17.
 */
class TaskTabFragment(override var layout: Int) : BaseFragment() {

    private object Holder { val INSTANCE = TaskTabFragment(R.layout.fragment_tasks) }

    companion object {
        val instance: TaskTabFragment by lazy { Holder.INSTANCE }
    }

    lateinit var component: TaskComponent

    private var tasksTab: TabLayout? = null

    @Inject lateinit var pageAdapter: TaskPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        component = DaggerTaskComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .build()
        component.inject(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksTab = (activity as IMainView).provideTabLayout()

        tasksTab?.visibility = View.VISIBLE

        task_viewpager.adapter = pageAdapter
        tasksTab?.setupWithViewPager(task_viewpager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tasksTab?.visibility = View.GONE
    }

    override fun toolbarPreference() {
        activity.supportActionBar?.title = resources.getString(R.string.tasks_title)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}