package dev.hebei.camera_x_android

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.PluginRegistry

/** CameraXAndroidPlugin */
class CameraXAndroidPlugin : FlutterPlugin, ActivityAware {
    private var instanceManager: InstanceManager? = null
    private var permissionManager: PermissionManager? = null
    private var cameraControllerAPI: CameraControllerAPI? = null

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        val context = binding.applicationContext
        val binaryMessenger = binding.binaryMessenger
        val viewRegistry = binding.platformViewRegistry
        val finalizationListener = object : InstanceManager.FinalizationListener {
            override fun onFinalize(identifier: Long) {
                ObjectFlutterAPI(binaryMessenger).dispose(identifier) {}
            }
        }
        val instanceManager = InstanceManager.create(finalizationListener)
        val permissionManager = PermissionManager()
        this.permissionManager = permissionManager
        this.instanceManager = instanceManager
        val instanceManagerAPI = InstanceManagerAPI(instanceManager)
        InstanceManagerHostAPI.setUp(binaryMessenger, instanceManagerAPI)
        val objectAPI = ObjectAPI(instanceManager)
        ObjectHostAPI.setUp(binaryMessenger, objectAPI)
        val cameraControllerAPI = CameraControllerAPI(context, instanceManager, permissionManager)
        this.cameraControllerAPI = cameraControllerAPI
        CameraControllerHostAPI.setUp(binaryMessenger, cameraControllerAPI)
        val previewViewAPI = PreviewViewAPI(context, instanceManager)
        PreviewViewHostAPI.setUp(binaryMessenger, previewViewAPI)
        val viewFactory = PreviewViewFactory(instanceManager)
        viewRegistry.registerViewFactory("hebei.dev/PreviewView", viewFactory)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        val instanceManager = this.instanceManager
        if (instanceManager != null) {
            instanceManager.stopFinalizationListener()
            this.instanceManager = null
        }
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        permissionManager?.activity = binding.activity
        permissionManager?.registry = binding::addRequestPermissionsResultListener
        cameraControllerAPI?.activity = binding.activity
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        permissionManager?.activity = null
        permissionManager?.registry = null
        cameraControllerAPI?.activity = null

    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }
}
