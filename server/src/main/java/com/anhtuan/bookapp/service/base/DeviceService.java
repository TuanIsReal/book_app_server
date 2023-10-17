package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Device;

import java.util.List;

public interface DeviceService {
    void insertDevice(Device device);
    Device getDeviceByUserId(String userId);
    void updateUserIdByDeviceToken(String userId, String deviceToken);
    List<Device> getDevicesByUserIdIsIn(List<String> userIdList);
    List<Device> getDevicesByDeviceToken(String deviceToken);
    void removeDevicesByUserId(String userId);
}
