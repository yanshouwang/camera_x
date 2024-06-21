
import 'camera_x_darwin_platform_interface.dart';

class CameraXDarwin {
  Future<String?> getPlatformVersion() {
    return CameraXDarwinPlatform.instance.getPlatformVersion();
  }
}
