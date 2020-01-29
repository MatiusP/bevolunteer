package org.dzedzich.volunteers.auth.ui.presenters

import com.haipclick.app.root.VolunteersApp
import org.dzedzich.volunteers.auth.business.AuthInteractor
import org.dzedzich.volunteers.auth.ui.views.IAuthView
import org.dzedzich.volunteers.root.structure.Presenter

/**
 * Created by alexscrobot on 13.05.17.
 */
class AuthPresenter(val interactor: AuthInteractor) : Presenter<IAuthView>() {

    override var view: IAuthView? = null
    val user = VolunteersApp.component.preferencesManager().user()

    override fun bindView(view: IAuthView) {
        super.bindView(view)

        if(user != null) {
            val name = user.name.orEmpty().split(" ")
            view.setUserName(name[0], if(name.size > 1) name[1] else "")
        } else getGoogleUserProfile(view.getToken())

        regions()
    }

    fun send() {
        interactor.getData(view?.loadData(), view?.getForm(), user?.token ?: "", view?.getToken())
    }

    private fun getGoogleUserProfile(token: String) {
        subscriptions.add(interactor.getGoogleUserProfile(token)?.subscribe ({
            view?.setUserName(it.givenName, it.familyName)
        }, Throwable::printStackTrace))
    }

    fun regions() {
        subscriptions.add(interactor.getRegions()?.subscribe ({
            view?.renderRegions(it)
        }, Throwable::printStackTrace))
    }

}