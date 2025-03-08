package com.ogzkesk.domain.model

data class Task(
    val id: Long,
    val title: String,
    val description: String?,
    val priority: Priority,
    val date: Long,
) {
    enum class Priority(val colorHex: Long) {
        LOW(0xFF2AC489),
        MEDIUM(0xFFFFB12A),
        HIGH(0xFFE64B3C);
    }
}
