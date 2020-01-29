package org.dzedzich.volunteers.profile.ui.views

import android.os.Bundle
import android.view.View
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.root.structure.IView

/**
 * Created by aleksejskrobot on 14.05.17.
 */
interface IUserProfileView : IView {

    var user: User?

    fun onViewCreated(view: View, savedInstanceState: Bundle?)
    fun renderProfile()

    fun getName(): String?
    fun getPhone(): String?
    fun getSite(): String?
    fun getSlogan(): String?
    fun getAbout(): String?
    fun isViewPhoneNumber(): Int?
}