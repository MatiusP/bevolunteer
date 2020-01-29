package org.dzedzich.volunteers.auth.ui.views

import android.os.Bundle
import android.widget.EditText
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.root.structure.IView

/**
 * Created by alexscrobot on 13.05.17.
 */
interface IAuthView : IView {
    fun loadData(): Bundle
    fun getForm(): Map<String, EditText>?
    fun renderRegions(data: Array<String>)
    fun setUserName(firstName: String, lastName: String)
    fun getToken() : String
}