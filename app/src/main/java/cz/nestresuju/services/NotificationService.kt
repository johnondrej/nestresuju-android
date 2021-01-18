package cz.nestresuju.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import cz.nestresuju.R
import cz.nestresuju.model.entities.api.notifications.NotificationConstants
import cz.nestresuju.model.repositories.FirebaseTokenRepository
import cz.nestresuju.router.RouterActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.random.Random

/**
 * Service for handling incoming notifications.
 */
class NotificationService : FirebaseMessagingService() {

    private val koinComponent = object : KoinComponent {}

    private val firebaseTokenRepository: FirebaseTokenRepository by koinComponent.inject()

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let { notification ->
            val clickIntent = Intent(this, RouterActivity::class.java).apply {
                message.data[NotificationConstants.KEY_CLICK_ACTION]?.let { clickAction ->
                    putExtra(NotificationConstants.KEY_CLICK_ACTION, clickAction)
                }
            }
            val pendingIntent = PendingIntent.getActivity(this, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val uiNotification = NotificationCompat.Builder(applicationContext, getString(R.string.notification_channel_id))
                .setChannelId(getString(R.string.notification_channel_id))
                .setColor(getColor(R.color.primaryDark))
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(Random.nextInt(), uiNotification)
        }
    }

    override fun onNewToken(token: String) {
        firebaseTokenRepository.onNewFirebaseToken(token)
    }
}