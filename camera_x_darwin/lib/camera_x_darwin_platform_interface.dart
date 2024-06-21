import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'camera_x_darwin_method_channel.dart';

abstract class CameraXDarwinPlatform extends PlatformInterface {
  /// Constructs a CameraXDarwinPlatform.
  CameraXDarwinPlatform() : super(token: _token);

  static final Object _token = Object();

  static CameraXDarwinPlatform _instance = MethodChannelCameraXDarwin();

  /// The default instance of [CameraXDarwinPlatform] to use.
  ///
  /// Defaults to [MethodChannelCameraXDarwin].
  static CameraXDarwinPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [CameraXDarwinPlatform] when
  /// they register themselves.
  static set instance(CameraXDarwinPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
