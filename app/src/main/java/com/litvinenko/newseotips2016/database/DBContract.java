package com.litvinenko.newseotips2016.database;

public class DBContract {

    public static final class Tables {
        public static final String SEO_ADVICES_TABLE = "advice";
    }

    public static final class SeoAdvicesTable {
        public static final String ID_COLUMN = "_id";
        public static final String NAME_COLUMN = "adviceName";
        public static final String CONTENT_COLUMN = "adviceContent";
        public static final String CATEGORY_COLUMN = "adviceCategory";
    }
}
