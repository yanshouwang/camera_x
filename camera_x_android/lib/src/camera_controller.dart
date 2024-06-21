import 'package:camera_x_platform_interface/camera_x_platform_interface.dart';
import 'package:flutter/foundation.dart';

import 'instance_manager.dart';
import 'object.dart';
import 'pigeon.dart';
import 'pigeon.g.dart';

class AndroidCameraController extends AndroidObject
    implements CameraController {
  static final _api = CameraControllerHostAPI();

  AndroidCameraController() : super.detached() {
    final identifier = InstanceManager.instance.addDartCreatedInstance(this);
    _api.create(identifier);
  }

  @protected
  AndroidCameraController.detached() : super.detached();

  @override
  AndroidCameraController copy() {
    return AndroidCameraController.detached();
  }

  @override
  Future<bool> requestPermissions() async {
    final granted = await _api.requestPermissions();
    return granted;
  }

  @override
  Future<void> bind() async {
    await _api.bindToLifecycle(identifier);
  }

  @override
  Future<void> unbind() async {
    await _api.unbind(identifier);
  }

  @override
  Future<void> setCameraSelector(CameraSelector cameraSelector) async {
    final cameraSelectorArgs = cameraSelector.toArgs();
    await _api.setCameraSelector(identifier, cameraSelectorArgs);
  }
}
