package com.ogzkesk.domain.notification

import java.time.LocalDateTime

data class PushNotification(
    val title: String,
    val description: String? = null,
    val date: LocalDateTime = LocalDateTime.now(),
)
