package com.ogzkesk.domain.model

data class Task(
    val title: String,
    val description: String?,
    val priority: Priority,
    val isCompleted: Boolean,
    val isDeleted: Boolean,
    val createdAt: Long,
    val id: Long = 0,
) {
    enum class Priority {
        LOW,
        MEDIUM,
        HIGH;
    }

    companion object {
        val EMPTY = Task(
            title = "",
            description = null,
            priority = Priority.LOW,
            isCompleted = false,
            isDeleted = false,
            createdAt = 0,
        )
    }
}
