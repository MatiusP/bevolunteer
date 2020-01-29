package org.dzedzich.volunteers.tasks.ui.views.impl

import android.os.Bundle
import android.os.Handler
import androidx.core.view.MenuItemCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_item_tasks.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.filter.ui.FilterActivity
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.root.structure.BaseFragment
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.di.DaggerFragmentTaskComponent
import org.dzedzich.volunteers.tasks.di.FragmentTaskModule
import org.dzedzich.volunteers.tasks.ui.presenters.TasksPresenter
import org.dzedzich.volunteers.tasks.ui.views.ITasksView
import org.dzedzich.volunteers.tasks.ui.views.adapters.TaskAdapter
import javax.inject.Inject

/**
 * Created by alexscrobot on 16.05.17.
 */
class TasksFragment private constructor(): BaseFragment(), ITasksView, androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    override var layout: Int = R.layout.fragment_item_tasks

    private object Holder { val INSTANCE = TasksFragment() }

    companion object {
        val instance: TasksFragment by lazy { Holder.INSTANCE }
    }

    init {
        DaggerFragmentTaskComponent.builder()
                .taskComponent(TaskTabFragment.instance.component)
                .fragmentTaskModule(FragmentTaskModule())
                .build()
                .inject(this)
    }

    @Inject lateinit var presenter: TasksPresenter
    @Inject lateinit var adapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)

        if(adapter.itemCount > 0) {
            loader.visibility = View.GONE
        }

        task_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        task_list.adapter = adapter

        refresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        refresh.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadList()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> onRefresh()
            R.id.action_filter -> context.openScreen(FilterActivity::class.java)
        }

        return true
    }

    override fun toolbarPreference() {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_refresh)
    }

    override fun renderTasks(list: List<Task?>?) {

        Log.d(TAG, list.toString())
        adapter.context = context
        adapter.loadList(list)

        loader.visibility = View.GONE
        no_results.visibility = if(list?.isEmpty() ?: false) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()

        inflater?.inflate(R.menu.menu_task_list, menu)

        val item = menu?.findItem(R.id.action_search)
        val searchView = SearchView(activity.supportActionBar?.themedContext)
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItemCompat.SHOW_AS_ACTION_IF_ROOM)
        MenuItemCompat.setActionView(item, searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.searchTask(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.searchTask(newText)
                return false
            }
        })

        searchView.setOnClickListener {

        }
    }

    override fun onRefresh() {
        refresh.isRefreshing = true

        presenter.loadList()

        Handler().postDelayed({
            if(refresh != null) {
                refresh.isRefreshing = false
            }
        }, 5000)
    }

    override fun getLoadedTasks(): List<Task?> = adapter.list.toList()
}