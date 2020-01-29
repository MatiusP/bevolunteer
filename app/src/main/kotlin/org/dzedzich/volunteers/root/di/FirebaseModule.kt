package org.dzedzich.volunteers.root.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.root.di.scopes.AppScope
import org.dzedzich.volunteers.root.di.scopes.FirebaseRemoteConfigInstance

/**
 * Created by aleksejskrobot on 21.05.17.
 */
@Module
class FirebaseModule {

    @Provides
    @AppScope
    @FirebaseRemoteConfigInstance
    fun remoteConfig(): FirebaseRemoteConfig {
        val config = FirebaseRemoteConfig.getInstance()
        config.setDefaults(R.xml.remote_config_defaults)
        return config
    }


}