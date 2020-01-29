package org.dzedzich.volunteers.main.route

import androidx.fragment.app.FragmentManager
import android.util.Log
import org.dzedzich.volunteers.feed.ui.FeedFragment
import org.dzedzich.volunteers.profile.ui.views.impl.UserProfileFragment
import org.dzedzich.volunteers.rating.ui.views.impl.RatingFragment
import org.dzedzich.volunteers.root.structure.AbstractFragmentRouter
import org.dzedzich.volunteers.tasks.ui.views.impl.TaskTabFragment
import org.dzedzich.volunteers.utils.Constants

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class MainRouter(contentContainer: Int, override var fragmentManager: androidx.fragment.app.FragmentManager) : AbstractFragmentRouter(contentContainer) {

    private val TAG = MainRouter::class.java.simpleName

    fun route(route: String) {
        when(route) {
            Constants.ROUTER.MAIN.ACCOUNT -> this.routeProfile()
            Constants.ROUTER.MAIN.FEED -> this.routeFeed()
            Constants.ROUTER.MAIN.RATING -> this.routeRating()
            Constants.ROUTER.MAIN.TASKS -> this.routeTasks()
        }
    }

    private fun routeProfile() {
        Log.d(TAG, "routeProfile")
        invoke(UserProfileFragment.instance, Constants.ROUTER.MAIN.ACCOUNT)
    }

    private fun routeFeed() {
        Log.d(TAG, "routeFeed")
        invoke(FeedFragment.instance, Constants.ROUTER.MAIN.FEED)
    }

    private fun routeTasks() {
        Log.d(TAG, "routeTasks")
        invoke(TaskTabFragment.instance, Constants.ROUTER.MAIN.TASKS)
    }

    private fun routeRating() {
        Log.d(TAG, "routeRating")
        invoke(RatingFragment.instance, Constants.ROUTER.MAIN.RATING)
    }

}