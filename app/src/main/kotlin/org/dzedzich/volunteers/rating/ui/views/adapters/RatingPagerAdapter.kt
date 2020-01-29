package org.dzedzich.volunteers.rating.ui.views.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.rating.ui.views.impl.CompaniesRatingFragment
import org.dzedzich.volunteers.rating.ui.views.impl.VolunteersRatingFragment

/**
 * Created by alexscrobot on 15.05.17.
 */
class RatingPagerAdapter(private val context: Context, fragmentManager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment? {

        when(position) {
            0 -> return VolunteersRatingFragment.instance
            1 -> return CompaniesRatingFragment.instance
        }

        return null
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.rating_voluntreers_tab_title)
            1 -> return context.getString(R.string.rating_companies_tab_title)
        }
        return null
    }

}