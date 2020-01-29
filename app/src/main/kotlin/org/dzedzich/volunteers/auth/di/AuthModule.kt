package org.dzedzich.volunteers.auth.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.auth.business.AuthInteractor
import org.dzedzich.volunteers.auth.data.AuthRepository
import org.dzedzich.volunteers.auth.ui.presenters.AuthPresenter
import org.dzedzich.volunteers.auth.ui.views.AuthActivity
import org.dzedzich.volunteers.auth.ui.views.IAuthView

/**
 * Created by alexscrobot on 20.04.17.
 */
@Module
class AuthModule(val view: AuthActivity) {

    @Provides
    @AuthScope
    fun view(): IAuthView {
        return view
    }

    @Provides
    @AuthScope
    fun presenter(interactor: AuthInteractor): AuthPresenter {
        return AuthPresenter(interactor)
    }

    @Provides
    @AuthScope
    fun interactor(repository: AuthRepository): AuthInteractor {
        return AuthInteractor(view, repository)
    }

    @Provides
    @AuthScope
    fun repository(): AuthRepository {
        return AuthRepository()
    }

}