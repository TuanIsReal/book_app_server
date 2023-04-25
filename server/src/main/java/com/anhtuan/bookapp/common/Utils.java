package com.anhtuan.bookapp.common;

public class Utils {
    public static String messageBodyBuyBook(String name, String bookName){
        return name + " vừa mua quyển truyện " + bookName + " của bạn ^^";
    }

    public static String messBodyAddChapter(String bookName, int numChapter, String chapterName){
        return "Quyển truyện " + bookName + " đã thêm chương mới!!!\n" + "Chương " + numChapter + ": " + chapterName;
    }
}
