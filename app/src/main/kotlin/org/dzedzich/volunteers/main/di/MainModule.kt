package org.dzedzich.volunteers.main.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.main.route.MainRouter
import org.dzedzich.volunteers.main.ui.IMainView
import org.dzedzich.volunteers.main.ui.MainActivity
import org.dzedzich.volunteers.main.ui.MainPresenter
import org.dzedzich.volunteers.push.PushRepository

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@Module
class MainModule(private val view: MainActivity) {

    @Provides
    @MainScope
    fun view(): IMainView {
        return view
    }

    @Provides
    @MainScope
    fun presenter(router: MainRouter, pushRepository: PushRepository): MainPresenter {
        return MainPresenter(router, pushRepository)
    }

    @Provides
    @MainScope
    fun router(): MainRouter {
        return MainRouter(R.id.contentContainer, view.supportFragmentManager)
    }

    @Provides
    @MainScope
    fun tokenRepository(): PushRepository {
        return PushRepository()
    }
}