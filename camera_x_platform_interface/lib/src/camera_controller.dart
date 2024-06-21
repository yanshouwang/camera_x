import 'camera_selector.dart';

abstract interface class CameraController {
  Future<bool> requestPermissions();
  Future<void> bind();
  Future<void> unbind();
  Future<void> setCameraSelector(CameraSelector cameraSelector);
}
