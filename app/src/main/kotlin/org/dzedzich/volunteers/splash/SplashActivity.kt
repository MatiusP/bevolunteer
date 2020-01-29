package org.dzedzich.volunteers.splash

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.view_spash.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.splash.di.DaggerSplashComponent
import org.dzedzich.volunteers.splash.di.SplashModule
import org.jetbrains.anko.onClick
import javax.inject.Inject
import com.google.android.gms.common.AccountPicker
import android.content.Intent
import android.accounts.AccountManager
import com.google.android.gms.auth.GoogleAuthUtil
import android.accounts.Account
import com.google.android.gms.auth.UserRecoverableAuthException


/**
 * Created by alexscrobot on 13.05.17.
 */
class SplashActivity : BaseActivity(), ISplashView {
    companion object {
        private const val RC_SIGN_IN = 0

        private const val G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me"
        private const val USER_INFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile"

        private const val SCOPES = "$G_PLUS_SCOPE $USER_INFO_SCOPE"
    }
    @Inject lateinit var presenter: SplashPresenter

    init {
        DaggerSplashComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }

    override fun toolbarPreference() {}

    override fun getContext(): Context {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_spash)
        presenter.bindView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    @Suppress("DEPRECATION")
    override fun renderButton() {
        setProgress(false)
        register.onClick {
            val intent = AccountPicker.newChooseAccountIntent(null, null, arrayOf("com.google"),
                    false, null, null, null, null)
            startActivityForResult(intent, RC_SIGN_IN)
            setProgress(true)
        }

        splash_text.movementMethod = LinkMovementMethod.getInstance()
        splash_text.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             Html.fromHtml(getString(R.string.registration_info), Html.FROM_HTML_MODE_LEGACY)
        } else Html.fromHtml(getString(R.string.registration_info))
    }

    private fun setProgress(progress: Boolean) {
        runOnUiThread {
            if (progress) {
                loader.visibility = View.VISIBLE
                welcome.visibility = View.GONE
                register.visibility = View.GONE
                splash_text.visibility = View.GONE
            } else {
                loader.visibility = View.GONE
                welcome.visibility = View.VISIBLE
                register.visibility = View.VISIBLE
                splash_text.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            if(resultCode == Activity.RESULT_OK && data != null) {
                val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                val accountDetails = Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)

                Thread {
                    try {
                        val token = GoogleAuthUtil.getToken(this, accountDetails, SCOPES)
                        presenter.router.openAuth(token)
                        setProgress(false)
                    } catch (e: UserRecoverableAuthException) {
                        startActivityForResult(e.intent, RC_SIGN_IN)
                    } catch (e: Exception) {
                        setProgress(false)
                    }
                }.start()
            } else setProgress(false)
        }
    }

}