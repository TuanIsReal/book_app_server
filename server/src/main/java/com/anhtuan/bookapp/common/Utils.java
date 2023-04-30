package com.anhtuan.bookapp.common;

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
}
