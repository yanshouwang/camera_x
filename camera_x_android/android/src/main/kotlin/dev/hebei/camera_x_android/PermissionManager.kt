package dev.hebei.camera_x_android

import android.Manifest.permission
import android.app.Activity
import android.content.pm.PackageManager
import androidx.annotation.VisibleForTesting
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener

typealias PermissionsRegistry = (listener: RequestPermissionsResultListener) -> Unit
typealias PermissionsResultCallback = (errorCode: String?, errorDescription: String?) -> Unit

class PermissionManager {
    companion object {
        /**
         * Camera access permission errors handled when camera is created. See `MethodChannelCamera`
         * in `camera/camera_platform_interface` for details.
         */
        private const val CAMERA_PERMISSIONS_REQUEST_ONGOING = "CameraPermissionsRequestOngoing"
        private const val CAMERA_PERMISSIONS_REQUEST_ONGOING_MESSAGE = "Another request is ongoing and multiple requests cannot be handled at once."
        private const val CAMERA_ACCESS_DENIED = "CameraAccessDenied"
        private const val CAMERA_ACCESS_DENIED_MESSAGE = "Camera access permission was denied."
        private const val AUDIO_ACCESS_DENIED = "AudioAccessDenied"
        private const val AUDIO_ACCESS_DENIED_MESSAGE = "Audio access permission was denied."
        private const val CAMERA_REQUEST_ID = 9796
    }

    var activity: Activity? = null
    var registry: PermissionsRegistry? = null

    @VisibleForTesting
    var ongoing = false
    fun requestPermissions(enableAudio: Boolean, callback: PermissionsResultCallback) {
        val activity = this.activity
        val registry = this.registry
        if (activity == null || registry == null) {
            throw IllegalStateException("Activity must be set to request permissions.")
        }
        if (ongoing) {
            callback(CAMERA_PERMISSIONS_REQUEST_ONGOING, CAMERA_PERMISSIONS_REQUEST_ONGOING_MESSAGE)
            return
        }
        if (!hasCameraPermission(activity) || enableAudio && !hasAudioPermission(activity)) {
            val listener = RequestPermissionsResultListener { requestCode, _, grantResults ->
                if (requestCode != CAMERA_REQUEST_ID) {
                    return@RequestPermissionsResultListener false
                }
                ongoing = false
                // grantResults could be empty if the permissions request with the user is interrupted
                // https://developer.android.com/reference/android/app/Activity#onRequestPermissionsResult(int,%20java.lang.String[],%20int[])
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    callback(CAMERA_ACCESS_DENIED, CAMERA_ACCESS_DENIED_MESSAGE)
                } else if (grantResults.size > 1 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    callback(AUDIO_ACCESS_DENIED, AUDIO_ACCESS_DENIED_MESSAGE)
                } else {
                    callback(null, null)
                }
                return@RequestPermissionsResultListener true
            }
            registry(listener)
            ongoing = true
            val permissions = if (enableAudio) arrayOf(permission.CAMERA, permission.RECORD_AUDIO)
            else arrayOf(permission.CAMERA)
            ActivityCompat.requestPermissions(activity, permissions, CAMERA_REQUEST_ID)
        } else {
            // Permissions already exist. Call the callback with success.
            callback(null, null)
        }
    }

    private fun hasCameraPermission(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasAudioPermission(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }
}
