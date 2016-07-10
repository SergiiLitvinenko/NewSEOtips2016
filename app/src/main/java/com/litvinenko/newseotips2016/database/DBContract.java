package com.litvinenko.newseotips2016.database;

public class DBContract {

    public static final class Tables {
        public static final String SEO_ADVICE_TABLE = "advice";
    }

    public static final class SeoAdviceTable {
        public static final String ID_COLUMN = "_id";
        public static final String CATEGORY_COLUMN = "adviceCategory";
        public static final String NUMBER_COLUMN = "adviceNumber";
        public static final String NAME_COLUMN = "adviceName";
        public static final String CONTENT_COLUMN = "adviceContent";
        public static final String EXAMPLE_COLUMN = "adviceExample";
        public static final String IMAGE_COLUMN = "adviceImage";
    }
}
