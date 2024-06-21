package dev.hebei.camera_x_android

import android.app.Activity
import android.content.Context
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.LifecycleOwner


class CameraControllerAPI(private val context: Context, private val instanceManager: InstanceManager, private val permissionManager: PermissionManager) : CameraControllerHostAPI {
    var activity: Activity? = null

    override fun create(identifier: Long) {
        val controller = LifecycleCameraController(context)
        instanceManager.addDartCreatedInstance(controller, identifier)
    }

    override fun requestPermissions(callback: (Result<Boolean>) -> Unit) {
        permissionManager.requestPermissions(false) { errorCode, _ ->
            val granted = errorCode == null
            callback(Result.success(granted))
        }
    }

    override fun bindToLifecycle(identifier: Long) {
        val controller = instanceManager.getInstance<LifecycleCameraController>(identifier) ?: throw IllegalArgumentException()
        val lifecycleOwner = activity as LifecycleOwner
        controller.bindToLifecycle(lifecycleOwner)
    }

    override fun unbind(identifier: Long) {
        val controller = instanceManager.getInstance<LifecycleCameraController>(identifier) ?: throw IllegalArgumentException()
        controller.unbind()
    }

    override fun setCameraSelector(identifier: Long, cameraSelectorArgs: CameraSelectorArgs) {
        val controller = instanceManager.getInstance<CameraController>(identifier) ?: throw IllegalArgumentException()
        controller.cameraSelector = cameraSelectorArgs.toCameraSelector()
    }
}