package org.dzedzich.volunteers.main.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import com.google.android.material.tabs.TabLayout
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.view_main.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.main.di.DaggerMainComponent
import org.dzedzich.volunteers.main.di.MainModule
import org.dzedzich.volunteers.push.VolAppFirebaseInstanceIdService
import org.dzedzich.volunteers.push.VolAppFirebaseMessagingService
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.utils.Constants
import org.jetbrains.anko.find
import javax.inject.Inject
import android.net.Uri
import com.google.android.gms.tasks.OnCompleteListener


/**
 * Created by alexscrobot on 11.05.17.
 */
class MainActivity: BaseActivity(), IMainView {

    override fun provideTabLayout(): TabLayout {
        return find(R.id.rating_tab)
    }

    @Inject lateinit var presenter: MainPresenter
    var extras: Bundle? = null

    init {
        DaggerMainComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_main)

        bottomBar.setOnTabSelectListener(presenter)

        presenter.bindView(this)

        toolbarPreference()

        startService(Intent(this, VolAppFirebaseInstanceIdService::class.java))
        startService(Intent(this, VolAppFirebaseMessagingService::class.java))

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    presenter.refreshTheToken(task.result?.token)
                })
    }

    fun checkBattery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        }
    }

    override fun toolbarPreference() {
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
            when(supportFragmentManager.findFragmentById(R.id.contentContainer)?.tag) {
                Constants.ROUTER.MAIN.ACCOUNT ->  bottomBar.selectTabAtPosition(3)
                Constants.ROUTER.MAIN.FEED -> bottomBar.selectTabAtPosition(0)
                Constants.ROUTER.MAIN.RATING -> bottomBar.selectTabAtPosition(2)
                Constants.ROUTER.MAIN.TASKS -> bottomBar.selectTabAtPosition(1)
            }
    }

    override fun initPoint() {
        extras = intent.extras
        val fromReg = extras?.getBoolean(Constants.BUNDLES.FROM_CONFIRM, false)

        if(fromReg != null && fromReg) {
            bottomBar.selectTabWithId(R.id.tab_account)
        } else {
            bottomBar.selectTabWithId(R.id.tab_feed)
        }
    }
}