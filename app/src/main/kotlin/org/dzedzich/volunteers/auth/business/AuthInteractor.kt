package org.dzedzich.volunteers.auth.business

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import org.dzedzich.volunteers.auth.data.AuthRepository
import org.dzedzich.volunteers.auth.data.models.GoogleUserProfile
import org.dzedzich.volunteers.auth.ui.views.AuthActivity
import org.dzedzich.volunteers.auth.ui.views.ConfirmActivity
import org.dzedzich.volunteers.main.ui.MainActivity
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.root.openScreenInNewTask
import org.dzedzich.volunteers.root.text
import org.dzedzich.volunteers.utils.Constants
import rx.Observable
import rx.lang.kotlin.onError
import java.util.*

/**
 * Created by alexscrobot on 13.05.17.
 */
class AuthInteractor(private val context: Context, private val repository: AuthRepository): Validator.ValidationListener {

    @NotEmpty
    private var name: EditText? = null

    @NotEmpty
    private var sirname: EditText? = null
    private var token: String? = null
    private var googleToken: String? = null

    private val validator: Validator = Validator(this)

    private var data: Bundle? = null

    private val regionsId: MutableList<String> = ArrayList()

    init {
        validator.setValidationListener(this)
    }

    fun fieldsInit(firstName: EditText?, lastname: EditText?) {
        name = firstName
        sirname = lastname
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        if (errors != null) {
            for (error in errors) {
                val view = error.view
                val message = error.getCollatedErrorMessage(context)

                // Display error messages ;)
                if (view is EditText) {
                    view.error = message
                } else {
                    text(message)
                }
            }
        }
    }

    override fun onValidationSucceeded() {
        var city = data?.getInt("city") as Int
        if(city > regionsId.size || city < 0) city = 0
        repository.auth(
                    data?.getString("firstname"),
                    data?.getString("lastname"),
                    token ?: "",
                    googleToken,
                    regionsId[city],
                    Constants.LOCALIZATION.BELARUS
                )
                .subscribe(
                    {
                        (context as AuthActivity).openScreenInNewTask(MainActivity::class.java)
                    },
                        {
                        }
                )
    }

    fun getData(data: Bundle?, form: Map<String, EditText>?, token: String?, googleToken: String?) {
        fieldsInit(form?.get(Constants.FORM.AUTH.FIRST_NAME), form?.get(Constants.FORM.AUTH.LAST_NAME))
        this.data = data
        this.token = token
        this.googleToken = googleToken
        validator.validate()
        /*if(validatePhone()) {
            validator.validate()
        } else {
            telephone?.error = context.resources.getString(R.string.auth_errors_phone)
        }*/
    }

    /*
    fun validatePhone(): Boolean {
        val phone = telephone?.text?.toString() ?: return false

        return phone.length >= 9
    }*/

    fun getRegions(): Observable<Array<String>>? {
        return repository.regions()
                ?.onError { it.printStackTrace() }
                ?.onErrorReturn { repository.manualRegionList() }
                ?.map {
                    val list: MutableList<String> = ArrayList()

                    it.forEach {
                        list.add(it.label as String)
                        regionsId.add(it.id as String)
                    }

                    return@map list.toTypedArray()
                }
    }

    fun getGoogleUserProfile(token: String): Observable<GoogleUserProfile>? {
        return repository.googleUserProfile(token)
                .onError { it.printStackTrace() }
                .onErrorReturn { GoogleUserProfile() }
    }
}