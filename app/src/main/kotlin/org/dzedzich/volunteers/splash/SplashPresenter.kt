package org.dzedzich.volunteers.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.haipclick.app.root.VolunteersApp
import org.dzedzich.volunteers.auth.ui.views.AuthActivity
import org.dzedzich.volunteers.auth.ui.views.ConfirmActivity
import org.dzedzich.volunteers.main.ui.MainActivity
import org.dzedzich.volunteers.root.openScreenInNewTask
import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.utils.Constants

/**
 * Created by alexscrobot on 13.05.17.
 */
class SplashPresenter(private val fetcher: RemoteConfigFetcher) : Presenter<ISplashView>() {

    override var view: ISplashView? = null
    val user = VolunteersApp.component.preferencesManager().user()
    val router = SplashRouter()

    override fun bindView(view: ISplashView) {
        super.bindView(view)

        Log.d("Fetching", fetcher.toString())
        Log.d("user", user.toString())

        fetcher.fetch({ task ->
            if (task.isSuccessful) {
                Log.d("Fetching", "Fetch Succeeded")

                fetcher.remoteConfig.activateFetched()
            } else {
                Log.d("Fetching", "Fetch Failed")
            }
        })

        Handler().postDelayed({
            if(this.user != null && this.user.confirmed == 1 && user.googleId != null) {
                router.openMain()
            } else {
                view.renderButton()
            }
        }, 2500)
    }

    inner class SplashRouter {

        fun openAuth(token: String) {
            val bundle = Bundle()
            bundle.putString(Constants.BUNDLES.TOKEN, token)
            view?.getContext()?.openScreenInNewTask(AuthActivity::class.java, bundle)
        }

        fun openConfirm() {
            view?.getContext()?.openScreenInNewTask(ConfirmActivity::class.java)
        }

        fun openMain() {
            val bundle = Bundle()
            bundle.putBoolean(Constants.BUNDLES.FROM_CONFIRM, false)
            view?.getContext()?.openScreenInNewTask(MainActivity::class.java, bundle)
        }
    }
}