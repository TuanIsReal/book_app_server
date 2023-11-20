package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.cache.UserInfoManager;
import com.anhtuan.bookapp.common.CustomPasswordEncode;
import com.anhtuan.bookapp.common.JwtTokenProvider;
import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import static com.anhtuan.bookapp.config.Constant.*;

import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.request.LoginGoogleRequest;
import com.anhtuan.bookapp.request.AuthenVerifyCodeRequest;
import com.anhtuan.bookapp.request.RegisterRequest;
import com.anhtuan.bookapp.response.LoginResponse;
import com.anhtuan.bookapp.response.RegisterResponse;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import com.anhtuan.bookapp.service.implement.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController{

    private UserService userService;
    private UserInfoService userInfoService;
    private DeviceService deviceService;
    private EmailService emailService;
    private VerifyCodeService verifyCodeService;
    private TransactionHistoryService transactionHistoryService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private final UserInfoManager userInfoManager;
    private final STFService stfService;


    @GetMapping("/login")
    public ResponseEntity<Response> loginController(@RequestParam String email,
                                                    @RequestParam String password,
                                                    @RequestParam String ip){
        Response response = new Response();
        User user = userService.getUserByEmail(email);
        if (user == null){
            response.setCode(ResponseCode.ACCOUNT_NOT_EXISTS);
            response.setData(new LoginResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (user.getStatus() == USER_STATUS.BLOCK){
            response.setCode(ResponseCode.ACCOUNT_IS_BLOCKED);
            response.setData(new LoginResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);
        userService.updateUserIpAndLoggedStatus(user.getId(), ip);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(new LoginResponse(token, refreshToken, userDetails.getUser().getRole()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reLogin")
    public ResponseEntity<Response> reLogin(@RequestParam String email,
                                            @RequestParam String password){
        Response response = new Response();

        User user = userService.getUserByEmail(email);
        if (user == null){
            response.setCode(ResponseCode.ACCOUNT_NOT_EXISTS);
            response.setData(new LoginResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        CustomUserDetails userDetails;
        if (user.getIsGoogleLogin() != null && user.getIsGoogleLogin()){
            userDetails = new CustomUserDetails(user);
        } else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            userDetails = (CustomUserDetails) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        if (user.getStatus() == USER_STATUS.BLOCK){
            response.setCode(ResponseCode.ACCOUNT_IS_BLOCKED);
            response.setData(new LoginResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String token = tokenProvider.generateToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(new LoginResponse(token, refreshToken, userDetails.getUser().getRole()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerController(@RequestBody RegisterRequest registerRequest){
        Response response = new Response();
        String email = registerRequest.getEmail();

        if (userService.getUserByEmail(email) != null){
            response.setCode(ResponseCode.EMAIL_EXISTS);
            return ResponseEntity.ok(response);
        }

        String password = registerRequest.getPassword();
        String encryptPassword = CustomPasswordEncode.encryptPassword(password);
        Integer role = USER_ROLE.USER;
        String name = registerRequest.getName();
        String ip = registerRequest.getIp();

        User newUser = new User(email, encryptPassword, role, name, "", ip, USER_STATUS.LOGIN,500);
        User user = userService.insertUser(newUser);
        userInfoManager.addUser(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = tokenProvider.generateToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(new RegisterResponse(token, refreshToken, user.getRole()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/google-login")
    public ResponseEntity<Response> googleLogin(@RequestBody LoginGoogleRequest googleRequest){
        Response response = new Response();
        User user = userService.findUserLoginGoolge(googleRequest.getEmail(),true);
        if(user!=null && (user.getIsGoogleLogin()==null || !user.getIsGoogleLogin())){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            response.setData(new LoginResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if(user != null && user.getIsGoogleLogin()){
            if (user.getStatus() == USER_STATUS.BLOCK){
                response.setCode(ResponseCode.ACCOUNT_IS_BLOCKED);
                response.setData(new LoginResponse());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            userService.updateUserIpAndLoggedStatus(user.getId(), googleRequest.getIp());
            CustomUserDetails userDetails = new CustomUserDetails(user);
            String token = tokenProvider.generateToken(userDetails);
            String refreshToken = tokenProvider.generateRefreshToken(userDetails);

            response.setCode(ResponseCode.SUCCESS);
            response.setData(new LoginResponse(token, refreshToken, user.getRole()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        String email = googleRequest.getEmail();
        String name = googleRequest.getName();
        String ip = googleRequest.getIp();
        User newUser = new User(email, "", USER_ROLE.USER, name,googleRequest.getImg(), ip, USER_STATUS.LOGIN,500,true);
        User saveUser = userService.insertUser(newUser);
        userInfoManager.addUser(saveUser);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = tokenProvider.generateToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(new LoginResponse(token, refreshToken, saveUser.getRole()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/checkExistUser")
    public ResponseEntity<Response> checkExistUser(@RequestParam String email){
        Response response = new Response();
        if (userService.getUserByEmail(email) != null){
            response.setCode(ResponseCode.EMAIL_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<Response> getUserInfoController(@RequestParam String userId){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user != null) {
            response.setCode(ResponseCode.SUCCESS);
            response.setData(user);
        } else {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/checkUserInfo")
    public ResponseEntity<Response> checkUserInfo(Authentication authentication){
        Response response = new Response();
        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (user.getStatus() == USER_STATUS.BLOCK){
            response.setCode(ResponseCode.ACCOUNT_IS_BLOCKED);
            response.setData(new LoginResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setCode(ResponseCode.SUCCESS);
        response.setData(userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUsername")
    public ResponseEntity<Response> getUsername(@RequestParam String userId){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        response.setCode(100);
        response.setData(user.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/loginDevice")
    public ResponseEntity<Response> loginDevice(Authentication authentication,
                                                @RequestParam String deviceToken){
        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        Device devices = deviceService.getDeviceByDeviceToken(deviceToken);
        if (devices == null){
            deviceService.insertDevice(new Device(userId, deviceToken));
        } else {
            deviceService.updateUserIdByDeviceToken(userId, deviceToken);
        }

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/logout")
    public ResponseEntity<Response> logout(Authentication authentication){
        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        userService.updateUserStatus(userId, USER_STATUS.LOGOUT);
        deviceService.removeDevicesByUserId(userId);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("updatePassword")
    public ResponseEntity<Response> updatePassword(Authentication authentication,
                                                   @RequestParam String password,
                                                   @RequestParam String newPassword){
        Response response = new Response();
        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        String encryptPassword = CustomPasswordEncode.encryptPassword(password);
        if (!userDetails.getUser().getPassword().equals(encryptPassword)){
            response.setCode(ResponseCode.PASSWORD_IS_WRONG);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String encryptNewPassword = CustomPasswordEncode.encryptPassword(newPassword);
        userService.updatePasswordByUserId(userId, encryptNewPassword);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("updateName")
    public ResponseEntity<Response> updateName(Authentication authentication,
                                               @RequestParam String password,
                                               @RequestParam String newName){
        Response response = new Response();
        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        if (!userDetails.getUser().getPassword().equals(CustomPasswordEncode.encryptPassword(password))){
            response.setCode(ResponseCode.PASSWORD_IS_WRONG);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        userService.updateNameByUserId(userId, newName);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("forgotPassword")
    public ResponseEntity<Response> forgotPassword(@RequestParam String email){
        Response response = new Response();
        User user = userService.findByEmailAndNotLoginGoogle(email);
        if (Objects.isNull(user)){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String code = Utils.getVerifyCode(6);
        long time = System.currentTimeMillis();
        verifyCodeService.addVerifyCode(new VerifyCode(user.getId(), email, VERIFY_CODE_TYPE.FORGOT_PASS, code, time));

        String text = Utils.mailForgotPassword(code);
        emailService.sendEmail(email, FORGOT_PASSWORD_SUBJECT, text);
        response.setCode(ResponseCode.SUCCESS);
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
            response.setCode(ResponseCode.VERIFY_CODE_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setCode(ResponseCode.SUCCESS);
        response.setData(verifyCode.getUserId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("createNewPassword")
    public ResponseEntity<Response> createNewPassword(@RequestParam String userId,
                                                   @RequestParam String newPassword){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        String encryptPassword = CustomPasswordEncode.encryptPassword(newPassword);
        userService.updatePasswordByUserId(userId, encryptPassword);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("getBalanceChange")
    public ResponseEntity<Response> getBalanceChange(Authentication authentication){
        Response response = new Response();
        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        List<TransactionHistory> transactionList = transactionHistoryService.getTransactionHistoryUser(userId);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(transactionList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<Response> refreshToken(@RequestParam String refreshToken){;
        Response response = new Response();
        if (refreshToken == null || refreshToken.isEmpty() || !tokenProvider.validateRefreshToken(refreshToken)){
            response.setCode(ResponseCode.REFRESH_TOKEN_INVALID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String userId = tokenProvider.getUserIdFromRefreshToken(refreshToken);
        CustomUserDetails user = (CustomUserDetails) userInfoService.loadUserById(userId);
        String newToken = tokenProvider.generateToken(user);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(newToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getAllUser")
    @Secured("ADMIN")
    public ResponseEntity<Response> getAllUSer(){
        Response response = new Response();
        List<User> userList = userInfoManager.getAllUser();

        Map<String, String> userAvatarMap = stfService.getUserAvatarPathMap(userList);
        userList.stream()
                .filter(user -> userAvatarMap.containsKey(user.getId()))
                .forEach(user -> user.setAvatarImage(userAvatarMap.get(user.getId())));

        response.setCode(ResponseCode.SUCCESS);
        response.setData(userList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("blockUser")
    @Secured("ADMIN")
    public ResponseEntity<Response> blockUser(@RequestParam String userId){
        Response response = new Response();
        User user = userInfoManager.getUserByUserId(userId);
        if (user == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        userService.updateUserStatus(userId, USER_STATUS.BLOCK);
        deviceService.removeDevicesByUserId(userId);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("unBlockUser")
    @Secured("ADMIN")
    public ResponseEntity<Response> unBlockUser(@RequestParam String userId){
        Response response = new Response();
        User user = userInfoManager.getUserByUserId(userId);
        if (user == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        userService.updateUserStatus(userId, USER_STATUS.LOGOUT);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/ping")
    public ResponseEntity<Response> ping(){
        Response response = new Response();
        response.setCode(ResponseCode.SUCCESS);
        response.setData("pong");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
