package com.ogzkesk.data.permission

import android.content.Context
import com.ogzkesk.domain.permission.PermissionManager

class PermissionManagerImpl(
    private val context: Context,
) : PermissionManager {
    override fun checkPermission(permission: String): Int {
        return context.checkSelfPermission(permission)
    }

    override fun checkPermissions(permissions: List<String>): Map<String, Int> {
        return mutableMapOf<String, Int>().apply {
            permissions.forEach { permission ->
                this[permission] = checkPermission(permission)
            }
        }
    }
}
