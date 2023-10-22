package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Device;
import com.anhtuan.bookapp.repository.customize.DeviceCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceRepository extends MongoRepository<Device, String>, DeviceCustomizeRepository {
    List<Device> findDevicesByUserId(String userId);
    List<Device> findDevicesByUserIdIsIn(List<String> userIdList);
    Device findDeviceByDeviceToken(String deviceToken);
    void deleteDevicesByUserId(String userId);
}
