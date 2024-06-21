import 'package:flutter_test/flutter_test.dart';
import 'package:camera_x/camera_x.dart';
import 'package:camera_x/camera_x_platform_interface.dart';
import 'package:camera_x/camera_x_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockCamera_xPlatform
    with MockPlatformInterfaceMixin
    implements Camera_xPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final Camera_xPlatform initialPlatform = Camera_xPlatform.instance;

  test('$MethodChannelCamera_x is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelCamera_x>());
  });

  test('getPlatformVersion', () async {
    Camera_x camera_xPlugin = Camera_x();
    MockCamera_xPlatform fakePlatform = MockCamera_xPlatform();
    Camera_xPlatform.instance = fakePlatform;

    expect(await camera_xPlugin.getPlatformVersion(), '42');
  });
}
