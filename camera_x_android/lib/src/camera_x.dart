import 'package:camera_x_platform_interface/camera_x_platform_interface.dart';

import 'camera_controller.dart';
import 'preview_view.dart';

final class CameraXAndroidPlugin extends CameraXPlugin {
  static void registerWith() {
    CameraXPlugin.instance = CameraXAndroidPlugin();
  }

  @override
  CameraController createCameraController() {
    return AndroidCameraController();
  }

  @override
  PreviewView createPreviewView() {
    return AndroidPreviewView();
  }
}
