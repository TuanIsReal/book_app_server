package com.anhtuan.bookapp.config;

public class Constant {
    public static final String BOOK_IMAGE_STORAGE_PATH = "C:/inetpub/wwwroot/book_image/";
    public static final String THUMBNAIL_STORAGE_PATH = "C:/inetpub/wwwroot/thumbnail/";
    public static final String AVATAR_IMAGE_STORAGE_PATH = "C:/inetpub/wwwroot/avatar_image/";
    public static final String CHAPTER_TEXT_STORAGE_PATH = "C:/chapter/";
    public static final String JPG = ".jpg";
    public static final String TXT = ".txt";

    public static final String BOOK_IMAGE_STORAGE_PATH_RESPONSE = "/book_image/";
    public static final String THUMBNAIL_STORAGE_PATH_RESPONSE = "/thumbnail/";
    public static final String AVATAR_IMAGE_STORAGE_PATH_RESPONSE = "/avatar_image/";

    public static final String BUY_BOOK_NOTIFICATION_TITLE = "Thông báo mua truyên";
    public static final String ADD_CHAPTER_NOTIFICATION_TITLE = "Thông báo chương mới";
    public static final String BOOK_REQUEST_UP_TITLE = "Thông báo yêu cầu đăng truyện";
    public static final String COMMENT_TITLE = "Thông báo bình luận";
    public static final String ADD_POINT_TITLE = "Thông báo thêm Point";
    public static final String WARNING_CHAPTER_TITLE = "Thông báo vi phạm";
    public static final int FILTER_ALL = 0;
    public static final String ADD_POINT = "addPoint";
    public static final String FORGOT_PASSWORD_SUBJECT = "Truyện AT - Xác nhận quên mật khẩu";

    public static final String CHAPTER_BLOCK_CONTENT = "\n\n\n\n\nChương này bị khóa do bạn chưa mua truyện. Bạn vui lòng mua truyện để có thể tiếp tục theo dõi chương này và các chương về sau";

    public static final String VERIFY_EMAIL_SUBJECT = "Truyện AT - Xác thực tài khoản";
    public static final long MINUTE_15 = 1000*60*15;
    public static final long A_SECOND = 1000L;
    public static final long A_MINUTE = 60 * A_SECOND;
    public static final long AN_HOUR = 60 * A_MINUTE;
    public static final long A_DAY = 24 * AN_HOUR;

    public static class STATUS_PURCHASED_BOOK {
        public static final int PURCHASED = 1;
        public static final int NOT_PURCHASED = 0;
    }

    public static class REACT_UP_BOOK_REQUEST {
        public static final int REJECT = 0;
        public static final int ACCEPT = 1;
    }

    public static class STATUS_BOOK_REQUEST_UP {
        public static final int REQUEST = 0;
        public static final int ACCEPTED = 1;
        public static final int REJECTED = -1;
    }

    public static class TYPE_FILTER{
        public static final int NEW_BOOK = 1;
        public static final int RECOMMEND_BOOK = 2;
        public static final int MOST_BUY = 3;
        public static final int MOST_REVIEW = 4;
    }

    public static class FILTER_STATUS{
        public static final int WRITING = 1;
        public static final int COMPLETE = 2;
    }

    public static class FILTER_POST{
        public static final int USER_POST = 1;
        public static final int ADMIN_POST = 2;
    }

    public static class FILTER_SORT{
        public static final int SORT_BY_TIME = 1;
        public static final int SORT_BY_PURCHASED = 2;
        public static final int SORT_BY_REVIEW = 3;
        public static final int SORT_BY_STAR = 4;
        public static final int SORT_BY_CHAPTER = 5;
        public static final int SORT_BY_PRICE = 6;
    }

    public static class TRANSACTION_STATUS{
        public static final int SUCCESS = 0;
        public static final int WAITING = 1;
        public static final int REJECT = 2;
    }

    public static class VERIFY_CODE_TYPE{
        public static final int FORGOT_PASS = 1;
        public static final int VERIFY_EMAIL = 2;
    }

    public static class TRANSACTION_TYPE{
        public static final int BUY_BOOK = 1;
        public static final int SELL_BOOK = 2;
        public static final int RECHARGE_BOOK = 3;
        public static final int ADMIN_ADD = 4;
    }

    public static class USER_STATUS{
        public static final int LOGIN = 1;
        public static final int LOGOUT = 0;
        public static final int BLOCK = -1;
    }

    public static class USER_ROLE{
        public static final int USER = 1;
        public static final int ADMIN = 2;
    }

    public static class BOOK_STATUS{
        public static final int REQUEST = 0;
        public static final int ACCEPTED = 1;
        public static final int COMPLETED = 2;
        public static final int REJECTED = -1;
    }

    public static class USER_TYPE_RANKING{
        public static final int INCOME_WRITER = 1;
        public static final int SPEND_POINT_USER = 2;
        public static final int RECHARGE_USER = 3;
    }

    public static class BOOK_CHAPTER_STATUS{
        public static final int NOT_VERIFY = 0;
        public static final int VERIFY = 1;
        public static final int WARNING = -1;
    }

    public static class REACT_WARNING{
        public static final int GOOD = 1;
        public static final int NOT_GOOD = -1;
    }
}


