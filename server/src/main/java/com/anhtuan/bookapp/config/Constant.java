package com.anhtuan.bookapp.config;

public class Constant {
    public static final String BOOK_IMAGE_STORAGE_PATH = "C:/Book_app_project/server/book_image/";
    public static final String AVATAR_IMAGE_STORAGE_PATH = "C:/Book_app_project/server/avatar_image/";
    public static final String PNG = ".png";
    public static final String PDF_STORAGE_PATH = "C:/Book_app_project/server/pdf/";
    public static final String PDF = ".pdf";

    public static final String BUY_BOOK_NOTIFICATION_TITLE = "Thông báo mua sách";
    public static final String ADD_CHAPTER_NOTIFICATION_TITLE = "Thông báo chương mới";

    public class StatusPurchasedBook{
        public static final int PURCHASED = 1;
        public static final int NOT_PURCHASED = 0;
    }

    public class ReactUpBookRequest{
        public static final int REJECT = 0;
        public static final int ACCEPT = 1;
    }

    public class StatusBookRequestUp{
        public static final int REQUEST = 0;
        public static final int ACCEPTED = 1;
        public static final int REJECTED = -1;
    }
}


