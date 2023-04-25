package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.NotificationMessage;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.request.RegisterRequest;
import com.anhtuan.bookapp.response.LoginResponse;
import com.anhtuan.bookapp.response.RegisterResponse;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.DeviceService;
import com.anhtuan.bookapp.service.base.FirebaseMessagingService;
import com.anhtuan.bookapp.service.base.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private FirebaseMessagingService firebaseMessagingService;
    private DeviceService deviceService;

    @GetMapping("/login")
    public ResponseEntity<Response> loginController(@RequestParam String email,
                                                    @RequestParam String password,
                                                    @RequestParam String ip){
        Response response = new Response();
        User user = userService.getUserByEmailAndPassword(email, password);
        if (user == null){
            response.setCode(102);
            response.setData(new LoginResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        userService.updateUserIpAndLoggedStatus(user.getId(), ip, true);
        response.setCode(100);
        response.setData(new LoginResponse(user.getId(), user.getRole()));
        System.out.println("client ip:  "+ip);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerController(@RequestBody RegisterRequest registerRequest){
        System.out.println(registerRequest.toString());
        Response response = new Response();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String role = "member";
        String name = registerRequest.getName();
        String ip = registerRequest.getIp();
        userService.insertUser(new User(email, password, role, name, "", ip, false,500));
        User user = userService.getUserByEmailAndPassword(email,password);

        response.setCode(100);
        response.setData(new RegisterResponse(user.getId(), user.getRole()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateAvatarImage")
    public ResponseEntity<Response> updateAvatarImage(@RequestParam String userId,
                                                    @RequestParam("image") MultipartFile image){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        byte[] fileData = null;
        try {
            fileData = image.getBytes();
            if (fileData == null){
                response.setCode(108);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            String filePath = Constant.AVATAR_IMAGE_STORAGE_PATH + userId +Constant.PNG;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(fileData);
            fos.close();
            userService.updateAvatarImageByUserId(userId, userId);
            response.setCode(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/checkExistUser")
    public ResponseEntity<Response> checkExistUser(@RequestParam String email){
        Response response = new Response();
        if (userService.getUserByEmail(email) != null){
            response.setCode(101);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<Response> getUserInfoController(@RequestParam String userId){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user != null) {
            response.setCode(100);
            response.setData(user);
        } else {
            response.setCode(106);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/logout")
    public ResponseEntity<Response> logout(@RequestParam String userId){
        Response response = new Response();
        userService.updateUserLoggedStatus(userId, false);
        deviceService.updateDeviceTokenByUserId(userId, "");
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/checkUserLogged")
    public ResponseEntity<Response> checkUserLogged(@RequestParam String ip){
        Response response = new Response();
        User user = userService.getUserByIp(ip);
        if (user == null || !user.getIsLogged()){
            response.setCode(103);
            response.setData(new User());
            System.out.println(response.getCode());
            System.out.println("check login:----" + user.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(100);
        response.setData(user);
        System.out.println(response.getCode());
        System.out.println("check login:----" + user.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "getAvatarImage", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getAvatarImage(@RequestParam String imageName) throws Exception{
        String filePath = Constant.AVATAR_IMAGE_STORAGE_PATH + imageName + Constant.PNG;
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }

    @PostMapping("updatePassword")
    public ResponseEntity<Response> updatePassword(@RequestParam String userId,
                                                   @RequestParam String password,
                                                   @RequestParam String newPassword){
        Response response = new Response();
        User user = userService.getUserByIdAndPassword(userId, password);
        if (user == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        userService.updatePasswordByUserId(userId, newPassword);
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("updateName")
    public ResponseEntity<Response> updateName(@RequestParam String userId,
                                                   @RequestParam String password,
                                                   @RequestParam String newName){
        Response response = new Response();
        User user = userService.getUserByIdAndPassword(userId, password);
        if (user == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        userService.updateNameByUserId(userId, newName);
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("sendNoti")
    public ResponseEntity<Response> sendNoti(@RequestBody NotificationMessage notificationMessage){
        Response response = new Response();
        response.setCode(firebaseMessagingService.sendNotificationByToken(notificationMessage));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
