package org.dzedzich.volunteers.profile.ui.presenters

import android.graphics.Bitmap
import android.util.Log
import android.view.MenuItem
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.options.OptionsActivity
import org.dzedzich.volunteers.profile.bussiness.UserProfileInteractor
import org.dzedzich.volunteers.profile.ui.views.IUserProfileView
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.root.text

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class UserProfilePresenter(private val interactor: UserProfileInteractor) : Presenter<IUserProfileView>() {

    override var view: IUserProfileView? = null

    override fun bindView(view: IUserProfileView) {
        super.bindView(view)

        subscriptions.add(interactor.profile().subscribe ({
            Log.d("user", it.toString())
            view.user = it
            view.renderProfile()
        }, Throwable::printStackTrace))
    }

    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> saveUserData()
            R.id.action_settings -> view?.getContext()?.openScreen(OptionsActivity::class.java)
        }

        return true
    }

    fun saveUserData() {
        subscriptions.add(interactor.saveUser(
                view?.getName(),
                view?.getPhone(),
                view?.getSite(),
                view?.getSlogan(),
                view?.getAbout(),
                view?.isViewPhoneNumber()

        ).subscribe ({
            text(R.string.actions_data_saved)
            view?.user = it
            view?.renderProfile()
        } , Throwable::printStackTrace))
    }

    fun  sendAvatar(uri: Bitmap?) {
        subscriptions.add(interactor.sendAvatar(view?.getContext(), uri).subscribe ({
            view?.user?.avatar = it.avatar
            view?.renderProfile()
            text(if(it.success) R.string.profile_avatar_success else R.string.profile_avatar_fails)
        }, Throwable::printStackTrace))
    }

    fun sendTask(task: String) {
        subscriptions.add(interactor.makeTask(task).subscribe({}, Throwable::printStackTrace, { text(R.string.profile_add_task_success) }))
    }


}