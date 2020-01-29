package org.dzedzich.volunteers.rating.ui.views.impl

import android.content.Context
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import android.view.*
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.fragment_rating.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.filter.ui.FilterActivity
import org.dzedzich.volunteers.main.ui.IMainView
import org.dzedzich.volunteers.rating.di.DaggerRatingComponent
import org.dzedzich.volunteers.rating.di.RatingComponent
import org.dzedzich.volunteers.rating.di.RatingModule
import org.dzedzich.volunteers.rating.ui.presenters.RatingPresenter
import org.dzedzich.volunteers.rating.ui.views.IRatingView
import org.dzedzich.volunteers.rating.ui.views.adapters.RatingPagerAdapter
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.root.structure.BaseFragment
import javax.inject.Inject

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class RatingFragment private constructor(): BaseFragment(), IRatingView {

    override var layout: Int = R.layout.fragment_rating

    private object Holder { val INSTANCE = RatingFragment() }

    companion object {
        val instance: RatingFragment by lazy { Holder.INSTANCE }
    }

    lateinit var component: RatingComponent

    @Inject lateinit var presenter: RatingPresenter
    @Inject lateinit var pageAdapter: RatingPagerAdapter

    private var ratingTab: TabLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        component = DaggerRatingComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .ratingModule(RatingModule(this))
                .build()
        component.inject(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)

        ratingTab = (activity as IMainView).provideTabLayout()

        ratingTab?.visibility = View.VISIBLE

        rating_viewpager.adapter = pageAdapter
        ratingTab?.setupWithViewPager(rating_viewpager)
    }

    override fun toolbarPreference() {
        activity.supportActionBar?.title = resources.getString(R.string.rating_title)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()

        inflater?.inflate(R.menu.menu_rating_list, menu)
    }

    override fun onAttach(context: Context?) {
//        adapter.setContext(context)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ratingTab?.visibility = View.GONE
    }
}