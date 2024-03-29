package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import static com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.config.PaymentConfig;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.request.PaymentRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("payment")
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;
    private UserService userService;
    private NotificationService notificationService;
    private FirebaseMessagingService firebaseMessagingService;
    private DeviceService deviceService;
    private TransactionHistoryService transactionHistoryService;

    @PostMapping("createPayment")
    public ResponseEntity<Response> createPayment(Authentication authentication,
                                                  @RequestBody PaymentRequest requestParams) throws IOException{

        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        Payment payment = new Payment();

        payment.setUserId(userId);

        int point = requestParams.getPoint();
        payment.setPoint(point);
        int amount = point * 100000;

        Map<String,String> vnp_params = new HashMap<>();
        vnp_params.put("vnp_Version", PaymentConfig.VNP_VERSION);
        vnp_params.put("vnp_Command", PaymentConfig.VNP_COMMAND);
        vnp_params.put("vnp_TmnCode", PaymentConfig.VNP_TMN_CODE);
        vnp_params.put("vnp_Amount", String.valueOf(amount));
        payment.setMoney(point * 1000);
//        String bankCode = requestParams.getBankCode();
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnp_params.put("vnp_BankCode", bankCode);
//            payment.setBankCode(bankCode);
//        }

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String createDate = time.format(formatter);
        vnp_params.put("vnp_CreateDate",createDate);
        payment.setTransactionTime(createDate);

        vnp_params.put("vnp_CurrCode",PaymentConfig.VNP_CURR_CODE);
        payment.setUnit(PaymentConfig.VNP_CURR_CODE);

        vnp_params.put("vnp_IpAddr",PaymentConfig.IP_DEFAULT);
        vnp_params.put("vnp_Locale",PaymentConfig.VNP_LOCALE);

        vnp_params.put("vnp_OrderInfo", requestParams.getDescription());
        payment.setTransactionInfo(requestParams.getDescription());

        vnp_params.put("vnp_OrderType", PaymentConfig.VNP_ORDER_TYPE);
        vnp_params.put("vnp_ReturnUrl",PaymentConfig.VNP_RETURN_URL);

        Payment checkPayment = new Payment();
        String transactionId;
        do {
            transactionId = PaymentConfig.getOTP(12);
            checkPayment = paymentService.findPaymentByTransactionId(transactionId);
        } while (!Objects.isNull(checkPayment));
        vnp_params.put("vnp_TxnRef", transactionId);
        payment.setTransactionId(transactionId);

//		vnp_params.put("vnp_SecureHash",PaymentConfig.VNP_SECURE_HASH);

        List fieldName = new ArrayList(vnp_params.keySet());
        Collections.sort(fieldName);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator iterator = fieldName.iterator();
        while (iterator.hasNext()){
            String name = (String) iterator.next();
            String value = vnp_params.get(name);
            if ((value != null)&&(value.length()>0)){

                hashData.append(name);
                hashData.append("=");
                hashData.append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(name,StandardCharsets.US_ASCII.toString()));
                query.append("=");
                query.append(URLEncoder.encode(value, String.valueOf(StandardCharsets.US_ASCII)));

                if (iterator.hasNext()){
                    query.append("&");
                    hashData.append("&");
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.VNP_SECURE_HASH, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = PaymentConfig.VNP_PAYMENT_URL + "?" + queryUrl;
        payment.setStatus(TRANSACTION_STATUS.WAITING);
        paymentService.addPayment(payment);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(paymentUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("returnResponse")
    public ModelAndView returnResponse(@RequestParam String vnp_PayDate,
                                       @RequestParam String vnp_ResponseCode,
                                       @RequestParam String vnp_TxnRef){
        long time = System.currentTimeMillis();
        Payment payment = paymentService.findPaymentByTransactionId(vnp_TxnRef);
        ModelAndView modelAndView = new ModelAndView();
        if (Objects.isNull(payment)){
            modelAndView.setViewName("fail.html");
            return modelAndView;
        }

        int status = payment.getStatus();
        if (status != TRANSACTION_STATUS.WAITING){
            modelAndView.setViewName("warning.html");
            return modelAndView;
        }

        if (!vnp_ResponseCode.equals("00")){
            paymentService.updatePaymentByTransactionId(vnp_TxnRef, TRANSACTION_STATUS.REJECT, vnp_PayDate, time);
            modelAndView.setViewName("fail.html");
            return modelAndView;
        }

        User user = userService.getUserByUserId(payment.getUserId());

        paymentService.updatePaymentByTransactionId(vnp_TxnRef, TRANSACTION_STATUS.SUCCESS, vnp_PayDate, time);

        if (payment.getTransactionInfo().equals(ADD_POINT)){
            int pointPayment = payment.getPoint();
            int newPoint = user.getPoint() + pointPayment;
            userService.updatePointByUserId(user.getId(), pointPayment);

            TransactionHistory sellBookHis = new TransactionHistory(user.getId(), payment.getPoint(), newPoint, TRANSACTION_TYPE.RECHARGE_POINT, time);
            transactionHistoryService.addTransactionHistory(sellBookHis);

            String mess = Utils.messSuccessAddPoint(payment.getPoint());
            Notification notification = new Notification
                    (user.getId(), "", mess, false, time);
            notificationService.insertNotification(notification);

            List<Device> devices = deviceService.getDevicesByUserId(user.getId());
            if (devices != null && !devices.isEmpty()){
                devices.forEach(device -> {
                    NotificationMessage message = new
                            NotificationMessage(device.getDeviceToken(), ADD_POINT_TITLE, mess);
                    firebaseMessagingService.sendNotificationByToken(message);
                });
            }
        }

        modelAndView.setViewName("success.html");
        modelAndView.addObject("point", payment.getPoint());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String formattedMoney = decimalFormat.format(payment.getMoney()) + " VND";
        modelAndView.addObject("money", formattedMoney);
        modelAndView.addObject("userName", user.getName());

        return modelAndView;
    }

}
