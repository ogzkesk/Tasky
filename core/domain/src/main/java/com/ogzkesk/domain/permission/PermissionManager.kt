package com.ogzkesk.domain.permission

interface PermissionManager {
    fun checkPermission(permission: String): Int

    fun checkPermissions(permissions: List<String>): Map<String, Int>
}
