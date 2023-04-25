package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Device;

import java.util.List;

public interface DeviceService {
    void insertDevice(Device device);
    Device getDeviceByUserId(String userId);
    void updateDeviceTokenByUserId(String userId, String deviceToken);
    List<Device> getDevicesByUserIdIsIn(List<String> userIdList);
}
