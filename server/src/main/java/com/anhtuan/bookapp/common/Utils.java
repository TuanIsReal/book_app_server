package com.anhtuan.bookapp.common;

import java.util.Random;

public class Utils {
    public static String messageBodyBuyBook(String name, String bookName){
        return name + " vừa mua quyển truyện " + bookName + " của bạn ^^";
    }

    public static String messBodyAddChapter(String bookName, int numChapter, String chapterName){
        return "Quyển truyện " + bookName + " đã thêm chương mới!!!\n" + "Chương " + numChapter + ": " + chapterName;
    }

    public static final String messSuccessUploadBook(String bookName){
        return "Quyển truyện " + bookName + " đã được chấp thuận để được đăng lên hệ thống!!!!\nMau vào để thêm chương cho truyện thôi";
    }

    public static final String messRejectUploadBook(String bookName){
        return "Quyển truyện " + bookName + " đã bị từ chối  đăng lên hệ thống :( Hãy thử đăng lại truyện khác nhé ^^";
    }

    public static final String messReplyCommentBook(String userName){
        return userName + " đã phản hồi bình luận của bạn";
    }

    public static final String messSuccessAddPoint(int point){
        return "Bạn đã nạp thêm thành công " + point + " point vào tài khoản!";
    }

    public static final String mailForgotPassword(String code){
        return "Mã xác nhận mật khẩu của bạn là : " + code + " \nMã xác nhận có hiệu lực trong vòng 15 phút. Bạn vui lòng quay lại app để xác thực";
    }

    public static final String textVerifyEmail(String code){
        return "Mã xác nhận để xác thực tài khoản của bạn là : " + code + " \nMã xác nhận có hiệu lực trong vòng 15 phút. Bạn vui lòng quay lại app để xác thực";
    }

    public static final String warningChapter(int chapterNumber, String bookName){
        return "Chương " + chapterNumber + " của truyện " + bookName + " đã bị xóa do hệ thống phát hiện đạo văn";
    }

    public static String getVerifyCode(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String byteToString(byte[] b) {
        int n = b.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append((char) b[i]);
        }
        return builder.toString();
    }
}
