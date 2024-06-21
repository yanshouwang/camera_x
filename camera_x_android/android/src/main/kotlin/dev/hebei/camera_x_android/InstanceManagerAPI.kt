package dev.hebei.camera_x_android

class InstanceManagerAPI(private val instanceManager: InstanceManager) : InstanceManagerHostAPI {
    override fun clear() {
        instanceManager.clear()
    }
}