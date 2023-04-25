package com.anhtuan.bookapp.repository.customize;

public interface DeviceCustomizeRepository {
    void updateDeviceTokenByUserId(String userId, String deviceToken);
}
