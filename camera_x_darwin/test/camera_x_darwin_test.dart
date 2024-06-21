import 'package:flutter_test/flutter_test.dart';
import 'package:camera_x_darwin/camera_x_darwin.dart';
import 'package:camera_x_darwin/camera_x_darwin_platform_interface.dart';
import 'package:camera_x_darwin/camera_x_darwin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockCameraXDarwinPlatform
    with MockPlatformInterfaceMixin
    implements CameraXDarwinPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final CameraXDarwinPlatform initialPlatform = CameraXDarwinPlatform.instance;

  test('$MethodChannelCameraXDarwin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelCameraXDarwin>());
  });

  test('getPlatformVersion', () async {
    CameraXDarwin cameraXDarwinPlugin = CameraXDarwin();
    MockCameraXDarwinPlatform fakePlatform = MockCameraXDarwinPlatform();
    CameraXDarwinPlatform.instance = fakePlatform;

    expect(await cameraXDarwinPlugin.getPlatformVersion(), '42');
  });
}
