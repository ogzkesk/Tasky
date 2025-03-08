package com.ogzkesk.data.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.ogzkesk.domain.notification.NotificationIntent
import com.ogzkesk.domain.notification.NotificationRepository
import com.ogzkesk.domain.notification.PushNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class NotificationsRepositoryImpl(
    private val context: Context,
    private val notificationManager: NotificationManager,
//    private val logger: Logger,
//    private val firebaseMessaging: Any = TODO(),
//    private val coroutineScope: CoroutineScope = CoroutineScope(IO)
) : NotificationRepository<Notification> {
    private var notificationId: Int = 1
//    private val mutex = Mutex()

    override fun createChannel(
        id: String,
        name: String,
        description: String,
        importance: Int?,
    ) {
        NotificationChannel(
            id,
            name,
            importance ?: NotificationManager.IMPORTANCE_DEFAULT,
        ).apply { this.description = description }.let { channel ->
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * @param smallIcon - Small icon must be a drawable resource with opaque white background.
     * @param largeIcon - Large icon must be a png (vector & mipmap do not work).
     * @param color - Small icon color, otherwise system theme color will be used.
     * @param autoCancel - If set to true, notification is dismissed when clicked.
     */
    override fun showNotification(
        channelId: String,
        smallIcon: Int,
        largeIcon: Int?,
        title: String,
        text: String,
        intent: NotificationIntent?,
        autoCancel: Boolean,
        color: Int?,
    ) {
        val notification = createNotification(
            channelId,
            smallIcon,
            largeIcon,
            title,
            text,
            intent,
            autoCancel,
            color,
        )

        notificationManager.notify(
            notificationId++,
            notification,
        )
    }

    /**
     * @param smallIcon - Small icon must be a drawable resource with opaque white background.
     * @param largeIcon - Large icon must be a png (vector & mipmap do not work).
     * @param color - Small icon color, otherwise system theme color will be used.
     * @param autoCancel - If set to true, notification is dismissed when clicked.
     */
    override fun createNotification(
        channelId: String,
        smallIcon: Int,
        largeIcon: Int?,
        title: String,
        text: String,
        intent: NotificationIntent?,
        autoCancel: Boolean,
        color: Int?,
    ): Notification {
        return NotificationCompat.Builder(context, channelId)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setLargeIcon(
                largeIcon?.let {
                    BitmapFactory.decodeResource(context.resources, largeIcon)
                },
            )
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(autoCancel)
            .setContentIntent(
                intent?.let {
                    val classIntent = Intent(context, it.intentClass)
                    PendingIntent.getActivity(
                        context,
                        it.requestCode,
                        classIntent,
                        PendingIntent.FLAG_IMMUTABLE,
                    )
                },
            )
            .apply { color?.let { setColor(context.getColor(color)) } }
            .build()
    }

    /**
     * Subscribes to FCM topics.
     * @param topics A list of topic names to subscribe to.
     * @return A map of topic names to a boolean indicating whether the subscription was successful.
     */
    override suspend fun subscribeToNotificationTopics(topics: List<String>): MutableMap<String, Boolean> {
        val subscriptionResults = mutableMapOf<String, Boolean>()
//        firebaseMessaging?.let {
//            for (topic in topics) {
//                try {
//                    firebaseMessaging.subscribeToTopic(topic).await()
//                    subscriptionResults[topic] = true
//                    logger?.d(logTag, "subscribed to topics: $topic")
//                } catch (e: Exception) {
//                    subscriptionResults[topic] = false
//                    logger?.d(logTag, "couldn't subscribe to topic: $topic with error: $e")
//                }
//            }
//        }
        return subscriptionResults
    }

    override suspend fun getFirebaseMessagingToken(): String? {
//        return firebaseMessaging?.token?.await()
        return null
    }

    override fun streamNotifications(): Flow<List<PushNotification>> {
//        return notificationsCache?.stream() ?: emptyFlow()
        return emptyFlow()
    }

//    private suspend fun publishNotification(pushNotification: PushNotification) {
// //        notificationsCache?.stream()?.first()?.toMutableList()?.apply {
// //            mutex.withLock {
// //                add(notification)
// //                notificationsCache.publish(this)
// //            }
// //        }
//    }
}
