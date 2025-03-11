package com.ogzkesk.domain.prefs.model

import kotlinx.serialization.Serializable

@Serializable
data class Theme(
    val mode: Mode,
    val dynamicColor: Boolean,
    val primaryColorHex: ULong?
) {
    enum class Mode {
        System,
        Dark,
        Light;
    }

    companion object {
        val DEFAULT = Theme(
            mode = Mode.System,
            dynamicColor = false,
            primaryColorHex = null
        )
    }
}
