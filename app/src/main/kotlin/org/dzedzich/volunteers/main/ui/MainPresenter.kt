package org.dzedzich.volunteers.main.ui

import android.util.Log
import com.roughike.bottombar.OnTabSelectListener
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.main.route.MainRouter
import org.dzedzich.volunteers.push.PushRepository
import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.utils.Constants

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class MainPresenter(private val router: MainRouter, private val pushRepository: PushRepository): Presenter<IMainView>(), OnTabSelectListener {

    override var view: IMainView? = null

    override fun bindView(view: IMainView) {
        super.bindView(view)

        view.initPoint()
    }

    fun refreshTheToken(token: String?) {
        if(token != null) {
            subscriptions.add(pushRepository.refreshToken(token).subscribe({ Log.d("firebase:token", "successfully refreshed") }, Throwable::printStackTrace))
        } else {
            Log.d("firebase:token", "token is null")
        }
    }

    override fun onTabSelected(tabId: Int) {
        when(tabId) {
            R.id.tab_feed -> router.route(Constants.ROUTER.MAIN.FEED)
            R.id.tab_rating -> router.route(Constants.ROUTER.MAIN.RATING)
            R.id.tab_tasks -> router.route(Constants.ROUTER.MAIN.TASKS)
            R.id.tab_account -> router.route(Constants.ROUTER.MAIN.ACCOUNT)
        }
    }
}