package org.dzedzich.volunteers.push

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import androidx.core.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.haipclick.app.root.VolunteersApp
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.tasks.ui.views.impl.TaskActivity
import org.dzedzich.volunteers.utils.Constants
import rx.subscriptions.CompositeSubscription

class VolAppFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FSMservice"
    private val repository = PushRepository()
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        subscriptions.add(repository.refreshToken(token).subscribe({
            Log.d("firebase:token:result", "token updated")
        }, Throwable::printStackTrace))
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val pref = VolunteersApp.component.preferencesManager()

        Log.d(TAG, "From: " + remoteMessage!!.from)

        if (remoteMessage.data.isNotEmpty() && pref.getSwitcherSetting("setting#push")) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            val type = remoteMessage.data["type"]
            val taskId = remoteMessage.data["task_id"]?.toInt()
            val bundle = Bundle()

            bundle.putInt(Constants.BUNDLES.TASK_ID, taskId ?: 0)

            val intent = Intent(this, TaskActivity::class.java)

            intent.putExtras(bundle)
            sendNotification(
                    remoteMessage.notification?.title?: "",
                    remoteMessage.notification?.body ?: "",
                    intent
            )
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private fun sendNotification(title: String, message: String, intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}