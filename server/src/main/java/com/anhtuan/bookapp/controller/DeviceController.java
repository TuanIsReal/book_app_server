package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.domain.Device;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.DeviceService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("device")
@AllArgsConstructor
public class DeviceController {

    private DeviceService deviceService;
    private UserService userService;

    @PostMapping("loginDevice")
    public ResponseEntity<Response> loginDevice(@RequestParam String userId,
                                              @RequestParam String deviceToken){
        Response response = new Response();
        if (userService.getUserByUserId(userId) == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Device device = deviceService.getDeviceByUserId(userId);
        if (device == null){
            deviceService.insertDevice(new Device(userId, deviceToken));
        } else if (!device.getDeviceToken().equals(deviceToken)) {
            deviceService.updateDeviceTokenByUserId(userId, deviceToken);
        }

        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
