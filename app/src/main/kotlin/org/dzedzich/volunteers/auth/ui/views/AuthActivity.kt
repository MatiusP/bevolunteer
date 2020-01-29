
package org.dzedzich.volunteers.auth.ui.views

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.view_auth.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.auth.di.AuthModule
import org.dzedzich.volunteers.auth.di.DaggerAuthComponent
import org.dzedzich.volunteers.auth.ui.presenters.AuthPresenter
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.utils.Constants
import java.util.*
import javax.inject.Inject


/**
 * Created by alexscrobot on 13.05.17.
 */
class AuthActivity: BaseActivity(), IAuthView {

    @Inject lateinit var presenter: AuthPresenter

    init {
        DaggerAuthComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .authModule(AuthModule(this))
                .build()
                .inject(this)
    }

    override fun toolbarPreference() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_auth)

        presenter.bindView(this)
    }

    override fun getToken(): String {
        return intent.extras.getString(Constants.BUNDLES.TOKEN)
    }

    override fun setUserName(firstName: String, lastName: String) {
        firstname.setText(firstName)
        lastname.setText(lastName)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun renderRegions(data: Array<String>) {
// адаптер
        val adapter = ArrayAdapter(this, R.layout.spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner.prompt = resources.getString(R.string.auth_place)
        spinner.setSelection(0)
    }

    fun onSaveClick(view: View) {
        presenter.send()
    }

    override fun loadData(): Bundle {
        val bundle = Bundle()
        bundle.putInt("city", spinner.selectedItemPosition)
        //bundle.putString("phone", phone.rawText)
        bundle.putString("firstname", firstname.text.toString())
        bundle.putString("lastname", lastname.text.toString())

        return bundle
    }

    override fun getForm(): Map<String, EditText> {
        val map = HashMap<String, EditText>()
        //map.put(Constants.FORM.AUTH.PHONE, phone)
        map.put(Constants.FORM.AUTH.FIRST_NAME, firstname)
        map.put(Constants.FORM.AUTH.LAST_NAME, lastname)

        return map
    }
}