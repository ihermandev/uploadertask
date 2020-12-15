package i.herman.uploadertask.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by Illia Herman on 14.12.2020.
 */

private const val APP_PERMISSIONS_REQUEST = 10

internal class FragmentPermissionsDelegate(private val fragment: Fragment) {


    fun hasStoragePermission(): Boolean {
        val permissionCheckResultWrite = ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permissionCheckResultRead = ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permissionCheckResultWrite == PackageManager.PERMISSION_GRANTED &&
                permissionCheckResultRead == PackageManager.PERMISSION_GRANTED
    }

    fun requestAllPermissions() {
        fragment.requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            APP_PERMISSIONS_REQUEST
        )
    }
}