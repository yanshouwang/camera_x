import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'camera_x_method_channel.dart';

abstract class Camera_xPlatform extends PlatformInterface {
  /// Constructs a Camera_xPlatform.
  Camera_xPlatform() : super(token: _token);

  static final Object _token = Object();

  static Camera_xPlatform _instance = MethodChannelCamera_x();

  /// The default instance of [Camera_xPlatform] to use.
  ///
  /// Defaults to [MethodChannelCamera_x].
  static Camera_xPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [Camera_xPlatform] when
  /// they register themselves.
  static set instance(Camera_xPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
