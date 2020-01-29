package org.dzedzich.volunteers.feed.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.fragment_feed.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.feed.data.models.Feed
import org.dzedzich.volunteers.feed.di.DaggerFeedComponent
import org.dzedzich.volunteers.feed.di.FeedModule
import org.dzedzich.volunteers.root.structure.BaseFragment
import javax.inject.Inject

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class FeedFragment private constructor(): BaseFragment(), IFeedView, SwipeRefreshLayout.OnRefreshListener {

    override var layout: Int = R.layout.fragment_feed

    private object Holder { val INSTANCE = FeedFragment() }

    companion object {
        val instance: FeedFragment by lazy { Holder.INSTANCE }
    }

    @Inject lateinit var presenter: FeedPresenter
    @Inject lateinit var adapter: FeedAdapter

    init {
        DaggerFeedComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .feedModule(FeedModule(this))
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)

        feed_list.layoutManager = LinearLayoutManager(activity)
        feed_list.adapter = adapter

        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)
        refresh.setOnRefreshListener(this)

        Handler().postDelayed({
            if(loader != null) {
                loader.visibility = View.GONE
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        presenter.bindView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun toolbarPreference() {
        activity.supportActionBar?.title = resources.getString(R.string.feed_title)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_refresh)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> onRefresh()
        }

        return true
    }

    override fun loadDataInFeedAdapter(data: List<Feed>) {
        adapter.loadFeed(data)
    }

    override fun onAttach(context: Context?) {
        adapter.setContext(context)
        super.onAttach(context)
    }

    override fun renderAdapter(list: List<Feed>) {
        adapter.addFeed(list)

    }

    override fun onRefresh() {
        refresh.isRefreshing = true

        presenter.bindView(this)

        Handler().postDelayed({
            if(refresh != null) {
                refresh.isRefreshing = false
            }
        }, 1000)
    }
}