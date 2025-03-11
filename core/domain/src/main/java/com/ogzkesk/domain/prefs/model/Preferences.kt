package com.ogzkesk.domain.prefs.model

import kotlinx.serialization.Serializable

@Serializable
data class Preferences(
    val theme: Theme = Theme.DEFAULT,
)
