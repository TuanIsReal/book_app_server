package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.domain.Notification;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.NotificationService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("notification")
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;
    private UserService userService;

    @GetMapping("getNotification")
    public ResponseEntity<Response> getNotification(@RequestParam String userId){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<Notification> notificationList = notificationService.getNotificationByUserId(userId);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(notificationList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("clickNotification")
    public ResponseEntity<Response> clickNotification(@RequestParam String id){
        Response response = new Response();
        notificationService.updateIsClickById(id, true);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
