import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'camera_x_darwin_platform_interface.dart';

/// An implementation of [CameraXDarwinPlatform] that uses method channels.
class MethodChannelCameraXDarwin extends CameraXDarwinPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('camera_x_darwin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
