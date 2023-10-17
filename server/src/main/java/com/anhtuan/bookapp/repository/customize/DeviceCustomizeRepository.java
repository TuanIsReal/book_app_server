package com.anhtuan.bookapp.repository.customize;

public interface DeviceCustomizeRepository {
    void updateUserIdByDeviceToken(String userId, String deviceToken);

}
