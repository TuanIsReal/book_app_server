package com.anhtuan.bookapp.config;

public class Constant {
    public static final String BOOK_IMAGE_STORAGE_PATH = "C:/Book_app_project/server/book_image/";
    public static final String AVATAR_IMAGE_STORAGE_PATH = "C:/Book_app_project/server/avatar_image/";
    public static final String PNG = ".png";
    public static final String PDF_STORAGE_PATH = "C:/Book_app_project/server/pdf/";
    public static final String PDF = ".pdf";

    public static final String BUY_BOOK_NOTIFICATION_TITLE = "Thông báo mua truyên";
    public static final String ADD_CHAPTER_NOTIFICATION_TITLE = "Thông báo chương mới";
    public static final String BOOK_REQUEST_UP_TITLE = "Thông báo yêu cầu đăng truyện";
    public static final String COMMENT_TITLE = "Thông báo bình luận";
    public static final String ADD_POINT_TITLE = "Thông báo thêm Point";
    public static final String ADMIN_ID = "63fccae5fb208813af311df2";
    public static final int FILTER_ALL = 0;
    public static final String ADD_POINT = "addPoint";
    public static final String FORGOT_PASSWORD_SUBJECT = "Truyện AT - Xác nhận quên mật khẩu";
    public static final String VERIFY_EMAIL_SUBJECT = "Truyện AT - Xác thực tài khoản";
    public static final long MINUTE_15 = 1000*60*15;

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

    public class TYPE_FILTER{
        public static final int NEW_BOOK = 1;
        public static final int RECOMMEND_BOOK = 2;
        public static final int MOST_BUY = 3;
        public static final int MOST_REVIEW = 4;
    }

    public class FILTER_STATUS{
        public static final int WRITING = 1;
        public static final int COMPLETE = 2;
    }

    public class FILTER_POST{
        public static final int USER_POST = 1;
        public static final int ADMIN_POST = 2;
    }

    public class FILTER_SORT{
        public static final int SORT_BY_TIME = 1;
        public static final int SORT_BY_PURCHASED = 2;
        public static final int SORT_BY_REVIEW = 3;
        public static final int SORT_BY_STAR = 4;
        public static final int SORT_BY_CHAPTER = 5;
        public static final int SORT_BY_PRICE = 6;
    }

    public class TRANSACTION_STATUS{
        public static final int SUCCESS = 0;
        public static final int WAITING = 1;
        public static final int REJECT = 2;
    }

    public class VERIFY_CODE_TYPE{
        public static final int FORGOT_PASS = 1;
        public static final int VERIFY_EMAIL = 2;
    }
}


