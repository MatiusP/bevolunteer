package com.haipclick.app.root

import android.content.Context
import androidx.multidex.MultiDexApplication
import org.dzedzich.volunteers.root.di.ApplicationComponent
import org.dzedzich.volunteers.root.di.ContextModule
import org.dzedzich.volunteers.root.di.DaggerApplicationComponent
import org.dzedzich.volunteers.root.di.FirebaseModule
import org.dzedzich.volunteers.utils.Constants
import timber.log.BuildConfig
import timber.log.Timber

class VolunteersApp : MultiDexApplication() {

    companion object {
        lateinit var component: ApplicationComponent
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, Constants.LOGTAG, message, t)
                }
            })
        }

        VolunteersApp.component = DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this))
                .firebaseModule(FirebaseModule())
                .build()

        context = applicationContext
    }

}