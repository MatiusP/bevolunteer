package org.dzedzich.volunteers.push

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import rx.subscriptions.CompositeSubscription


/**
 * Created by aleksejskrobot on 03.06.17.
 */
class VolAppFirebaseInstanceIdService {
/*
    private val repository = PushRepository()
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("firebase:token", "Refreshed token: $refreshedToken")

        subscriptions.add(repository.refreshToken(refreshedToken).subscribe({
            Log.d("firebase:token:result", "token updated")
        }, Throwable::printStackTrace))
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }*/

}