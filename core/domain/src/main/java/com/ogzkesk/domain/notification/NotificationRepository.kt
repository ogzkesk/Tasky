package com.ogzkesk.domain.notification

import kotlinx.coroutines.flow.Flow

interface NotificationRepository<N> {
    fun createChannel(
        id: String,
        name: String,
        description: String,
        importance: Int? = null,
    )

    @Suppress("LongParameterList")
    fun showNotification(
        channelId: String,
        smallIcon: Int,
        largeIcon: Int? = null,
        title: String,
        text: String,
        intent: NotificationIntent? = null,
        autoCancel: Boolean = false,
        color: Int? = null,
    )

    @Suppress("LongParameterList")
    fun createNotification(
        channelId: String,
        smallIcon: Int,
        largeIcon: Int? = null,
        title: String,
        text: String,
        intent: NotificationIntent? = null,
        autoCancel: Boolean = false,
        color: Int? = null,
    ): N

    suspend fun subscribeToNotificationTopics(topics: List<String>): MutableMap<String, Boolean>

    suspend fun getFirebaseMessagingToken(): String?

    fun streamNotifications(): Flow<List<PushNotification>>
}
