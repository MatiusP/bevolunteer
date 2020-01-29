package org.dzedzich.volunteers.root.di

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Component
import org.dzedzich.volunteers.root.di.scopes.AppScope
import org.dzedzich.volunteers.root.di.scopes.ApplicationContext
import org.dzedzich.volunteers.root.di.scopes.FirebaseRemoteConfigInstance
import org.dzedzich.volunteers.utils.PreferenceManager
import retrofit2.Retrofit

/**
 * Created by aleksejskrobot on 22.03.17.
 */
@AppScope
@Component(modules = arrayOf(RestModule::class, FirebaseModule::class))
interface ApplicationComponent {

    @ApplicationContext fun context(): Context
    @FirebaseRemoteConfigInstance fun remote(): FirebaseRemoteConfig

    fun retrofit(): Retrofit
    fun preferencesManager(): PreferenceManager

}