package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.request.AddReCommentRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reComment")
@AllArgsConstructor
public class ReCommentController {
    private ReCommentService reCommentService;
    private CommentService commentService;
    private UserService userService;
    private NotificationService notificationService;
    private DeviceService deviceService;
    private FirebaseMessagingService firebaseMessagingService;

    @PostMapping("addReComment")
    public ResponseEntity<Response> addReComment(Authentication authentication,
                                                 @RequestBody AddReCommentRequest request){
        Response response = new Response();

        if (authentication.getPrincipal() == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();
        Comment comment = commentService.getCommentById(request.getParentCommentId());

        if (comment == null){
            response.setCode(116);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ReComment reComment = new ReComment(request);
        reComment.setAuthor(userId);
        reComment.setCommentTime(System.currentTimeMillis());
        reCommentService.insertReComment(reComment);
        commentService.updateTotalReCommentById(request.getParentCommentId(), comment.getTotalReComment()+1);

        String mess = Utils.messReplyCommentBook(userDetails.getUser().getName());
        Notification notification = new Notification
                (comment.getAuthor(), comment.getBookId(), mess, false, System.currentTimeMillis());
        notificationService.insertNotification(notification);

        List<Device> devices = deviceService.getDevicesByUserId(comment.getAuthor());
        if (devices != null && !devices.isEmpty()){
            devices.forEach(device -> {
                NotificationMessage message = new
                        NotificationMessage(device.getDeviceToken(), Constant.COMMENT_TITLE, mess);
                firebaseMessagingService.sendNotificationByToken(message);
            });
        }

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("getReCommentList")
    public ResponseEntity<Response> getReCommentList(@RequestParam String parentCommentId){
        Response response = new Response();
        List<ReComment> reComments = reCommentService.getReCommentsByParentCommentId(parentCommentId);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(reComments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
