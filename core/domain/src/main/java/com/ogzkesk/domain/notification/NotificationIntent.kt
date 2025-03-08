package com.ogzkesk.domain.notification

data class NotificationIntent(
    val requestCode: Int,
    val intentClass: Class<*>,
)
