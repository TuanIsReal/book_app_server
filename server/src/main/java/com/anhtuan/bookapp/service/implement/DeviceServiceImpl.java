package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.Device;
import com.anhtuan.bookapp.repository.base.DeviceRepository;
import com.anhtuan.bookapp.service.base.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;


    @Override
    public void insertDevice(Device device) {
        deviceRepository.insert(device);
    }

    @Override
    public Device getDeviceByUserId(String userId) {
        return deviceRepository.findDeviceByUserId(userId);
    }

    @Override
    public void updateUserIdByDeviceToken(String userId, String deviceToken) {
        deviceRepository.updateUserIdByDeviceToken(userId, deviceToken);
    }

    @Override
    public List<Device> getDevicesByUserIdIsIn(List<String> userIdList) {
        return deviceRepository.findDevicesByUserIdIsIn(userIdList);
    }

    @Override
    public List<Device> getDevicesByDeviceToken(String deviceToken) {
        return deviceRepository.findDevicesByDeviceToken(deviceToken);
    }

    @Override
    public void removeDevicesByUserId(String userId) {
        deviceRepository.deleteDevicesByUserId(userId);
    }

}
