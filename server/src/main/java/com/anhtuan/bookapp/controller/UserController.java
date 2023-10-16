package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.Utils;
import static com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.NotificationMessage;
import com.anhtuan.bookapp.domain.TransactionHistory;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.request.LoginGoolgeRequest;
import com.anhtuan.bookapp.domain.VerifyCode;
import com.anhtuan.bookapp.request.AuthenVerifyCodeRequest;
import com.anhtuan.bookapp.request.RegisterRequest;
import com.anhtuan.bookapp.response.LoginResponse;
import com.anhtuan.bookapp.response.RegisterResponse;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController{

    private UserService userService;
    private FirebaseMessagingService firebaseMessagingService;
    private DeviceService deviceService;
    private EmailService emailService;
    private VerifyCodeService verifyCodeService;
    private TransactionHistoryService transactionHistoryService;


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
        Response response = new Response();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String role = "member";
        String name = registerRequest.getName();
        String ip = registerRequest.getIp();

        User newUser = new User(email, password, role, name, "", ip, false,500);
        User user = userService.insertUser(newUser);

        response.setCode(100);
        response.setData(new RegisterResponse(user.getId(), user.getRole()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/google-login")
    public ResponseEntity<Response> googleLogin(@RequestBody LoginGoolgeRequest goolgeRequest){
        Response response = new Response();
        User user = userService.findUserLoginGoolge(goolgeRequest.getEmail(),true);
        if(user!=null && (user.getGoogleLogin()==null||!user.getGoogleLogin())){
            throw  new RuntimeException("User is existed without google login");
        }
        if(user!=null && user.getGoogleLogin()){
            userService.updateUserIpAndLoggedStatus(user.getId(), goolgeRequest.getIp(), true);
            response.setCode(100);
            response.setData(new LoginResponse(user.getId(), user.getRole()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        String email = goolgeRequest.getEmail();
        String password = goolgeRequest.getPassword();
        String role = "member";
        String name = goolgeRequest.getName();
        String ip = goolgeRequest.getIp();
        User saveUser=new User(email, "", role, name,goolgeRequest.getImg() , ip, false,500,true);
        userService.insertUser(saveUser);
        User newUser = userService.getUserByEmailAndPassword(email,password);
        response.setCode(100);
        response.setData(new RegisterResponse(newUser.getId(), newUser.getRole()));
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
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(100);
        response.setData(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @PostMapping("forgotPassword")
    public ResponseEntity<Response> forgotPassword(@RequestParam String email){
        Response response = new Response();
        User user = userService.findByEmailAndNotLoginGoogle(email);
        if (Objects.isNull(user)){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String code = Utils.getVerifyCode(6);
        long time = System.currentTimeMillis();
        verifyCodeService.addVerifyCode(new VerifyCode(user.getId(), email, VERIFY_CODE_TYPE.FORGOT_PASS, code, time));

        String text = Utils.mailForgotPassword(code);
        emailService.sendEmail(email, FORGOT_PASSWORD_SUBJECT, text);
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("authenVerifyCode")
    public ResponseEntity<Response> authenVerifyCode(@RequestBody AuthenVerifyCodeRequest request){
        Response response = new Response();
        String code = request.getCode();
        long time = System.currentTimeMillis() - MINUTE_15;
        int type = request.getType();
        VerifyCode verifyCode;

        verifyCode = verifyCodeService.
                findVerifyCodeByCodeAndEmailAndTypeAndTimeGreaterThan(code, request.getEmail(), type, time);
        if (Objects.isNull(verifyCode)){
            response.setCode(121);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setCode(100);
        response.setData(verifyCode.getUserId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("createNewPassword")
    public ResponseEntity<Response> createNewPassword(@RequestParam String userId,
                                                   @RequestParam String newPassword){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        userService.updatePasswordByUserId(userId, newPassword);
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("getBalanceChange")
    public ResponseEntity<Response> getBalanceChange(@RequestParam String userId){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<TransactionHistory> transactionList = transactionHistoryService.getTransactionHistoryUser(userId);
        response.setCode(100);
        response.setData(transactionList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
