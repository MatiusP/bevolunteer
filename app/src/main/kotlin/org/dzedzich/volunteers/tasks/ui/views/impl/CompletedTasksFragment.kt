package org.dzedzich.volunteers.tasks.ui.views.impl

import android.os.Bundle
import android.os.Handler
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_item_tasks.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.root.structure.BaseFragment
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.di.DaggerFragmentTaskComponent
import org.dzedzich.volunteers.tasks.ui.presenters.CompletedTasksPresenter
import org.dzedzich.volunteers.tasks.ui.views.ICompletedView
import org.dzedzich.volunteers.tasks.ui.views.adapters.TaskAdapter
import javax.inject.Inject

/**
 * Created by alexscrobot on 16.05.17.
 */
class CompletedTasksFragment private constructor(): BaseFragment(), ICompletedView, androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    override var layout: Int = R.layout.fragment_item_tasks

    private object Holder { val INSTANCE = CompletedTasksFragment() }

    companion object {
        val instance: CompletedTasksFragment by lazy { Holder.INSTANCE }
    }

    init {
        DaggerFragmentTaskComponent.builder()
                .taskComponent(TaskTabFragment.instance.component)
                .build()
                .inject(this)
    }

    @Inject lateinit var presenter: CompletedTasksPresenter
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
        }

        return true
    }

    override fun toolbarPreference() {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_refresh)
    }

    override fun renderTasks(list: List<Task?>?) {
        adapter.context = context
        adapter.loadList(list)

        loader.visibility = View.GONE
        no_results.visibility = if(list?.isEmpty() ?: false) View.VISIBLE else View.GONE
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
}