import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'camera_x_platform_interface.dart';

/// An implementation of [Camera_xPlatform] that uses method channels.
class MethodChannelCamera_x extends Camera_xPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('camera_x');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
