
import 'camera_x_platform_interface.dart';

class Camera_x {
  Future<String?> getPlatformVersion() {
    return Camera_xPlatform.instance.getPlatformVersion();
  }
}
