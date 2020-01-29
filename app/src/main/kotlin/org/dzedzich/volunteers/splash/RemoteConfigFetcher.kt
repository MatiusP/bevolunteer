package org.dzedzich.volunteers.splash

import android.os.Handler
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

/**
 * Created by aleksejskrobot on 21.05.17.
 */
class RemoteConfigFetcher (
        private val view: SplashActivity,
        val remoteConfig: FirebaseRemoteConfig
    ) {

    private val cacheDuration = 3600L

    fun fetch(func: (Task<Void>) -> Unit) {
        Log.d("Fetching", "start fetch")
        Handler().postDelayed({remoteConfig.fetch(cacheDuration).addOnCompleteListener(view, func)}, 0)
    }
}