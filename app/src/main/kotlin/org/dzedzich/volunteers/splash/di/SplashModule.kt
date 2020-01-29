package org.dzedzich.volunteers.splash.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.root.di.scopes.FirebaseRemoteConfigInstance
import org.dzedzich.volunteers.splash.RemoteConfigFetcher
import org.dzedzich.volunteers.splash.SplashActivity
import org.dzedzich.volunteers.splash.SplashPresenter

/**
 * Created by aleksejskrobot on 21.05.17.
 */
@Module
class SplashModule(private val view: SplashActivity) {

    @Provides
    @SplashScope
    fun presenter(fetcher: RemoteConfigFetcher): SplashPresenter {
        return SplashPresenter(fetcher)
    }

    @Provides
    @SplashScope
    fun fetcher(@FirebaseRemoteConfigInstance remote: FirebaseRemoteConfig): RemoteConfigFetcher {
        return RemoteConfigFetcher(view, remote)
    }

}