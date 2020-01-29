package org.dzedzich.volunteers.auth.ui.views

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.view_auth_confirm.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.auth.business.ConfirmInteractor
import org.dzedzich.volunteers.auth.data.AuthRepository
import org.dzedzich.volunteers.main.ui.MainActivity
import org.dzedzich.volunteers.root.openScreenInNewTask
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.root.text
import org.dzedzich.volunteers.utils.Constants
import org.jetbrains.anko.enabled
import rx.subscriptions.CompositeSubscription

/**
 * Created by alexscrobot on 13.05.17.
 */
class ConfirmActivity : BaseActivity(), IConfirmView {

    val interactor = ConfirmInteractor(AuthRepository())
    val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun toolbarPreference() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_auth_confirm)

        sms.maxEms = 4
        sms.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s != null && s.length == 4) {
                    hideSmsField()
                    subscriptions.add(interactor.confirm(s.toString()).subscribe(
                            {
                                if(it.success == true) {
                                    Handler().postDelayed({
                                        val bundle = Bundle()
                                        bundle.putBoolean(Constants.BUNDLES.FROM_CONFIRM, true)
                                        openScreenInNewTask(MainActivity::class.java, bundle)
                                    }, 2500)
                                } else {
                                    renderSmsField()
                                }
                            },
                            {
                                error ->
                                    error.printStackTrace()
                                    renderSmsField()
                            }
                    ))
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }

    fun renderSmsField() {
        sms.enabled = true
        sms.setText("")
        loader.visibility = View.GONE
        text(R.string.auth_errors_confirm)
    }

    fun hideSmsField() {
        sms.enabled = false
        loader.visibility = View.VISIBLE
    }
}