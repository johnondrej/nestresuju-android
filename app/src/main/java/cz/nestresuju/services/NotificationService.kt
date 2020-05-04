package cz.nestresuju.services

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import cz.nestresuju.R
import cz.nestresuju.model.repositories.FirebaseTokenRepository
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
            val uiNotification = NotificationCompat.Builder(applicationContext, getString(R.string.notification_channel_id))
                .setChannelId(getString(R.string.notification_channel_id))
                .setColor(getColor(R.color.primaryDark))
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setSmallIcon(R.drawable.ic_notification)
                .build()

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(Random.nextInt(), uiNotification)
        }
    }

    override fun onNewToken(token: String) {
        firebaseTokenRepository.onNewFirebaseToken(token)
    }
}