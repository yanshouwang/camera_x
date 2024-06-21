package dev.hebei.camera_x_android

class ObjectAPI(private val instanceManager: InstanceManager) : ObjectHostAPI {
    override fun dispose(identifier: Long) {
        instanceManager.remove<Any>(identifier)
    }
}