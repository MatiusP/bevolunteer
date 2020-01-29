package org.dzedzich.volunteers.rating.ui.views.impl

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_item_rating.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.feed.ui.VolunteersAdapter
import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.rating.data.models.VolunteerShort
import org.dzedzich.volunteers.rating.di.DaggerFragmentRatingComponent
import org.dzedzich.volunteers.rating.ui.presenters.VolunteersRatingPresenter
import org.dzedzich.volunteers.rating.ui.views.IVolunteersRatingView
import org.dzedzich.volunteers.root.structure.BaseFragment
import java.util.*
import javax.inject.Inject
import android.nfc.tech.MifareUltralight.PAGE_SIZE


/**
 * Created by aleksejskrobot on 14.05.17.
 */
class VolunteersRatingFragment private constructor() : BaseFragment(), IVolunteersRatingView, androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    override var layout: Int = R.layout.fragment_item_rating

    private object Holder {
        val INSTANCE = VolunteersRatingFragment()
    }

    companion object {
        val instance: VolunteersRatingFragment by lazy { Holder.INSTANCE }
    }

    init {
        DaggerFragmentRatingComponent.builder()
                .ratingComponent(RatingFragment.instance.component)
                .build()
                .inject(this)
    }

    @Inject
    lateinit var presenter: VolunteersRatingPresenter
    @Inject
    lateinit var adapter: VolunteersAdapter
    lateinit var regionsAdapter: ArrayAdapter<String>
    lateinit var regions: List<Region>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rating_list.layoutManager = layoutManager
        rating_list.adapter = adapter
        rating_list.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!presenter.isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    presenter.loadMore()

                }
            }
        })

        if (adapter.itemCount > 0) loader.visibility = View.GONE

        regionsAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, mutableListOf())

        refresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        refresh.setOnRefreshListener(this)
    }

    override fun toolbarPreference() {
        activity.supportActionBar?.title = resources.getString(R.string.rating_title)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_refresh)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onRefresh()
            R.id.action_filter -> openFilter()
        }

        return true
    }

    override fun loadRegions(list: List<Region>) {
        regions = list
        regionsAdapter.clear()
        regionsAdapter.addAll(list.map { it.label ?: "" })
        regionsAdapter.notifyDataSetChanged()
    }

    private fun openFilter() {
        val dialog = Dialog(context)
        val list = ListView(context)
        dialog.setContentView(list)
        dialog.setTitle(getString(R.string.region))
        list.adapter = regionsAdapter
        list.setOnItemClickListener { adapterView, view, i, l ->
            presenter.filter(regions[i].id)
            dialog.hide()
        }
        dialog.show()
    }

    override fun loadData(list: List<VolunteerShort>) {
        adapter.setContext(context)
        adapter.loadList(list)
        loader?.let { it.visibility = View.GONE }
    }

    override fun addData(list: List<VolunteerShort>) {
        adapter.addList(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun onResume() {
        super.onResume()
        presenter.bindView(this)
    }

    override fun showError(message: String?) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRefresh() {
        refresh.isRefreshing = true

        presenter.bindView(this)

        Handler().postDelayed({
            if (refresh != null) {
                refresh.isRefreshing = false
            }
        }, 5000)
    }
}