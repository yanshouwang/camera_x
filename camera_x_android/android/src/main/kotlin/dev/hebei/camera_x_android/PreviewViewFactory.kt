package dev.hebei.camera_x_android

import android.content.Context
import android.view.View
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class PreviewViewFactory(private val instanceManager: InstanceManager) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val identifier = args as Long
        val view = instanceManager.getInstance<View>(identifier)
        return object : PlatformView {
            override fun getView(): View? {
                return view
            }

            override fun dispose() {}
        }
    }
}